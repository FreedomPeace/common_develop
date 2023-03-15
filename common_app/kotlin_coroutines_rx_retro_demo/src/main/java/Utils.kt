
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object Utils {
    fun toPrettyFormat(json: Any?): String {
        val gson = Gson()
        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(gson.toJson(json)).asJsonObject
        val gson2 = GsonBuilder().setPrettyPrinting().create()
        return gson2.toJson(jsonObject)
    }
}