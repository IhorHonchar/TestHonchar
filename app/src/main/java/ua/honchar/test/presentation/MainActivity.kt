package ua.honchar.test.presentation

import ua.honchar.test.R
import ua.honchar.test.base.BaseActivity
import ua.honchar.test.core.ext.*
import ua.honchar.test.databinding.ActivityMainBinding
import ua.honchar.test.presentation.posts.PostsFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var toolBarListener: Callbacks? = null

    override fun initViews() {
        super.initViews()
        binding.ivBack.click { toolBarListener?.onBackClick() }
        binding.ivEnd.click { toolBarListener?.onEndClick() }
    }

    fun redraw(model: ToolbarItem, listener: Callbacks) {
        toolBarListener = listener
        handleBackIcon(model.visibleBack)
        binding.tvTitle.switchText(model.title)
        handleEndIcon(model.endIcon)
    }

    fun visibleNavigation() {
        binding.navigation.visible()
    }

    fun hideNavigation() {
        binding.navigation.gone()
    }

    private fun handleBackIcon(visibleBack: Boolean) {
        if (visibleBack) {
            binding.tvTitle.animateMargin(resources.getDimensionPixelSize(R.dimen.toolbar_title_large_margin))
            binding.ivBack.showView()
        } else {
            binding.ivBack.goneView()
            binding.tvTitle.animateMargin(resources.getDimensionPixelSize(R.dimen.default_horizontal_margin))
        }
    }

    private fun handleEndIcon(iconRes: Int?) {
        iconRes?.let {
            binding.ivEnd.setImageResource(it)
            binding.ivEnd.showView()
        } ?: binding.ivEnd.goneView()
    }

    interface Callbacks {
        fun onBackClick()
        fun onEndClick()
    }
}

data class ToolbarItem(
    val title: String,
    val visibleBack: Boolean,
    val endIcon: Int? = null
)
