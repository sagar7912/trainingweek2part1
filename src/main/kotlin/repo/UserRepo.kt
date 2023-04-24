package repo
import com.google.gson.Gson
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.combine
import kotlinx.serialization.ExperimentalSerializationApi
import models.UserModel
import org.bson.Document

class UserRepo {
    @OptIn(ExperimentalSerializationApi::class)
    companion object {
        private val mongoClient = MongoClients.create("mongodb://localhost:27017")
        private val database = mongoClient.getDatabase("mydb")
        private val collection = database.getCollection("users")
        private val gson = Gson()
        private val documents = collection.find().toList()
        private val jsonArray = documents.joinToString(prefix = "[", separator = ",", postfix = "]") { gson.toJson(it) }
        var users: MutableList<UserModel> = gson.fromJson(jsonArray, Array<UserModel>::class.java).toMutableList()
    }


    fun getAll(): MutableList<UserModel> {
        return users
    }


    fun get(id: String): UserModel? {
        return users.firstOrNull { it.userModelId == id }
    }


    fun post(user: UserModel): UserModel {
        users.add(user)
        collection.insertOne(Document.parse(user.toString()))
        return user
    }


    fun put(user: UserModel): UserModel {
        println("!!!")
        users.remove(get(user.userModelId))
        users.add(user)
        val str = user.userModelId
        val filter = eq("userModelId", user.userModelId)
        val update = combine(Document.parse(user.toString()))
        collection.updateOne(filter, update)
        return user
    }

    fun delete(id: String): Boolean {
        val isRemoved = users.removeIf { it.userModelId == id }
        return if (isRemoved) {
            val filter = eq("userModelId", id)
            collection.deleteOne(filter)
            users.remove(get(id))
            true
        } else {
            false
        }
    }
}