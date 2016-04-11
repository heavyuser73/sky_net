package com.syaku.security;

import com.syaku.security.model.domain.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;



@RestController
public class LoginController {



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







    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Result login(
            @RequestParam(value="email", defaultValue="") String email,
            @RequestParam(value="password", defaultValue="") String password) {


        final Session session = getSession();
        UserEntity user = (UserEntity) session.get(UserEntity.class, email);

        if(user == null) {
            String uuid = createUUID();
            UserEntity newEntity = new UserEntity();
            newEntity.setEmail(email);
            newEntity.setSession_id(uuid);

            session.beginTransaction();
            session.save(newEntity);
            session.getTransaction().commit();

        }

        if(user != null && user.getSession_id() != null) {
            System.out.println(user.getSession_id());
            return new Result("1", user.getSession_id());
        }
        else {
            return new Result("0.001", "Failed to login");
        }

    }

    private String createUUID() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return  randomUUIDString;
    }
}
