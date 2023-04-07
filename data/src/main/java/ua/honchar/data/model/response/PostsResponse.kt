package ua.honchar.data.model.response

import com.google.gson.annotations.SerializedName
import ua.honchar.data.network.ResponseError

data class PostsResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<PostResponse>?,
    @SerializedName("detail")
    val detail: String?,
): ResponseError {
    override fun getError(): String = detail.orEmpty()
}

data class PostResponse(
    @SerializedName("favorite")
    val favorite: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String?
)