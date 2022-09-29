package lab1

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.awt.Desktop
import java.net.URI
import java.net.URL
import java.net.URLEncoder

class WikipediaSearch(var requestString: String) {
    private val requestLink = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch="
    private val resultLink = "https://ru.wikipedia.org/w/index.php?curid="

    var wikipediaResults: List<WikipediaPage> = emptyList()

    init {
        requestString = processInput(requestString)

        val jsonResponse = getJsonResponse(requestString)

        wikipediaResults = getResults(jsonResponse)

        if(wikipediaResults.isEmpty()) {
            throw Exception("Null search result")
        }
    }

    private fun processInput(input: String): String {
        return URLEncoder.encode(input, "UTF-8")
    }

    private fun getJsonResponse(requestString: String): String {
        return URL(requestLink + "\"$requestString\"").readText()
    }

    private fun getResults(jsonString: String): List<WikipediaPage> {
        val newResults: MutableList<WikipediaPage> = mutableListOf()

        val jsonArray = Gson()
            .fromJson(jsonString, JsonObject::class.java)
            .getAsJsonObject("query")
            .getAsJsonArray("search")

        jsonArray.forEach {
            newResults.add(WikipediaPage(
                it.asJsonObject.getAsJsonPrimitive("title").toString(),
                it.asJsonObject.getAsJsonPrimitive("pageid").toString()))
        }

        return newResults
    }

    fun openLink(id: Int) {
        Desktop.getDesktop().browse(URI(resultLink + wikipediaResults[id].pageid))
    }

}