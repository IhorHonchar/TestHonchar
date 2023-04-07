package ua.honchar.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ua.honchar.data.sharedPref.SharedPrefProvider

class ParamsInterceptor(
    private val sharedPreferencesProvider: SharedPrefProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .apply {
                val token = sharedPreferencesProvider.getToken()
                if (token.isNotBlank())
                    addHeader("Authorization", "Token $token")
            }
            .build()

        return chain.proceed(request)
    }
}
