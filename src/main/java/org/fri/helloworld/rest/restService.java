package org.fri.helloworld.rest;

import org.fri.helloworld.beans.IocInterface;
import org.fri.helloworld.beans.IocLocalBean;
import org.fri.helloworld.models.IOC;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;

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

    @EJB
    private static IocLocalBean bean;
    private static IocInterface remoteBean;


    public restService() throws Exception {
        remoteBean = InitialContext.doLookup("java:global/wildfly-helloworld/IocRemoteBean");
    }


    /* Local access to EJB */
    @GET
    public Response listIOCs() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(bean.showIOCs())
                .build();
    }

    @POST
    @Path("/add")
    public Response createIOC(IOC ioc) {
        bean.addIOC(ioc);
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
        bean.removeIOC(id);
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }


    /* Remote access to EJB */
    @GET
    @Path("/remote")
    public Response listIocsRemote() {
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(remoteBean.showIOCs())
                .build();
    }

    @POST
    @Path("/remote/add")
    public Response createIocRemote(IOC ioc) {
        remoteBean.addIOC(ioc);
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
    @Path("/remote/delete/{id}")
    public Response deleteIocRemote(@PathParam("id")Integer id) {
        remoteBean.removeIOC(id);
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
