package repo
import com.google.gson.Gson
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.or
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates.set
import models.UserModel
import org.bson.Document
import org.json.JSONObject
import java.util.*

class UserRepo {
    companion object {
        private val mongoClient = MongoClients.create("mongodb://localhost:27017")
        private val database = mongoClient.getDatabase("mydb")
        private val collection = database.getCollection("users")
        private val gson = Gson()
    }


    fun getAllUsers(limit: Int, offset: Int, json: String): MutableList<UserModel> {
        var users: MutableList<UserModel> = mutableListOf()
        val sortBy = Sorts.ascending("timeCreated")
        val cursor =
            collection.find().projection(Document.parse(json)).sort(sortBy).limit(limit).skip(limit * offset).iterator()
        while (cursor.hasNext()) {
            val doc = cursor.next()
            val user = gson.fromJson(doc.toJson(), UserModel::class.java)
            users.add(user)
        }
        return users
    }


    fun getUserById(id: String, json: String): UserModel? {
        val filter = eq("_id", id)
        val document = collection.find(filter).first()
        return gson.fromJson(document.toJson(), UserModel::class.java)
    }

    fun getNumberorEmail(data: Pair<String, String>): UserModel? {
        val filter = or(eq("number", data.first), eq("email", data.second))
        val cursor = collection.find(filter).iterator()
        if (cursor.hasNext()) {
            val doc = cursor.next()
            return gson.fromJson(doc.toJson(), UserModel::class.java)
        }
        return null
    }


    fun postNewUser(user: UserModel): UserModel {
        val doc = Document.parse(user.toString())
        doc["_id"] = user.userModelId ?: UUID.randomUUID().toString()
        collection.insertOne(doc)
        val json = collection.find(eq("_id", user.userModelId)).first()?.toJson()
        return gson.fromJson(json, UserModel::class.java)
    }


    fun updateUser(user: UserModel, fields: List<String>): UserModel? {
        val str = user.userModelId
        if (str == "") {
            return null
        }
        if (fields.isEmpty()) {
            val filter = eq("_id", user.userModelId)
            val update = Document("\$set", Document.parse(user.toString()))
            val updated = collection.findOneAndUpdate(
                filter,
                update,
                FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER)
            )
            return gson.fromJson(updated.toJson(), UserModel::class.java)
        } else {
            val mapper = JSONObject(user.toString())
            for (key in fields) {
                val filter = eq("_id", user.userModelId)
                val update = set(key, mapper.get(key))
                collection.updateOne(filter, update)
            }
            val cursor = collection.find(eq("_id", user.userModelId)).iterator()
            if (cursor.hasNext()) {
                val doc = cursor.next()
                return gson.fromJson(doc.toJson(), UserModel::class.java)
            }
            return null
        }
    }

    fun removeUser(id: String): Boolean {
        return collection.deleteOne(eq("_id", id)).wasAcknowledged()
    }
}