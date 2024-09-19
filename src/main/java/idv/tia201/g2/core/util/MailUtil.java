package idv.tia201.g2.core.util;

import idv.tia201.g2.core.pojo.Mail;
import idv.tia201.g2.web.store.vo.Store;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

@Component
public class MailUtil {

    private static final String SENDER = "tiaxiahutea@gmail.com";
    private static final String APPLICATION_PASSWORD = "krxeqijcufqrcaay";

    public static void sendMail(Mail mail) throws AddressException, MessagingException {
        Session session = getSession();
        Message message = getMessage(session, mail);
        Transport.send(message);
    }

    private static Message getMessage(Session session, Mail mail) throws AddressException, MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getRecipient()));
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        return message;
    }

    private static Session getSession() {
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, APPLICATION_PASSWORD);
            }
        });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    public void sendAttachmentsMail(Store store, Mail mail) throws MessagingException, IOException {
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setSubject(mail.getSubject());
        helper.setText(buildContent(store.getStoreName(), store.getPassword()), true);
        helper.setTo(mail.getRecipient());
        helper.setFrom(new InternetAddress(SENDER));
        Transport.send(message);
    }

    public String buildContent(String storeName, String password) throws IOException {
        //加载邮件html模板
        Resource resource = new ClassPathResource("static/templates/mailtemplate.ftl");
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try (
                InputStream inputStream = resource.getInputStream();
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return MessageFormat.format(buffer.toString(), storeName, password);
    }


}
