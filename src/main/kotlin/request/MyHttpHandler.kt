//package request
//import org.glassfish.grizzly.http.server.HttpHandler
//import org.glassfish.grizzly.http.server.Request
//import org.glassfish.grizzly.http.server.Response
//import repo.User
//import java.util.concurrent.CopyOnWriteArrayList
//class MyHttpHandler : HttpHandler() {
//    companion object{
//        var ID =0
//        private val users = CopyOnWriteArrayList<User>()
//        init{
//            println("!")
//            users.add(User("0", "Sagar", "sagar.sheoran@fretron.com", "7727839857"))
//            users.add(User(, "Atul", "atul.bhatia@fretron.com", "9999999999"))
//        }
//    }
//    override fun service(request: Request, response: Response){
//        println("THIS LINE")
//        when(request.method.toString()){
//            "GET" -> handleGet(request,response)
//            "POST" -> handlePost(request,response)
//            "PUT" -> handlePut(request, response)
//            "DELETE" -> handleDelete(request, response)
//            else -> response.sendError(405, "Method not allowed")
//        }
//    }
//    private fun handleGet(request: Request, response: Response){
//        val path = request.requestURI.substringAfter('/')
//        println(path)
//        if(path == "users"){
//            println(ID)
//            response.writer.apply{
//                write(users.joinToString(separator="\n"))
//                flush()
//            }
//        }
//        else if(path.startsWith("users/")){
//            val id = path.substringAfterLast('/').toIntOrNull()
//            if(id == null){
//                response.sendError(400, "Invalid repo.User Id")
//            }
//            else{
//                val user = users.find{it.id == id}
//                if(user == null){
//                    response.sendError(400, "Invalid repo.User Id")
//                }
//                else{
//                    response.writer.apply { write(user.toString()); flush()}
//
//                }
//            }
//        }
//        else{
//            response.sendError(400, "Invalid path")
//        }
//    }
//    private fun handlePost(request: Request, response: Response){
//        val user = try {
//            val (name, number, email) = request.reader.readLines().toString().split(".")
//            User(ID++, name, email, number)
//        }
//        catch(e: Exception){
//            null
//        }
//        if(user == null){
//            response.sendError(400, "Invalid Data")
//        }
//        else{
//            users.add(user)
//            response.writer.apply{
//                write(user.toString())
//                flush()
//            }
//        }
//
//    }
//    private fun handlePut(request: Request, response: Response){
//        val path = request.requestURI.substringAfter('/')
//        val id = path.substringAfterLast('/').toIntOrNull()
//        val user = try{
//            val (name, number, email) = request.reader.readLines().toString().split('.')
//            User(ID++, name, email, number)
//        }
//        catch(e: Exception){
//            null
//        }
//        if(id == null || user == null){
//            response.sendError(400, "invalid data")
//        }
//        else if(users.none{ it.id == id }){
//            response.sendError(400, "Invalid Book Id")
//        }
//        else{
//            users.replaceAll { if (it.id == id) user else it}
//            response.writer.apply {
//                write(user.toString())
//                flush()
//            }
//        }
//    }
//    private fun handleDelete(request: Request, response: Response){
//        val path =request.requestURI.substringAfter('/')
//        if(path == "delete"){
//            response.sendError(400,"Specify the repo.User id to be deleted")
//        }
//        else if(path.startsWith("users/")){
//            val id1=path.substringAfterLast('/').toIntOrNull()
//            val user = users.find{it.id == id1}
//            if(user != null){
//                users.remove(user)
//                response.status = 204
//                response.writer.apply{
//                    write("Resource Deleted")
//                    flush()
//                }
//            }
//            else{
//                response.sendError(400 , "Not Found")
//            }
//        }
//    }
//}