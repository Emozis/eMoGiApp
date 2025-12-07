package com.meta.emogi.views.login.serviceterms

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meta.emogi.R

class ServiceTermsFragment : Fragment() {

    companion object {
        fun newInstance() = ServiceTermsFragment()
    }

    private val viewModel: ServiceTermsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_service_terms, container, false)
    }
}