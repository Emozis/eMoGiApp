package com.meta.emogi.views.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentChatListBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.toolbar.ToolbarView;

public class ChatListFragment extends BaseFragment<FragmentChatListBinding,ChatListViewModel> {

    private ChatListViewModel viewModel;
    private FragmentChatListBinding binding;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("채팅리스트");
    }

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_chat_list;
    }
    @Override
    protected Class<ChatListViewModel> viewModelClass() {
        return ChatListViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }

}