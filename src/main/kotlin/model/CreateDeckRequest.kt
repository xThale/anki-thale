package model

data class CreateDeckRequest(
    val name: String,
    val cards: List<CreateCardRequest>
)

data class CreateCardRequest(
    val front: String,
    val back: String,
    val leech: Boolean = false
)
