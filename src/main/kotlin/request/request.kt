package request
import service.Service
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
@Path("users")
class MyResource {
    companion object{
        const val value=1
    }
    private val service=Service()

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleGetAll(): String {
        return service.handleGetAll()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleGetOne(@PathParam("id") id: Int): String {
        return service.handleGet(id).toString()
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun handlePost(user: String): String{
        return service.handlePost(user)
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun handlePut(@PathParam("id")id: Int, user: String): String{
        return service.handlePut(id, user).toString()
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun handleDelete(@PathParam("id") id: Int): String{
        service.handleDelete(id)
        return "Deleted"
    }
}
