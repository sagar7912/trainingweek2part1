
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import request.MyHttpHandler
import java.net.URI
val request=MyHttpHandler()
fun main(args: Array<String>) {
    val baseURI = URI.create("http://localhost:8080/")
    val resourceConfig = ResourceConfig.forApplicationClass(MyApplication::class.java)
    val server = GrizzlyHttpServerFactory.createHttpServer(baseURI, resourceConfig)
    while (true)
     server.start()
//    while (true) {
//        Thread.sleep(Long.MAX_VALUE)
//    }
}