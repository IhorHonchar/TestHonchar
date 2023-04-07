package ua.honchar.domain

import org.koin.dsl.module
import ua.honchar.domain.repository.TestRepository

val domainModule = module {
    single { TestRepository(get(), get()) }
}