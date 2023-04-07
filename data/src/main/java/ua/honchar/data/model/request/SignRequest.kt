package ua.honchar.data.model.request

import com.google.gson.annotations.SerializedName

data class SignRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
