data class ClientLoginResponse(
    val token: ClientToken
)

data class ClientToken(
    val idToken: String
)
