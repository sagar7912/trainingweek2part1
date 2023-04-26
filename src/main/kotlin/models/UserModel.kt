package models
import org.json.JSONObject

data class UserModel(
    var userModelId: String,
    var name: String,
    var email: String,
    var number: String,
    var timeCreated: Long,
    var timeLastUpdated: Long
) {

    constructor() : this("", "", "", "", 0, 0)

    override fun toString(): String {
        val obj = JSONObject()
        obj.put("userModelId", this.userModelId)
        obj.put("name", this.name)
        obj.put("timeCreated", this.timeCreated)
        obj.put("timeLastUpdated", this.timeLastUpdated)
        obj.put("email", this.email)
        obj.put("number", this.number)
        return obj.toString()
    }
}