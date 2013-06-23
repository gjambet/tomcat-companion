package guillaume.tomcat.companion.mail;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Guillaume
 */
public class MailSenderFactory {

    private static final String JAVA_COMP_ENV = "java:comp/env";
    
    private MailSender sender;

    public MailSenderFactory() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup(JAVA_COMP_ENV);
        Session session = (Session) envCtx.lookup("mail/Session");
        sender = new MailSender(session);
    }

    public MailSender getMailSender() {
        return sender;
    }
}
