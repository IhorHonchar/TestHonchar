package ua.honchar.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import ua.honchar.data.api.TestApi
import ua.honchar.data.network.ParamsInterceptor
import ua.honchar.data.network.RetrofitFactory
import ua.honchar.data.network.RetrofitFactoryImpl
import ua.honchar.data.sharedPref.SharedPrefProvider
import ua.honchar.data.sharedPref.SharedPrefProviderImpl

val dataModule = module {
    single<SharedPrefProvider> { SharedPrefProviderImpl(get()) }
    single { OkHttpClient() }
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    single<RetrofitFactory> { RetrofitFactoryImpl(get(), get(), get()) }
    single<Retrofit> { get<RetrofitFactory>().createRetrofit() }
    single { get<Retrofit>().create(TestApi::class.java) }
    single { ParamsInterceptor(get()) }
}