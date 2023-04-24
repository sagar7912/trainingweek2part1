package resources

import services.Service
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("users") // users is the path
class MyResource {
    companion object {
        const val value = 1
        private val service = Service() // initialized object of Service class to use further
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun handleGetAll(): String {
        return service.handleGetAll().toString()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleGetOne(@PathParam("id") id: String): String {
        return service.handleGet(id).toString()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun handlePost(user: String): String {
        return service.handlePost(user).toString()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun handlePut(user: String): String {
        return service.handlePut(user).toString()
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    fun handleDelete(@PathParam("id") id: String): Boolean{
        return service.handleDelete(id)
    }
}
