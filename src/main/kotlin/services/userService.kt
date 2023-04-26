package services

import com.google.gson.GsonBuilder
import models.UserModel
import org.json.JSONObject
import repo.UserRepo
import java.util.*

class Service {

    fun handleGetAll(limit: Int, offset: Int, json: String): MutableList<UserModel> {
        return UserRepo().getAllUsers(limit, offset, json)
    }

    fun handleGet(id: String, json: String): UserModel? {
        return UserRepo().getUserById(id, json)
    }

    fun handlePost(json: String): UserModel? {
        val gson = GsonBuilder().create()
        val user = gson.fromJson(json, UserModel::class.java) //map json to userModel
        user.timeCreated = System.currentTimeMillis()
        user.timeLastUpdated = System.currentTimeMillis()
        user.userModelId = UUID.randomUUID().toString()
        //pattern matching for verifying correct email address
        val emailPattern = Regex("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+\$")
        //if incorrect mail format or number length not right return null
        if ((!emailPattern.matches(user.email)) ||
            (user.number.length != 10)
        ) {
            return null
        }
        //check if the email or number already exists in db or not, if yes return null
        if (UserRepo().getNumberorEmail(user.number, user.email) == null
        ) {
            return UserRepo().postNewUser(user)
        }
        return null
    }

    fun handlePut(json: String): UserModel? {
        val gson = GsonBuilder().create()
        val user = gson.fromJson(json, UserModel::class.java)
        val currUser = UserRepo().getNumberorEmail(user.number, user.email)
        if (currUser != null) {
            if (((currUser.userModelId == user.userModelId) || (currUser == null))) {
                user.timeLastUpdated = System.currentTimeMillis()
                val mapper = JSONObject(json)
                var fields = mapper.getJSONArray("fields").toList().map { it.toString() }
                return UserRepo().updateUser(user, fields)
            }
        }
        return null
    }

    fun handleDelete(id: String): Boolean {
        return UserRepo().removeUser(id)
    }
}