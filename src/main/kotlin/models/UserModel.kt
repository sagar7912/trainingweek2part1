package models

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class UserModel(var userModelId: String, var name: String, var email: String, var number: String){

    constructor() : this("", "", "", "")

    override fun toString(): String {
        val obj = JSONObject()
        obj.put("userModelId",this.userModelId)
        obj.put("name",this.name)

        obj.put("email",this.email)
        obj.put("number",this.number)
        return obj.toString()
    }
}