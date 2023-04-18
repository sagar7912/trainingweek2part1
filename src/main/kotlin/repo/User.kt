package repo

import org.json.JSONObject

data class User(val id: Int, val name: Any, val email: Any, val number: Any){
    constructor(): this(0,"","","")

    override fun toString(): String {
        val obj = JSONObject()
        obj.put("id",this.id)
        obj.put("name",this.name)
        obj.put("email",this.email)
        obj.put("number",this.number)
        return obj.toString()
    }
}
