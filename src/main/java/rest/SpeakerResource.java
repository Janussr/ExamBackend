package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.ConferenceFacade;
import facades.SpeakerFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;

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
}