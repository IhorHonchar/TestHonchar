package ua.honchar.data.model.response

import com.google.gson.annotations.SerializedName
import ua.honchar.data.network.ResponseError

data class LogoutResponse(
    @SerializedName("detail")
    val detail: String?
): ResponseError {
    override fun getError(): String = detail.orEmpty()
}
