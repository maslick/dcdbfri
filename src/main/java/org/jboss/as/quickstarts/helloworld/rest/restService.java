package org.jboss.as.quickstarts.helloworld.rest;

import org.jboss.as.quickstarts.helloworld.models.IOC;


import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by maslick on 22/08/15.
 */
@Path("/iocs")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class restService {

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

    private static final String PERSISTENCE_UNIT_NAME = "dcdbfri";
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, persistConfig());
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

