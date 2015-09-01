package org.fri.helloworld.rest;

import org.fri.helloworld.models.DbConfig;
import org.fri.helloworld.models.IOC;


import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by maslick on 22/08/15.
 */
@Path("/iocs")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class restService {
    private static final String PERSISTENCE_UNIT_NAME = "dcdbfri";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, DbConfig.getPersistConfig());
    private EntityManager em = factory.createEntityManager();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listIOCs() {
        TypedQuery<IOC> query  = em.createNamedQuery("IOC.findAll", IOC.class);
        List<IOC> iocs = query.getResultList();
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(iocs)
                .build();
    }

    @POST
    @Path("/add")
    public Response createIOC(IOC ioc) {
        ioc.setId(null);
        em.getTransaction().begin();
        em.persist(ioc);
        em.getTransaction().commit();
        return Response
                .status(Response.Status.CREATED)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(ioc)
                .build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteIOC(@PathParam("id")Integer id) {
        IOC ioc = em.find(IOC.class, id);
        em.getTransaction().begin();
        em.remove(ioc);
        em.getTransaction().commit();
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
}

