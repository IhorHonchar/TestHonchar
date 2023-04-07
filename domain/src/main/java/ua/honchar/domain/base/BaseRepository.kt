package ua.honchar.domain.base

import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response
import ua.honchar.data.network.ResponseError

abstract class BaseRepository {

    protected suspend inline fun <reified T : ResponseError> execute(
        crossinline action: suspend () -> Response<T>
    ): Result {
        val response = action()
        return if (response.isSuccessful) {
            Result.Success(response.body())
        } else {
            Result.Error(
                getModelFromError<T>(response.errorBody())?.getError(),
                errorCode = response.code()
            )
        }
    }

    protected suspend inline fun executeIgnoreRes(
        crossinline action: suspend () -> Unit
    ): Result {
        action()
        return Result.Success(Unit)
    }

    protected inline fun <reified E> getModelFromError(body: ResponseBody?): E? {
        val bodyString = JsonParser().parse(body?.string())
        val gson = Gson()
        return gson.fromJson(gson.toJson(bodyString), E::class.java)
    }
}

sealed class Result {
    data class Success<T>(val data: T?) : Result()
    data class Error(val error: String?, val errorCode: Int) : Result()
}