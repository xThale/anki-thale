import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class AuthService(
    private val username: String,
    private val password: String
) {

    fun login() : ClientLoginResponse? {

        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI("https://back.thale.info/auth/login/client"))
            .POST(HttpRequest.BodyPublishers.ofString(""))
            .header("Authorization", (username to password).basicAuth())
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200) {
            return Gson().fromJson(response.body(), ClientLoginResponse::class.java)
        }

        return null
    }

}