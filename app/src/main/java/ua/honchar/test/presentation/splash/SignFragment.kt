package ua.honchar.test.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.honchar.test.R
import ua.honchar.test.base.BaseFragment
import ua.honchar.test.core.ext.click
import ua.honchar.test.core.ext.observe
import ua.honchar.test.databinding.FragmentSignBinding
import ua.honchar.test.presentation.MainActivity

class SignFragment : BaseFragment<FragmentSignBinding>(
    R.layout.fragment_sign,
    FragmentSignBinding::inflate
) {
    override val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isLogout = requireActivity().intent?.extras?.getBoolean(IS_LOGOUT) ?: false
        viewModel.checkToken(isLogout)
    }

    override fun initViews() {
        super.initViews()
        initClickListener()
    }

    override fun observeData() {
        super.observeData()
        viewModel.state.observe(owner = viewLifecycleOwner, action = ::handleState)
        viewModel.errorMessage.observe(owner = viewLifecycleOwner, action = ::showError)
        viewModel.tokenEnable.observe(owner = viewLifecycleOwner, action = ::handleTokenAccess)
    }

    private fun initClickListener() {
        binding.tvSwitch.click(viewModel::changeState)
        binding.btnNext.click(::signClick)
    }

    private fun signClick() {
        val email = binding.etEmail.text.toString()
        val pass = binding.etPass.text.toString()
        viewModel.sign(email, pass)
    }

    private fun handleState(state: SplashState) {
        with(binding) {
            tvTitle.setText(state.title)
            tvSubTitle.setText(state.subTitle)
            btnNext.setText(state.btn)
            tvSwitch.setText(state.switch)
        }
    }

    private fun handleTokenAccess(enableToken: Boolean) {
        if (enableToken) openPostsScreen()
        else showInputFields()
    }

    private fun openPostsScreen() {
        requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun showInputFields() {
        binding.motion.apply {
            setTransition(R.id.start, R.id.end)
            transitionToEnd()
        }
    }

    companion object {
        const val IS_LOGOUT = "is_logout"
    }
}