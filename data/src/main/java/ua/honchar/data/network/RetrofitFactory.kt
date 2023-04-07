package ua.honchar.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.honchar.data.BuildConfig
import java.util.concurrent.TimeUnit

interface RetrofitFactory {

    fun createRetrofit(): Retrofit
}

class RetrofitFactoryImpl(
    private val gson: Gson,
    private val okHttpClient: OkHttpClient,
    private val paramsInterceptor: ParamsInterceptor
): RetrofitFactory {

    override fun createRetrofit(): Retrofit {
        val okHttpBuilder = okHttpClient.newBuilder()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        okHttpBuilder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(paramsInterceptor)
            .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private const val BASE_URL = "http://api.android-test-app.4-com.pro/"
        private const val TIMEOUT_SECS: Long = 20
    }
}