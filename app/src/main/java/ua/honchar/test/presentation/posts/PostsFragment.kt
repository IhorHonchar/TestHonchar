package ua.honchar.test.presentation.posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.honchar.domain.model.PostModel
import ua.honchar.test.R
import ua.honchar.test.base.BaseFragment
import ua.honchar.test.base.BaseToolbarFragment
import ua.honchar.test.core.ext.click
import ua.honchar.test.core.ext.observe
import ua.honchar.test.databinding.FragmentPostsBinding
import ua.honchar.test.presentation.MainActivity
import ua.honchar.test.presentation.ToolbarItem

class PostsFragment : BaseToolbarFragment<FragmentPostsBinding>(
    R.layout.fragment_posts,
    FragmentPostsBinding::inflate
) {

    override val viewModel: PostsViewModel by viewModel()

    override fun initToolBar() = ToolbarItem(
        title = getString(R.string.characters_menu_item),
        visibleBack = false,
        endIcon = R.drawable.ic_logout
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.visibleNavigation()
//        viewModel.getPosts()
    }

    override fun initViews() {
        super.initViews()
        binding.btnNext.click {
            (requireActivity() as? MainActivity)?.hideNavigation()
            val post = PostModel(
                id = 1,
                image = "http://api.android-test-app.4-com.pro/media/Rick_Sanchez.png",
                name = "Rick Sanchez",
                status = "Alive",
                favorite = false
            )
            val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(post)
            findNavController().navigate(action)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.successLogout.observe(viewLifecycleOwner) { startSignActivity() }
    }

    override fun onBackClick() = Unit

    override fun onEndClick() {
        viewModel.logout()
    }
}