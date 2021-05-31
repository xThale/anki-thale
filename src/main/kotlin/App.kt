import mapping.toCreateDeckRequest
import java.lang.RuntimeException

fun main(args: Array<String>) {

    println("Starting")

    val username = args.getOrNull(0) ?: throw RuntimeException("No username provided")
    val password = args.getOrNull(1) ?: throw RuntimeException("No password provided")

    val deckList = args.toList().subList(2, args.size)

    println("Creating decks for ${deckList.joinToString(", ")}")

    val loginClient = AuthService(username, password).login()

    val deckClient = DeckService(loginClient?.token?.idToken ?: throw RuntimeException("Token could not be fetched"))

    val existingDecks = deckClient.get() ?: throw RuntimeException("Could not fetch decks")

    val ankiClient = AnkiService()

    deckList.forEach { deckName ->
        existingDecks.decks.filter { it.name == deckName }.forEach {
            deckClient.delete(it.id)
            println("Deleted ${it.name}")
        }

        val cardsId = ankiClient.findCard(deckName) ?: throw RuntimeException("Could not get cards for $deckName")
        val cardsInfo = ankiClient.cardsInfo(cardsId.result) ?: throw RuntimeException("Could not get cards info for $deckName")


        val response = deckClient.create(cardsInfo.toCreateDeckRequest(deckName))

        println("Deck created ${response?.id ?: throw RuntimeException("Deck $deckName could not be created")}")
    }



}
