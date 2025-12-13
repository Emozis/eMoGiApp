package com.meta.emogi.views.profile.editprofile

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.databinding.FragmentEditProfileBinding
import com.meta.emogi.views.profile.ProfileActivity
import com.meta.emogi.views.toolbar.ToolbarView
import com.meta.emogi.views.toolbar.ToolbarView.ToolbarRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>() {

    private val activity by lazy { requireActivity() as ProfileActivity }

    override fun layoutId(): Int = R.layout.fragment_edit_profile

    override fun viewModelClass(): Class<EditProfileViewModel> = EditProfileViewModel::class.java

    override fun toolbarCallback(): ToolbarView.ToolbarRequest? {
        return ToolbarRequest("프로필 편집")
    }

    override fun registerObservers() {
        viewModel.currentTab.observe(this) { tabIndex ->
            updateTabUI(tabIndex)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.refreshToolbar(ToolbarRequest("프로필 편집"), "저장", true, false)

        binding.tabSocial.setOnClickListener {
            viewModel.setTab(0)
        }

        binding.tabChat.setOnClickListener {
            viewModel.setTab(1)
        }
    }

    private fun updateTabUI(tabIndex: Int) {
        if (tabIndex == 0) {
            // Social Tab Selected
            binding.tabSocial.setTextColor(resources.getColor(R.color.white, null))
            binding.tabChat.setTextColor(resources.getColor(R.color.gray, null))
            
            // Indicator Animation or Position update
            val params = binding.tabIndicator.layoutParams as ConstraintLayout.LayoutParams
            params.startToStart = binding.tabSocial.id
            params.endToEnd = binding.tabSocial.id
            binding.tabIndicator.layoutParams = params

            binding.layoutSocialProfile.visibility = View.VISIBLE
            binding.layoutChatProfile.visibility = View.GONE
        } else {
            // Chat Tab Selected
            binding.tabSocial.setTextColor(resources.getColor(R.color.gray, null))
            binding.tabChat.setTextColor(resources.getColor(R.color.white, null))

            val params = binding.tabIndicator.layoutParams as ConstraintLayout.LayoutParams
            params.startToStart = binding.tabChat.id
            params.endToEnd = binding.tabChat.id
            binding.tabIndicator.layoutParams = params

            binding.layoutSocialProfile.visibility = View.GONE
            binding.layoutChatProfile.visibility = View.VISIBLE
        }
    }
}
