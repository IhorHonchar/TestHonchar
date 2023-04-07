package ua.honchar.test.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ua.honchar.test.base.StubViewModel
import ua.honchar.test.presentation.postDetails.PostDetailsViewModel
import ua.honchar.test.presentation.posts.PostsViewModel
import ua.honchar.test.presentation.splash.SplashViewModel

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { PostsViewModel(get()) }
    viewModel { StubViewModel(get()) }
    viewModel { PostDetailsViewModel(get()) }
}