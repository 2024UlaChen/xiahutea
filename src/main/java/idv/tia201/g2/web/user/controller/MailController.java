package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.core.pojo.Mail;
import idv.tia201.g2.core.util.MailUtil;
import idv.tia201.g2.web.store.vo.Store;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MailController {

    @Autowired
    private MailUtil mailUtil;

    @GetMapping("/mail")
    public void test() throws MessagingException {
        Mail mail = new Mail();
        mail.setRecipient("tiaxiahutea@gmail.com");
        mail.setSubject("Test");
        mail.setText("Test");
        MailUtil.sendMail(mail);
    }

    @GetMapping("/mail1")
    public void test1() throws MessagingException, IOException {
        Mail mail = new Mail();
        Store store = new Store();
        mail.setRecipient("tiaxiahutea@gmail.com");
        mail.setSubject("歡迎加入夏虎茶");
        mailUtil.sendAttachmentsMail(store,mail);
    }

}
