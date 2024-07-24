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
import com.meta.emogi.databinding.FragmentChatListBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.menu.MenuViewModel;

public class ChatListFragment extends Fragment {

    private ChatListViewModel viewModel;
    private FragmentChatListBinding binding;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

}