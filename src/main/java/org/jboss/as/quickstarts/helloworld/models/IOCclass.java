package org.jboss.as.quickstarts.helloworld.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IOCclass {
    static final String PERSISTENCE_UNIT_NAME = "dcdbfri";
    EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistConfig());
    EntityManager em = factory.createEntityManager();

    private Map<String, Object> persistConfig() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        for (String envName : env.keySet()) {
            if (envName.contains("DB_HOSTURL")) {
                /* DB_URL = jdbc:mysql://localhost:3306/dcdbfri
                *  DB_HOSTURL = localhost
                * */

                configOverrides.put("javax.persistence.jdbc.url", "jdbc:mysql://" + env.get(envName) + ":3306/dcdbfri");
            }
            if (envName.contains("DB_USER")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            }
            if (envName.contains("DB_PWD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        }

        /* For Docker environment */
        String host = System.getenv("MYSQL_PORT_3306_TCP_ADDR");
        String port = System.getenv("MYSQL_PORT_3306_TCP_PORT");
        if (host != null && port != null) {
            configOverrides.put("javax.persistence.jdbc.url", "jdbc:mysql://" + host + ":" + port + "/dcdbfri");
        }

        String user = System.getenv("MYSQL_ENV_MYSQL_USER");
        String pwd = System.getenv("MYSQL_ENV_MYSQL_PASSWORD");
        if (user != null && pwd != null) {
            configOverrides.put("javax.persistence.jdbc.user", user);
            configOverrides.put("javax.persistence.jdbc.password", pwd);
        }

        return configOverrides;
    }

    public List<IOC> getIOCs() {
        Query q = em.createQuery("select t from IOC t");
        List<IOC> ioclist = q.getResultList();
        return ioclist;
    }

    public void insertIOC(String name, String desc, String ip) {
        em.getTransaction().begin();
        IOC ioc = new IOC();
        ioc.setDescription(desc);
        ioc.setIp(ip);
        ioc.setName(name);
        em.persist(ioc);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteIOC(Integer id) {
        IOC ioc = em.find(IOC.class, id);
        em.getTransaction().begin();
        em.remove(ioc);
        em.getTransaction().commit();
    }
}
