package services

import com.google.gson.GsonBuilder
import models.UserModel
import org.json.JSONObject
import repo.UserRepo
import java.util.*

class Service {

    fun handleGetAll(): MutableList<UserModel> {
        return UserRepo().getAll()
    }

    fun handleGet(id: String?): UserModel?{
        if (id == null) {
            return null //if id not received return null
        } else {
            return UserRepo().get(id) //else search for the obj
        }
    }

    fun handlePost(json: String): UserModel? {
        val obj = JSONObject(json) //create json object of the incoming json string
        obj.put("userModelId", UUID.randomUUID().toString()) // add the random uuid as userModelId
        val gson = GsonBuilder().create()
        val user = gson.fromJson(obj.toString(), UserModel::class.java) //map json to userModel
        //pattern matching for verifying correct email address
        val emailPattern = Regex("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$")
        //if incorrect mail format or number length not right return null
        if ((!emailPattern.matches(user.email)) || (user.number.length != 10)) {
            return null
        }
        if (user == null) {//if user object not created return null
            return null
        } else {
            val users = handleGetAll()
            //check if the email or number already exists in db or not, if yes return null
            if ((users.firstOrNull { it.email == user.email } == null) && (users.firstOrNull { it.number == user.number } == null)) {
                return UserRepo().post(user)
            }
            return null
        }
    }

    fun handlePut(json: String): UserModel? {
        val gson = GsonBuilder().create()
        val user = gson.fromJson(json, UserModel::class.java)
        val users = handleGetAll()
        if (user == null) {
            return null
        } else {
            //if the given userModelId do not exist then create the new user using post request
            val currUser = handleGet(user.userModelId) ?: return handlePost(user.toString())
            val users = handleGetAll()
            users.remove(currUser) // remove the existing user from the list to perform email and number checks
            var b = true
            if (currUser.email != user.email) {
                if (users.firstOrNull { it.email == user.email } != null) {
                    //we have removed the existing user so if email exists in file then return null
                    b = false
                }
            }
            if (currUser.number != user.number) {
                if (users.firstOrNull { it.number == user.number } != null) {
                    //we have removed the existing user so if number exists in file then return null
                    b = false
                }
            }
            if (b) {
                return UserRepo().put(user)
            } else {
                return null
            }
        }
    }

    fun handleDelete(id: String): Boolean {
        val users = handleGetAll()
        if (users.firstOrNull { it.userModelId == id } == null) {
            //if user with this userModelId does not exist then return null
            return false
        } else {
            return UserRepo().delete(id)
        }
    }
}