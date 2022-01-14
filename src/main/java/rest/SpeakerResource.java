package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Speaker.SpeakerDTO;
import facades.ConferenceFacade;
import facades.SpeakerFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/speaker")
public class SpeakerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final SpeakerFacade facade =  SpeakerFacade.getInstance(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World! THis is endpoints for SpeakerResource";
    }

    //ROLES ALLOWED HERE

    @Path("/all")
    @GET
    @RolesAllowed({"admin","user"})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllSpeakers(){
        return gson.toJson(facade.getAllSpeakers());
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editSpeaker(@PathParam("id") int id, String speaker)  {
        SpeakerDTO s = gson.fromJson(speaker, SpeakerDTO.class);
        s.setId(id);
        SpeakerDTO speakerEdited = facade.editSpeaker(s);
        return gson.toJson(speakerEdited);
    }

}