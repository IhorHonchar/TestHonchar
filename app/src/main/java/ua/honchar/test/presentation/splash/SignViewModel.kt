package ua.honchar.test.presentation.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ua.honchar.domain.model.SignModel
import ua.honchar.domain.repository.TestRepository
import ua.honchar.test.R
import ua.honchar.test.base.BaseViewModel

class SplashViewModel(repository: TestRepository) : BaseViewModel(repository) {

    private val _state = MutableStateFlow(SplashState.SIGN_UP)
    val state: Flow<SplashState> = _state

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: Flow<String> = _errorMessage

    private val _tokenEnable = MutableSharedFlow<Boolean>()
    val tokenEnable: Flow<Boolean> = _tokenEnable

    fun changeState() {
        _state.value = _state.value.anotherState()
    }

    fun sign(email: String, pass: String) {
        execute<SignModel>(
            action = {
                if (_state.value == SplashState.SIGN_UP)
                    repository.signUp(email = email, pass = pass)
                else
                    repository.signIn(email = email, pass = pass)
            },
            successCallback = {
                _tokenEnable.emit(true)
            },
            errorCallback = {
                _errorMessage.emit(it)
            }
        )
    }

    fun checkToken(isLogout: Boolean) {
        viewModelScope.launch {
            val token = repository.getToken()
            if (isLogout) {
                delay(SHORT_WAITING)
                _tokenEnable.emit(false)
            } else {
                delay(LONG_WAITING)
                _tokenEnable.emit(token.isNotEmpty())
            }
        }
    }

    companion object {
        private const val LONG_WAITING = 1500L
        private const val SHORT_WAITING = 300L
    }
}

enum class SplashState(
    val title: Int,
    val subTitle: Int,
    val btn: Int,
    val switch: Int
) {
    SIGN_UP(
        title = R.string.welcome_sign_up_title,
        subTitle = R.string.welcome_sign_up_sub_title,
        btn = R.string.sign_up,
        switch = R.string.sign_in
    ),
    SIGN_IN(
        title = R.string.welcome_sign_in_title,
        subTitle = R.string.welcome_sign_in_sub_title,
        btn = R.string.sign_in,
        switch = R.string.sign_up
    );

    fun anotherState() = when (this) {
        SIGN_UP -> SIGN_IN
        SIGN_IN -> SIGN_UP
    }
}