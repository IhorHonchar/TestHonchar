package ua.honchar.test.di

import org.koin.dsl.module
import ua.honchar.test.base.ProgressDelegate
import ua.honchar.test.base.ProgressDelegateImpl
import ua.honchar.test.base.ProgressDialog

val appModule = module {
    factory { ProgressDialog() }
    single<ProgressDelegate> { ProgressDelegateImpl(get()) }
}