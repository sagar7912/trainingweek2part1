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
    fun handleGetAll(
        @QueryParam("limit") limit: Int,
        @QueryParam("offset") offset: Int,
        @QueryParam("projections") json: String
    ): String {
        return service.handleGetAll(limit, offset,json).toString()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleGetOne(
        @PathParam("id") id: String, @QueryParam("projections") json: String
    ): String {
        return service.handleGet(id, json).toString()
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
        val user = service.handlePut(user)
        return user.toString()
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleDelete(@PathParam("id") id: String): Boolean{
        return service.handleDelete(id)
    }
}
