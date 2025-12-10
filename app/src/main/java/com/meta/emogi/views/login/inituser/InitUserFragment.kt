package com.meta.emogi.views.login.inituser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.databinding.FragmentInitUserBinding
import com.meta.emogi.views.toolbar.ToolbarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitUserFragment : BaseFragment<FragmentInitUserBinding, InitUserViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_init_user

    override fun viewModelClass(): Class<InitUserViewModel> = InitUserViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<InitUserViewModel>().value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun registerObservers() {
        viewModel.navigateToMain.observe(this) {
            (requireActivity() as? com.meta.emogi.views.login.LoginActivity)?.moveToMainActivity()
        }
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        return ToolbarView.ToolbarRequest("회원정보 입력")
    }
}