package com.meta.persona.views.chatroom;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.persona.R;
import com.meta.persona.data.ChatMessage;
import com.meta.persona.databinding.FragmentChatRoomBinding;
import com.meta.persona.di.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomFragment extends Fragment {

    private ChatRoomViewModel viewModel;
    private FragmentChatRoomBinding binding;
    private ChatListAdapter adapter;

    public static ChatRoomFragment newInstance() {
        return new ChatRoomFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(ChatRoomViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // RecyclerView 설정
        RecyclerView recyclerView = binding.chatField;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 예제 데이터
        List<ChatMessage> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new ChatMessage("User message " + i, ChatMessage.TYPE_USER));
            data.add(new ChatMessage("Other message " + i, ChatMessage.TYPE_OTHER));
        }

        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}