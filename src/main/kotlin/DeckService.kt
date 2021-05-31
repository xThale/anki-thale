import com.google.gson.Gson
import model.CreateDeckRequest
import model.CreateDeckResponse
import model.GetDeckResponse
import java.lang.RuntimeException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class DeckService(
    private val bearer: String
) {

    private val client = HttpClient.newBuilder().build()

    fun create(request: CreateDeckRequest) : CreateDeckResponse? {


        val request = HttpRequest.newBuilder().uri(URI("https://back.thale.info/deck"))
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(request)))
            .header("Authorization", "Bearer $bearer")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 201) {
            return Gson().fromJson(response.body(), CreateDeckResponse::class.java)
        }

        return null
    }

    fun get() : GetDeckResponse? {


        val request = HttpRequest.newBuilder().uri(URI("https://back.thale.info/deck"))
            .GET()
            .header("Authorization", "Bearer $bearer")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            return Gson().fromJson(response.body(), GetDeckResponse::class.java)
        }

        return null
    }

    fun delete(id: String) {


        val request = HttpRequest.newBuilder().uri(URI("https://back.thale.info/deck/$id"))
            .DELETE()
            .header("Authorization", "Bearer $bearer")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 204) {
            return
        }

        throw RuntimeException("Could not delete $id")
    }

}