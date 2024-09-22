package idv.tia201.g2.web.member.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SendSmsService {
    //     Find your Account Sid and Token at twilio.com/console
    @Value("${XiaHuTea.sms.user}")
    public String ACCOUNT_SID;
    @Value("${XiaHuTea.sms.token}")
    public String AUTH_TOKEN;
    public static final String messageHeader = "XiaHuTea verify code is : ";


    public void sendSMS(String phoneNumber, Integer memberId) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        StringBuilder sb = new StringBuilder();
        sb.append(messageHeader);
        sb.append(randomToken());
        System.out.println(memberId);
        System.out.println(sb.toString());
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(phoneNumber),
                        new com.twilio.type.PhoneNumber("+14695759239"), sb.toString())
                .create();
        System.out.println(message);
    }

    public String randomToken() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(chars.length);
            sb.append(chars[randomIndex]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }

}

