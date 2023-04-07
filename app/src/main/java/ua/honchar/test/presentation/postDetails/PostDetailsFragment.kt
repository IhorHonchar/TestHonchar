package ua.honchar.test.presentation.postDetails

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.honchar.domain.model.PostModel
import ua.honchar.test.R
import ua.honchar.test.base.BaseToolbarFragment
import ua.honchar.test.base.StubViewModel
import ua.honchar.test.core.ext.click
import ua.honchar.test.core.ext.loadImage
import ua.honchar.test.core.ext.observe
import ua.honchar.test.databinding.FragmentPostDetailsBinding
import ua.honchar.test.presentation.ToolbarItem
import ua.honchar.test.presentation.posts.PostsViewModel

class PostDetailsFragment : BaseToolbarFragment<FragmentPostDetailsBinding>(
    R.layout.fragment_post_details,
    FragmentPostDetailsBinding::inflate
) {

    override val viewModel: PostDetailsViewModel by viewModel()

    private val args: PostDetailsFragmentArgs by navArgs()

    private val post: PostModel
        get() = args.post

    override fun initToolBar() = ToolbarItem(
        title = post.name,
        visibleBack = true,
        endIcon = null
    )

    override fun initViews() {
        super.initViews()
        viewModel.getBtnState(post.favorite)
        binding.tvStatus.text = post.status
        binding.ivAvatar.loadImage(post.image, R.drawable.rick_morty_title)
        binding.btnNext.click(viewModel::onClick)
    }

    override fun observeData() {
        super.observeData()
        viewModel.btnState.observe(owner = viewLifecycleOwner, action = ::handleBtnState)
    }

    override fun onBackClick() {
        findNavController().popBackStack()
    }

    override fun onEndClick() = Unit

    private fun handleBtnState(state: BtnFavoriteState) {
        binding.btnNext.setBackgroundResource(state.bg)
        binding.tvBtnName.setText(state.text)
        binding.ivFavorite.setImageResource(state.heartImage)
        binding.ivFavorite.imageTintList = ColorStateList.valueOf(state.heartColor)
    }
}