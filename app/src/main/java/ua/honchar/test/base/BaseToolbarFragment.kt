package ua.honchar.test.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import ua.honchar.test.presentation.MainActivity
import ua.honchar.test.presentation.ToolbarItem

abstract class BaseToolbarFragment<B : ViewBinding>(
    @LayoutRes
    layoutId: Int,
    inflate: FragmentInflate<B>
) : BaseFragment<B>(layoutId, inflate), MainActivity.Callbacks {

    abstract fun initToolBar(): ToolbarItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(initToolBar(), this)
    }
}