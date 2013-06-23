package guillaume.tomcat.companion.jndi;

import static org.fest.assertions.Assertions.assertThat;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;

import guillaume.tomcat.companion.jndi.FilePlaceholder;
import guillaume.tomcat.companion.jndi.JndiFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class JndiFactoryTest {

    /**
     * Simulation du contexte JNDI
     * 
     * sert a simuler la configuration suivante dans le context.xml du serveur :
     * 
     *  <Resource name="addi/Keystore" auth="Container" type="ch.vd.dfin.addi.core.config.crypto.JndiFilePlaceholder" 
     *      factory="org.apache.naming.factory.BeanFactory" path="src/test/resources/keystore/vaudtax.jceks" />
     *  
     */
    @BeforeClass
    public static void setUpClass() throws Exception {

        // Setup the JNDI context and the FilePlaceHolder

        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        InitialContext ctx = new InitialContext();

        ctx.createSubcontext("java:");
        ctx.createSubcontext("java:comp");
        ctx.createSubcontext("java:comp/env");
        ctx.createSubcontext("java:comp/env/config");

        // Construct FilePlaceHolder
        FilePlaceholder placeHolder = new FilePlaceholder();
        placeHolder.setPath("src/test/resources/SmartMuleDescriptor.xml");
        ctx.bind("java:comp/env/config/Smartmule", placeHolder);

    }

    @Test
    public void smokeTest() throws NamingException {
        FilePlaceholder placeHolder = new JndiFactory<FilePlaceholder>().getRessource("config/Smartmule");
        assertThat(placeHolder).isNotNull();
    }
}
