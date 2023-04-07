package ua.honchar.domain.repository

import ua.honchar.data.api.TestApi
import ua.honchar.data.model.request.SignRequest
import ua.honchar.data.model.response.PostsResponse
import ua.honchar.data.model.response.SignResponse
import ua.honchar.data.sharedPref.SharedPrefProvider
import ua.honchar.domain.base.BaseRepository
import ua.honchar.domain.base.Result
import ua.honchar.domain.mappers.toModel


class TestRepository(
    private val api: TestApi,
    private val sharedPref: SharedPrefProvider
) : BaseRepository() {

    suspend fun signIn(email: String, pass: String): Result {
        return execute {
            api.singIn(SignRequest(email, pass))
        }.let { result ->
            if (result is Result.Success<*>) {
                (result.data as SignResponse).let {
                    val model = it.toModel()
                    sharedPref.saveToken(model.token)
                    Result.Success(model)
                }
            } else result
        }
    }

    suspend fun signUp(email: String, pass: String): Result {
        return execute {
            api.singUp(SignRequest(email, pass))
        }.let { result ->
            if (result is Result.Success<*>) {
                (result.data as SignResponse).let {
                    val model = it.toModel()
                    sharedPref.saveToken(model.token)
                    Result.Success(model)
                }
            } else result
        }
    }

    suspend fun getToken(): String = sharedPref.getToken()

    suspend fun clearToken() {
        sharedPref.saveToken("")
    }

    suspend fun logout(): Result {
        return executeIgnoreRes {
            api.logout()
        }.let {
            clearToken()
            Result.Success(Unit)
        }
    }

    suspend fun getPosts(): Result {
        return execute {
            api.getPosts(1, 10)
        }.let { result ->
            if (result is Result.Success<*>) {
                (result.data as PostsResponse).let {
                    Result.Success(it.toModel())
                }
            } else result
        }
    }
}