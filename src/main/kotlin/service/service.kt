package service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.json.JSONObject
import repo.User
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*
class Service{
    private var file = File("users.txt")
    private var writer= FileWriter(file,true)
    companion object{
        private var users: MutableList<User> = mutableListOf()
        private val objectMapper = ObjectMapper().registerModule(KotlinModule())
        init{
//            users.add(User(ID++, "Sagar", "sagar.sheoran@fretron.com", "7727839857"))
//            users.add(User(ID++, "Atul", "atul.bhatia@fretron.com", "9999999999"))
            var file = File("users.txt")
            var writer= FileWriter(file,true)
            val bufferedReader = BufferedReader(FileReader(file))
            var string =file.readText()
            users = objectMapper.readValue(string,object: TypeReference<MutableList<User>>(){})
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
        val user = User(UUID.randomUUID(),obj.get("name"),obj.get("email"),obj.get("number"))
        users.add(user)
        objectMapper.writeValue(file,users)
        writer.flush()
        return user.toString()
    }
    fun handlePut(id: Int, json: String): User?{
        if(id == null){
            return null
        }
        val obj = JSONObject(json)
        val user = User(id,obj.get("name"), obj.get("email"), obj.get("number"))
        users.replaceAll { if (it.id == id) user else it }
        objectMapper.writeValue(file,users)
        writer.flush()
        return user
    }
    fun handleDelete(id: Int?){
        if(id == null){
            return
        }
        val user = users.find{it.id == id} ?: return
        users.remove(user)
        objectMapper.writeValue(file,users)
        writer.flush()
    }
}