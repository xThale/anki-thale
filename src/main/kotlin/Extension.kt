import java.util.*

fun Pair<String, String>.basicAuth() : String {
    return "Basic " + Base64.getEncoder().encodeToString((this.first + ":" + this.second).encodeToByteArray())
}