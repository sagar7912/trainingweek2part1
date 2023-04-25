package repo
import com.google.gson.Gson
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Sorts
import models.UserModel
import org.bson.Document
import java.util.*

class UserRepo {
    companion object {
        private val mongoClient = MongoClients.create("mongodb://localhost:27017")
        private val database = mongoClient.getDatabase("mydb")
        private val collection = database.getCollection("users")
        private val gson = Gson()
    }


    fun getAll(): MutableList<UserModel> {
        var users: MutableList<UserModel> = mutableListOf()
        val sortBy = Sorts.ascending("timeCreated")
        val document = collection.find().limit(10).skip(0).sort(sortBy).iterator()
        while (document.hasNext()) {
            val curr = document.next()
            val user = gson.fromJson(curr.toJson(), UserModel::class.java)
            users.add(user)
        }
        return users
    }


    fun get(id: String): UserModel? {
        val filter = eq("_id", id)
        val document = collection.find(filter).first()
        return gson.fromJson(document.toJson(), UserModel::class.java)
    }

    fun getNumber(number: String): UserModel? {
        val filter = eq("number", number)
        val document = collection.find(filter).firstOrNull()
        if (document != null) {
            return gson.fromJson(document.toJson(), UserModel::class.java)
        }
        return null
    }

    fun getEmail(email: String): UserModel? {
//        println("!!")
        val filter = eq("email", email)
        val document = collection.find(filter).firstOrNull()
        if (document != null) {
            return gson.fromJson(document.toJson(), UserModel::class.java)
        }
        return null
    }

    fun post(user: UserModel): UserModel {
        val doc = Document.parse(user.toString())
        doc["_id"] = user.userModelId ?: UUID.randomUUID().toString()
        collection.insertOne(doc)
        val json = collection.find(eq("_id", user.userModelId)).first()?.toJson()
        return gson.fromJson(json, UserModel::class.java)
    }


    fun put(user: UserModel): UserModel? {
        val str = user.userModelId
        if (str == "") {
            return null
        }
        val filter = eq("_id", user.userModelId)
        val update = Document("\$set", Document.parse(user.toString()))
        val updated = collection.findOneAndUpdate(filter, update, FindOneAndUpdateOptions().upsert(true))
        return gson.fromJson(updated.toJson(), UserModel::class.java)
    }

    fun delete(id: String): Boolean {
        return collection.deleteOne(eq("_id", id)).wasAcknowledged()
    }
}