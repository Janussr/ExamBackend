package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Talk.TalkDTO;
import facades.ConferenceFacade;
import facades.TalkFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/talk")
public class TalkResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TalkFacade facade =  TalkFacade.getInstance(EMF);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World! This is endpoints for TalkResource";
    }



    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createTalk(String talk) {
        TalkDTO talkDTO = gson.fromJson(talk, TalkDTO.class);
        TalkDTO talkDTONew = facade.createTalk(talkDTO);
        return gson.toJson((talkDTONew));
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTalks(){
        return gson.toJson(facade.getAllTalks());
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteTalk(@PathParam("id")int id){
        TalkDTO talkDeleted = facade.deleteTalk(id);
        return gson.toJson(talkDeleted);
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAllTalksFromSpecificConference(@PathParam("id")int id){
        return gson.toJson(facade.getAllTalksInSpecificConference(id));
    }

    @Path("/byspeaker/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAllTalksFromSpecificSpeaker(@PathParam("id")int id){
        return gson.toJson(facade.getAllTalksBySpeaker(id));
    }

    @Path("/changespeaker/{id}/{newId}/{oldId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String changeSpeaker(@PathParam("id")int id,@PathParam("oldId") int oldId,@PathParam("newId") int newId){
        facade.updateTalkSpeaker(id,newId,oldId);
        return "";
    }

}