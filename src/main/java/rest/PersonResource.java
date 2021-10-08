package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import errorhandling.CustomException;
import facades.PersonFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ReflectPermission;

@OpenAPIDefinition(
        info = @Info(
                title = "Peter og Thomas PersonDatabase API",
                version = "0.011598",
                description = "Simpel API til person håndtering CA1"
        ),
        tags = {
                @Tag(name = "Person", description = "Persondatabase API endpoints")

        },
        servers = {
                @Server(
                        description = "For Local host testing",
                        url = "http://localhost:8080/CA1_war_exploded/"
                ),
                @Server(
                        description = "Server API",
                        url = "https://thomasovergaard.me"
                )

        }
)



@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() throws CustomException {
        PersonsDTO psDto = FACADE.getAllPersons();
        return Response.ok()
                .entity(GSON.toJson(psDto)).build();
    }


    @Operation(summary = "Get a person by id",
                tags = {"person"},
                responses = {
                    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The person requested"),
                    @ApiResponse(responseCode = "400", description = "Person with requested ID does not exist")
                })

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOnId(@PathParam("id") Integer id) throws CustomException {
        PersonDTO pDTO = FACADE.getPerson(id);
        return Response.ok()
                .entity(GSON.toJson(pDTO)).build();
    }


    @Operation(summary = "Add new person to database",
            tags = {"person"},
            responses = {
                    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "200", description = "The person added to the database"),
                    @ApiResponse(responseCode = "403", description = "Person with that data already exists")
            })

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String a) throws CustomException {
        PersonDTO pDTO = GSON.fromJson(a, PersonDTO.class);
        PersonDTO result = FACADE.addPerson(pDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @Operation(summary = "Edit a person in the database",
            tags = {"person"},
            responses = {
                    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "404", description = "One or more fields are missing."),
                    @ApiResponse(responseCode = "404", description = "Hobby not found."),
                    @ApiResponse(responseCode = "404", description = "No person-ID was provided"),
                    @ApiResponse(responseCode = "403", description = "The Phone number already exists.")
            })

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(String a) throws CustomException {
        PersonDTO pDTO = GSON.fromJson(a, PersonDTO.class);
        return Response.ok().entity(GSON.toJson(FACADE.editPerson(pDTO))).build();
    }

    @Operation(summary = "Delete a person in the database",
            tags = {"person"},
            responses = {
                    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No person with provided id found.")
            })


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer id) throws CustomException {
        FACADE.deletePersonById(id);
        return Response.ok().build();
    }

    @GET
    @Path("/hobby/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allHobbies(){
        return Response.ok().entity(GSON.toJson(FACADE.returnAllHobbys())).build();
    }

    @GET
    @Path("/cities/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allCities(){
        return Response.ok().entity(GSON.toJson(FACADE.returnAllCities())).build();
    }
}
