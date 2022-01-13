package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Conference.ConferenceDTO;
import facades.ConferenceFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/conference")
public class ConferenceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ConferenceFacade facade =  ConferenceFacade.getInstance(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World! This is the endpoints conference";
    }



    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllConfereneces(){
        return gson.toJson(facade.getAllConferences());
    }


    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createConference(String conference){
        ConferenceDTO conferenceDTO = gson.fromJson(conference, ConferenceDTO.class);
        ConferenceDTO conferenceDTONew = facade.createConference(conferenceDTO);
        return gson.toJson(conferenceDTONew);
    }
}