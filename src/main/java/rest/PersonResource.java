package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok()
                .entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOnId(@PathParam("id")Integer id) {
        PersonDTO psd = FACADE.getPerson(id);
        return Response.ok()
                .entity(GSON.toJson(psd)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String a)  {
        PersonDTO psd = GSON.fromJson(a, PersonDTO.class);
            PersonDTO result = FACADE.addPerson(psd.getfName(),psd.getlName(),psd.getAge(),psd.getGender(),psd.getPhone(),psd.getEmail(), psd.getCity(), psd.getStreet(), psd.getZip(),psd.getHobbies());
            return Response.ok()
                    .entity(GSON.toJson(result)).build();
    }
}