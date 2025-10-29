package com.meta.emogi.views.makecharacter.intro

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meta.emogi.R
import com.meta.emogi.base.BaseFragment
import com.meta.emogi.databinding.FragmentChatIntroBinding
import com.meta.emogi.views.toolbar.ToolbarView

class ChatIntroFragment : BaseFragment<FragmentChatIntroBinding, ChatIntroViewModel>() {

    companion object {
        fun newInstance() = ChatIntroFragment()
    }

    private val viewModel: ChatIntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_chat_intro, container, false)
    }

    override fun toolbarCallback(): ToolbarView.ToolbarRequest {
        TODO("Not yet implemented")
    }

    override fun layoutId(): Int {
        TODO("Not yet implemented")
    }

    override fun viewModelClass(): Class<ChatIntroViewModel> {
        return ChatIntroViewModel::class.java
    }

    override fun registerObservers() {
        TODO("Not yet implemented")
    }
}