package ua.honchar.test.presentation.posts

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ua.honchar.domain.model.Posts
import ua.honchar.domain.repository.TestRepository
import ua.honchar.test.base.BaseViewModel

class PostsViewModel(
    repository: TestRepository
) : BaseViewModel(repository) {

    private val _successLogout = MutableSharedFlow<Unit>()
    val successLogout: Flow<Unit> = _successLogout

    fun logout() {
        execute<Any>(
            action = {
                repository.logout()
            },
            successCallback = {
                _successLogout.emit(Unit)
            },
            errorCallback = {}
        )
    }

    fun getPosts() {
        execute<Posts>(
            action = {
                repository.getPosts()
            },
            successCallback = {

            },
            errorCallback = {}
        )
    }
}