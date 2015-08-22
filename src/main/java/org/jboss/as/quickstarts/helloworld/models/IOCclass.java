package org.jboss.as.quickstarts.helloworld.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by maslick on 22/08/15.
 */
public class IOCclass {
    static final String PERSISTENCE_UNIT_NAME = "dcdbfri";
    static EntityManagerFactory factory;



    public List<IOC> getIOCs() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select t from IOC t");
        List<IOC> ioclist = q.getResultList();
        return ioclist;
    }

    public void insertIOC(String name, String desc, String ip) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        IOC ioc = new IOC();
        ioc.setDescription(desc);
        ioc.setIp(ip);
        ioc.setName(name);
        em.persist(ioc);
        em.getTransaction().commit();
        em.close();
    }
}
