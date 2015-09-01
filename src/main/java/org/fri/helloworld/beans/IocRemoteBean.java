package org.fri.helloworld.beans;


import org.fri.helloworld.models.DbConfig;
import org.fri.helloworld.models.IOC;



import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;



@Stateful
public class IocRemoteBean implements IocInterface {
    private static final String PERSISTENCE_UNIT_NAME = "dcdbfri";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, DbConfig.getPersistConfig());
    private EntityManager em = factory.createEntityManager();

    public void addIOC(IOC ioc) {
        ioc.setId(null);
        em.getTransaction().begin();
        em.persist(ioc);
        em.getTransaction().commit();
    }

    public void removeIOC(Integer id) {
        IOC ioc = em.find(IOC.class, id);
        em.getTransaction().begin();
        em.remove(ioc);
        em.getTransaction().commit();
    }

    public List<IOC> showIOCs() {
        TypedQuery<IOC> query  = em.createNamedQuery("IOC.findAll", IOC.class);
        return query.getResultList();
    }
}