import org.glassfish.jersey.server.ResourceConfig
import resources.MyResource

val obj = MyResource()

class MyApplication : ResourceConfig() {

    init {
        register(obj) // registered the MyResource Class here as the next layer to move into if the request is hit
    }
}