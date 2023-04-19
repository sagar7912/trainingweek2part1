package repo

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class User(val id: String, val name: String, val email: String, val number: String){
    companion object{
        const val id=0
    }
    constructor(): this("0","","","")

    override fun toString(): String {
        val obj = JSONObject()
        obj.put("id",this.id)
        obj.put("name",this.name)
        obj.put("email",this.email)
        obj.put("number",this.number)
        return obj.toString()
    }
}
