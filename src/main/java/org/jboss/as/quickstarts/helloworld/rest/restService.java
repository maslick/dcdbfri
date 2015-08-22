package org.jboss.as.quickstarts.helloworld.rest;

import org.jboss.as.quickstarts.helloworld.models.IOCclass;
import org.jboss.as.quickstarts.helloworld.models.IOC;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
    @Inject
    IOCclass test;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<IOC> listIOCs() {
        return test.getIOCs();
    }

    @POST
    @Path("/add")
    public Response createIOC(IOC ioc) {
        test.insertIOC(ioc.getName(), ioc.getDescription(), ioc.getIp());
        return Response.status(Response.Status.CREATED).entity(ioc).build();
    }
}

