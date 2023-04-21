import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI

fun main(args: Array<String>) {
    val baseURI = URI.create("http://localhost:8000/") // this is the port number
    val resourceConfig = ResourceConfig.forApplicationClass(MyApplication::class.java)
    val server = GrizzlyHttpServerFactory.createHttpServer(baseURI, resourceConfig)
    while (true)
        server.start() // server started
}