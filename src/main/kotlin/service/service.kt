package service

import org.json.JSONObject
import repo.User
import java.util.concurrent.CopyOnWriteArrayList

class Service{
    companion object{
        var ID =0
        private val users = CopyOnWriteArrayList<User>()
        init{
            users.add(User(ID++, "Sagar", "sagar.sheoran@fretron.com", "7727839857"))
            users.add(User(ID++, "Atul", "atul.bhatia@fretron.com", "9999999999"))
        }
    }


    fun handleGetAll(): String{
        return users.toString()
    }

    fun handleGet(id: Int?): User?{
        if(id == null){
            return null
        }
        else{
            val user =  users.find{it.id == id}
            if(user == null){
                return null
            }
            else{
                return user
            }
        }
    }
    fun handlePost(json: String): String{
        val obj = JSONObject(json)
        val user = User(ID++,obj.get("name"),obj.get("email"),obj.get("number"))
        users.add(user)
        return user.toString()
    }
    fun handlePut(id: Int, json: String): User?{
        if(id == null){
            return null
        }
        val obj = JSONObject(json)
        val user = User(id,obj.get("name"), obj.get("email"), obj.get("number"))
        users.replaceAll { if (it.id == id) user else it }
        return user
    }
    fun handleDelete(id: Int?){
        if(id == null){
            return
        }
        val user = users.find{it.id == id} ?: return
        users.remove(user)
    }
}