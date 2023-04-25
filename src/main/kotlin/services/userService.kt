package services

import com.google.gson.GsonBuilder
import models.UserModel
import repo.UserRepo
import java.time.LocalDateTime
import java.util.*

class Service {

    fun handleGetAll(): MutableList<UserModel> {
        return UserRepo().getAll()
    }

    fun handleGet(id: String): UserModel? {
        return UserRepo().get(id)
    }

    fun handlePost(json: String): UserModel? {
        val gson = GsonBuilder().create()
        val user = gson.fromJson(json, UserModel::class.java) //map json to userModel
        user.timeCreated = LocalDateTime.now().toString()
        user.timeLastUpdated = LocalDateTime.now().toString()
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
        if ((UserRepo().getNumber(user.number) == null) &&
            (UserRepo().getEmail(user.email) == null)
        ) {
            return UserRepo().post(user)
        }
        return null
    }

    fun handlePut(json: String): UserModel? {
        val gson = GsonBuilder().create()
        val user = gson.fromJson(json, UserModel::class.java)
        val currUser1 = UserRepo().getEmail(user.email)
        val currUser2 = UserRepo().getNumber(user.number)
        if ((currUser1 == user || currUser1 == null) && (currUser2 == null || currUser2 == user)) {
            user.timeLastUpdated = LocalDateTime.now().toString()
            return UserRepo().put(user)
        }
        return null
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