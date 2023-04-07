package ua.honchar.data.model.response

import com.google.gson.annotations.SerializedName
import ua.honchar.data.network.ResponseError

data class SignResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("email")
    val emailErrors: List<String>?,
    @SerializedName("password")
    val passwordErrors: List<String>?,
): ResponseError {
    override fun getError(): String {
        val string = StringBuilder()
        emailErrors?.forEach {
            string.append("$it\n")
        }
        passwordErrors?.forEach {
            string.append("$it\n")
        }
        return string.toString()
    }
}