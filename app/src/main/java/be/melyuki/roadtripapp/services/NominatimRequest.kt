package be.melyuki.roadtripapp.services

import be.melyuki.roadtripapp.models.MapResearchModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.maps.logW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class NominatimRequest() {
    suspend fun searchCity(city: String) : List<MapResearchModel>? {

        return withContext(Dispatchers.IO) {

            val url = URL("https://nominatim.openstreetmap.org/search?q=$city&format=json")
//            logW("URL", url.toString())

            val connect = url.openConnection() as? HttpURLConnection

            connect?.run {
                requestMethod = "GET"
                setRequestProperty("content-type", "application/json; charset=utf-8")
                doInput = true

                val json = inputStream.bufferedReader().lineSequence().joinToString("\n")
//                logW("JSON", json)

                // Solution:
                // https://www.baeldung.com/kotlin/gson-parse-arrays
                val typeToken = object : TypeToken<List<MapResearchModel>>() {}.type
                val result = Gson().fromJson<List<MapResearchModel>>(json, typeToken)

                return@withContext result
            }
            throw Exception("Connection is not open")
        }
    }
}