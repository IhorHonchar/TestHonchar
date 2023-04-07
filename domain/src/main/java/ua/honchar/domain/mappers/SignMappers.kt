package ua.honchar.domain.mappers

import ua.honchar.data.model.response.PostResponse
import ua.honchar.data.model.response.PostsResponse
import ua.honchar.data.model.response.SignResponse
import ua.honchar.domain.model.PostModel
import ua.honchar.domain.model.Posts
import ua.honchar.domain.model.SignModel

fun SignResponse.toModel() = SignModel(
    token = token.orEmpty()
)

fun PostsResponse.toModel() = Posts(
    count = count ?: 0,
    next = next.orEmpty(),
    previous = previous.orEmpty(),
    results = results?.map {
        it.toModel()
    }.orEmpty()
)

fun PostResponse.toModel() = PostModel(
    favorite = favorite ?: false,
    id = id ?: 0,
    image = image.orEmpty(),
    status = subtitle.orEmpty(),
    name = title.orEmpty()
)