package mapping

import model.*
import java.lang.RuntimeException


fun CardsInfoResponse.toCreateDeckRequest(deckName: String) : CreateDeckRequest {

    return when(deckName) {
        "#TodayOwn" -> createDeckRequest(deckName, this)
        else -> throw RuntimeException("No mapper for $deckName")
    }

}

fun Field.toValue() =
    this.value.replace("\\[sound:(.*?)\\]".toRegex(), "")

fun createDeckRequest(deckName: String, input: CardsInfoResponse) : CreateDeckRequest {

    return CreateDeckRequest(
        name = deckName,
        cards = input.result.map {
            CreateCardRequest(
                front = it.fields.Front.toValue(),
                back = it.fields.Back.toValue()
            )
        }
    )


}