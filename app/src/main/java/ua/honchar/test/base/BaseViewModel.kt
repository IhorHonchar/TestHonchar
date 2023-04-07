package ua.honchar.test.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import ua.honchar.domain.base.Result
import ua.honchar.domain.repository.TestRepository


abstract class BaseViewModel(protected val repository: TestRepository) : ViewModel() {

    private val _loading = MutableSharedFlow<UIState.Loading>()
    val loading: Flow<UIState.Loading> = _loading

    private val _baseError = MutableSharedFlow<UIState.Error>()
    val baseError: Flow<UIState.Error> = _baseError

    private val _authError = MutableSharedFlow<Unit>()
    val authError: Flow<Unit> = _authError

    suspend fun startLoading() {
        _loading.emit(UIState.Loading(true))
    }

    suspend fun stopLoading() {
        _loading.emit(UIState.Loading(false))
    }

    suspend fun checkConnection() {
        _baseError.emit(UIState.Error)
    }

    suspend fun sendAuthError() {
        repository.clearToken()
        _authError.emit(Unit)
    }

    protected inline fun <reified T> execute(
        crossinline action: suspend () -> Result,
        crossinline successCallback: suspend (T) -> Unit,
        crossinline errorCallback: suspend (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                startLoading()
                val response = action()
                stopLoading()
                when (response) {
                    is Result.Success<*> -> {
                        (response.data as? T)?.let { successCallback.invoke(it) }
                    }
                    is Result.Error -> {
                        if (response.errorCode == AUTHENTICATION_ERROR) {
                            sendAuthError()
                        }
                        errorCallback.invoke(response.error.orEmpty())
                    }
                }
            } catch (e: Exception) {
                stopLoading()
                checkConnection()
            }
        }
    }
    companion object {
        const val AUTHENTICATION_ERROR = 401
    }
}

sealed class UIState {
    data class Loading(val loading: Boolean) : UIState()
    data class Success<T>(val data: T) : UIState()
    object Error : UIState()
}
