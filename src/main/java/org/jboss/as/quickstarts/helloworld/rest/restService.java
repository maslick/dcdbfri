package org.jboss.as.quickstarts.helloworld.rest;

import org.jboss.as.quickstarts.helloworld.models.IOCclass;
import org.jboss.as.quickstarts.helloworld.models.IOC;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
    public Response listIOCs() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(test.getIOCs())
                .build();
    }

    @POST
    @Path("/add")
    public Response createIOC(IOC ioc) {
        test.insertIOC(ioc.getName(), ioc.getDescription(), ioc.getIp());
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
        test.deleteIOC(id);
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

