package org.fri.helloworld.bean;


import org.fri.helloworld.models.DbConfig;
import org.fri.helloworld.models.IOC;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;




@Stateless
public class IocLocalBean {
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