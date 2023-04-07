package ua.honchar.test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ua.honchar.data.dataModule
import ua.honchar.domain.domainModule
import ua.honchar.test.di.appModule
import ua.honchar.test.di.viewModelModule

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(
                viewModelModule,
                appModule,
                domainModule,
                dataModule,
            )
        }
    }
}