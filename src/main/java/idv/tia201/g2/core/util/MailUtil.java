package idv.tia201.g2.core.util;

import idv.tia201.g2.core.pojo.Mail;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


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

}
