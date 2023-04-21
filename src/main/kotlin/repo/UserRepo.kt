package repo
import com.google.gson.Gson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import models.UserModel
import java.io.File

class UserRepo {
    private val file = File("users.bin") //creates or select the given file
    private val serializer = ListSerializer(UserModel.serializer()) //serializer created for writing objects

    @OptIn(ExperimentalSerializationApi::class)
    companion object {
        val gson = Gson()
        private var users: MutableList<UserModel> = mutableListOf()

        init {
            try {
                val file = File("users.bin")
                val readByteArray = file.readBytes() //read data in byte form
                users =
                    ProtoBuf.decodeFromByteArray(
                        ListSerializer(UserModel.serializer()),
                        readByteArray
                    ).toMutableList() //deserializing the data and storing into user
            } catch (e: Exception) {
                e.printStackTrace() // will run into exception if file not created already
            }
        }
    }


    fun getAll(): MutableList<UserModel> {
        return users
    }


    fun get(id: String): UserModel? {

        return users.firstOrNull { it.userModelId == id }
    }


    fun post(user: UserModel): UserModel {
        users.add(user)
        val byteArray = ProtoBuf.encodeToByteArray(serializer, users.toList()) //serialize the users list
        file.writeBytes(byteArray) //write into file
        return user
    }


    fun put(user: UserModel): UserModel{
        users.remove(get(user.userModelId))
        users.add(user)
        val byteArray = ProtoBuf.encodeToByteArray(serializer, users.toList())
        file.writeBytes(byteArray)
        return user
    }

    fun delete(id: String): Boolean {
        val isRemoved = users.removeIf { it.userModelId == id }
        return if (isRemoved) {
            val byteArray = ProtoBuf.encodeToByteArray(serializer, users.toList())
            file.writeBytes(byteArray)
            true
        } else {
            false
        }
    }
}