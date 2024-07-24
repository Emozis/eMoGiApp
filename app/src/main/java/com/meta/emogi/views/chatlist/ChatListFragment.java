package com.meta.emogi.views.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.di.ViewModelFactory;

public class ChatListFragment extends Fragment {

    private ChatListViewModel mViewModel;
    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ViewModelFactory 사용
        ViewModelFactory factory = new ViewModelFactory();
        mViewModel = factory.create(ChatListViewModel.class);
        // TODO: Use the ViewModel
    }

}