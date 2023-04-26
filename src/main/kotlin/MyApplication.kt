
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import resources.MyResource
import java.net.URI

val obj = MyResource()

class MyApplication : ResourceConfig() {

    init {
        register(obj) // registered the MyResource Class here as the next layer to move into if the request is hit
    }

    fun start() {
        val baseURI = URI.create("http://localhost:8000/") // this is the port number
        val resourceConfig = ResourceConfig.forApplicationClass(MyApplication::class.java)
        val server = GrizzlyHttpServerFactory.createHttpServer(baseURI, resourceConfig)
        while (true)
            server.start() // server started
    }
}