import com.google.gson.Gson
import model.CardsInfoResponse
import model.CreateDeckRequest
import model.CreateDeckResponse
import model.FindCardResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class AnkiService() {



    private val client = HttpClient.newBuilder().build()

    fun findCard(deck: String) : FindCardResponse? {


        val request = HttpRequest.newBuilder().uri(URI("http://localhost:8765"))
            .POST(HttpRequest.BodyPublishers.ofString(deck.toFindCard()))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            return Gson().fromJson(response.body(), FindCardResponse::class.java)
        }

        return null
    }

    fun cardsInfo(cards: List<Long>) : CardsInfoResponse? {


        val request = HttpRequest.newBuilder().uri(URI("http://localhost:8765"))
            .POST(HttpRequest.BodyPublishers.ofString(cards.toCardsInfo()))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            return Gson().fromJson(response.body(), CardsInfoResponse::class.java)
        }

        return null
    }


}


fun String.toFindCard() = """
    {
        "action": "findCards",
        "version": 6,
        "params": {
            "query": "deck:$this"
        }
    }
""".trimIndent()

fun List<Long>.toCardsInfo() = """
    {
        "action": "cardsInfo",
        "version": 6,
        "params": {
            "cards": [${this.joinToString(", ")}]
        }
    }
""".trimIndent()