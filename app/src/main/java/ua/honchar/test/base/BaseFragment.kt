package ua.honchar.test.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.get
import ua.honchar.test.core.ext.observe
import ua.honchar.test.presentation.MainActivity
import ua.honchar.test.presentation.ToolbarItem
import ua.honchar.test.presentation.splash.SignFragment
import ua.honchar.test.presentation.splash.SplashActivity


typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<B : ViewBinding>(
    @LayoutRes
    protected val layoutId: Int,
    private val inflate: FragmentInflate<B>
) : Fragment(layoutId) {

    protected abstract val viewModel: BaseViewModel

    private var _binding: B? = null
    val binding: B
        get() = _binding!!

    private val progressDelegate: ProgressDelegate = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun initViews() = Unit

    open fun observeData() {
        viewModel.baseError.observe(viewLifecycleOwner) { checkConnection() }
        viewModel.loading.observe(viewLifecycleOwner, action = ::handleLoading)
        viewModel.authError.observe(viewLifecycleOwner) { startSignActivity() }
    }

    protected fun initToolbar(toolbar: ToolbarItem, listener: MainActivity.Callbacks) {
        (requireActivity() as? MainActivity)?.redraw(toolbar, listener)
    }

    protected fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    protected fun startSignActivity() {
        requireActivity().startActivity(
            Intent(
                requireContext(),
                SplashActivity::class.java
            ).apply { putExtra(SignFragment.IS_LOGOUT, true) },
        )
        requireActivity().finish()
    }

    private fun handleLoading(state: UIState.Loading) =
        if (state.loading) showLoading() else stopLoading()

    private fun checkConnection() {
        if (isNetworkAvailable(requireContext())) showBaseError()
        else showLostConnectionError()
    }

    private fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

    private fun showLoading() {
        progressDelegate.showLoading(requireActivity())
    }

    private fun stopLoading() {
        progressDelegate.hideLoading(requireActivity())
    }

    private fun showBaseError() {
        showError("Something wrong")
    }

    private fun showLostConnectionError() {
        showError("Connection error")
    }

}