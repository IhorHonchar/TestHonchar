package ua.honchar.test.presentation.postDetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ua.honchar.domain.repository.TestRepository
import ua.honchar.test.R
import ua.honchar.test.base.BaseViewModel

class PostDetailsViewModel(repository: TestRepository) : BaseViewModel(repository) {

    private val _btnState = MutableStateFlow(BtnFavoriteState.INACTIVE)
    val btnState: Flow<BtnFavoriteState> = _btnState

    fun getBtnState(isFavorite: Boolean) {
        val state = if (isFavorite) BtnFavoriteState.ACTIVE else BtnFavoriteState.INACTIVE
        _btnState.value = state
    }

    fun onClick() {
        _btnState.value = _btnState.value.getAnotherState()
    }

}

enum class BtnFavoriteState(
    val bg: Int,
    val text: Int,
    val heartImage: Int,
    val heartColor: Int
) {
    ACTIVE(
        R.drawable.btn_favorite_selected_ripple,
        R.string.btn_remove_favorites,
        R.drawable.ic_heart_selected,
        R.color.white
    ),
    INACTIVE(
        R.drawable.btn_favorite_inactive_ripple,
        R.string.btn_add_favorites,
        R.drawable.ic_heart_inactive,
        R.color.btn_color
    );

    fun getAnotherState() = when (this) {
        ACTIVE -> INACTIVE
        INACTIVE -> ACTIVE
    }
}