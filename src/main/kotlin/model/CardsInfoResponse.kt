package model

data class CardsInfoResponse(
    val result: List<CardInfo>
)

data class CardInfo(
    val fields: Fields
)

data class Fields(
    val Front: Field,
    val Back: Field
)

data class Field(
    val value: String
)