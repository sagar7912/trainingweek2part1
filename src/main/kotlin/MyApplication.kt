import org.glassfish.jersey.server.ResourceConfig
import request.MyResource
val obj = MyResource()
class MyApplication : ResourceConfig() {

    init {
        register(obj)
    }
}