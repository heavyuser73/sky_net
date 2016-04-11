import com.syaku.security.model.domain.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;
import java.util.Map;

/**
 * User: jhkim
 * Date: 16. 4. 8
 * Time: 오후 1:13
 */
public class Main {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {

            String hibernatePropsFilePath = "/home/heavyuser/workspace/Spring-security-restful-authentication-and-Angularjs/src/hibernate.cfg.xml";
            File hibernatePropsFile = new File(hibernatePropsFilePath);

            Configuration configuration = new Configuration();
            configuration.configure(hibernatePropsFile);

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = session.getSessionFactory().getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {

                    if(o instanceof  UserEntity) {
                        UserEntity ue = (UserEntity) o;
                        System.out.println("  " + ue.getEmail());
                    }


                }
            }
        } finally {
            session.close();
        }
    }
}
