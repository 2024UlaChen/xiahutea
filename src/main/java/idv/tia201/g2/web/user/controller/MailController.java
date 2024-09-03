package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.core.pojo.Mail;
import idv.tia201.g2.core.util.MailUtil;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {
    @GetMapping("/mail")
    public void test() throws MessagingException {
        Mail mail = new Mail();
        mail.setRecipient("tiaxiahutea@gmail.com");
        mail.setSubject("Test");
        mail.setText("Test");
        MailUtil.sendMail(mail);
    }

}
