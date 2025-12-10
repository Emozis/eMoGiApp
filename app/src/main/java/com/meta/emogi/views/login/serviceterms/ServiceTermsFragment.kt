package com.meta.emogi.views.login.serviceterms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.databinding.FragmentServiceTermsBinding
import com.meta.emogi.views.login.LoginActivity
import com.meta.emogi.views.toolbar.ToolbarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceTermsFragment : BaseFragment<FragmentServiceTermsBinding, ServiceTermsViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_service_terms

    override fun viewModelClass(): Class<ServiceTermsViewModel> = ServiceTermsViewModel::class.java

    private val activity by lazy { requireActivity() as LoginActivity }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModels<ServiceTermsViewModel>().value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()
    }

    private fun setupListeners() {
        binding.cbAllAgree.setOnClickListener {
            viewModel.onAllAgreeChecked(binding.cbAllAgree.isChecked)
        }

        val checkListener = View.OnClickListener {
            viewModel.checkMandatory()
        }

        binding.cbTerm1.setOnClickListener(checkListener)
        binding.cbTerm2.setOnClickListener(checkListener)
        binding.cbTerm3.setOnClickListener(checkListener)
    }

    override fun registerObservers() {
        viewModel.navigateToInitUser.observe(this) {
            findNavController(requireView()).navigate(R.id.action_serviceTermsFragment_to_initUserFragment)
        }
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        return ToolbarView.ToolbarRequest("서비스 이용 약관")
    }
}