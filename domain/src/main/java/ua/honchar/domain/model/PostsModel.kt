package ua.honchar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Posts(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PostModel>
)

@Parcelize
data class PostModel(
    val favorite: Boolean,
    val id: Int,
    val image: String,
    val status: String,
    val name: String
): Parcelable