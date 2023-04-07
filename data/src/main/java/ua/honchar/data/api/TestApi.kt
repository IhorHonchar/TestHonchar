package ua.honchar.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ua.honchar.data.model.request.SignRequest
import ua.honchar.data.model.response.LogoutResponse
import ua.honchar.data.model.response.PostsResponse
import ua.honchar.data.model.response.SignResponse

interface TestApi {

    @POST("v0/singup/")
    suspend fun singUp(@Body request: SignRequest): Response<SignResponse>

    @POST("v0/singin/")
    suspend fun singIn(@Body request: SignRequest): Response<SignResponse>

    @POST("v0/logout/")
    suspend fun logout()

    @GET("v0/post/list/")
    suspend fun getPosts(
        @Query("page")
        page: Int,
        @Query("page_size")
        pageSize: Int
    ): Response<PostsResponse>
}