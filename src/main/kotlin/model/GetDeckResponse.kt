package model

data class GetDeckResponse(
    val decks: List<DeckResponse>
)

data class DeckResponse(
    val id: String,
    val name: String
)
