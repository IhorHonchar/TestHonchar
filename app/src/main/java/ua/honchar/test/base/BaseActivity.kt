package ua.honchar.test.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias ActivityInflate<T> = (LayoutInflater) -> T

abstract class BaseActivity<B: ViewBinding>(
    private val inflate: ActivityInflate<B>
) : AppCompatActivity() {


    private var _binding: B? = null
    val binding: B
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        initViews()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun initViews() = Unit
    open fun observeData() = Unit
}