package com.meta.emogi.views.makecharacter.content

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.databinding.FragmentCharacterContentBinding
import com.meta.emogi.views.toolbar.ToolbarView

class CharacterContentFragment : BaseFragment<FragmentCharacterContentBinding, CharacterContentViewModel>() {

    companion object {
        fun newInstance() = CharacterContentFragment()
    }

    private val viewModel: CharacterContentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_character_content, container, false)
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        TODO("Not yet implemented")
    }

    override fun layoutId(): Int {
        TODO("Not yet implemented")
    }

    override fun viewModelClass(): Class<CharacterContentViewModel> {
        return CharacterContentViewModel::class.java
    }

    override fun registerObservers() {
        TODO("Not yet implemented")
    }
}