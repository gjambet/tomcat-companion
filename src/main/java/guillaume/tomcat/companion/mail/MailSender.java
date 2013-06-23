package guillaume.tomcat.companion.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Yom
 */
public class MailSender {

   private Session session;

    public MailSender(Session session) {
        this.session = session;
    }

    // todo : what is this class for ?
    // todo : add Unit Test with java mock mail
    public void notify(String target, String file) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@"));
        InternetAddress to[] = new InternetAddress[1];
        to[0] = new InternetAddress(target);
        message.setRecipients(Message.RecipientType.TO, to);
        message.setSubject("subject");
        message.setContent("content", "text/plain");
        Transport.send(message);
    }
}
