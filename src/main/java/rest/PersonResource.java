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
import java.lang.reflect.ReflectPermission;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
   // @Path("all") det er normalt at 'all' output kommer direkte på applayer, og ikke på "/all" path
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok()
                .entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOnId(@PathParam("id") Integer id) {
        PersonDTO pDTO = FACADE.getPerson(id);
        return Response.ok()
                .entity(GSON.toJson(pDTO)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String a) throws Exception {
        PersonDTO pDTO = GSON.fromJson(a, PersonDTO.class);
        return Response.ok().entity(GSON.toJson(FACADE.addPerson(pDTO))).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(String a){
        PersonDTO pDTO = GSON.fromJson(a, PersonDTO.class);
        return Response.ok().entity(GSON.toJson(FACADE.editPerson(pDTO))).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id){
        FACADE.deletePersonById(id);
        return Response.ok().build();
    }
}
