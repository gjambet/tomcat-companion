
package guillaume.tomcat.companion.jndi;

import java.io.IOException;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Guillaume
 */
public class JndiFactory<T> {

    private static final String JAVA_COMP_ENV = "java:comp/env";

    public T getRessource(String key) throws NamingException {

        try{

            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup(JAVA_COMP_ENV);
            return (T) envCtx.lookup(key); // "config/Keystore"

        } catch (NamingException e) {
            NamingException ex = new NamingException("Can not find / create Resource for : " + JAVA_COMP_ENV + '/' + key);
            ex.setRootCause(e);
            throw ex;
        }
    }


}
