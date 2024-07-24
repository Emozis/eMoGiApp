package com.meta.emogi.views.chatroom;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.data.ChatMessage;
import com.meta.emogi.databinding.FragmentChatRoomBinding;
import com.meta.emogi.di.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomFragment extends Fragment {

    private ChatRoomViewModel viewModel;
    private FragmentChatRoomBinding binding;
    private ChatListAdapter adapter;
    private List<ChatMessage> data;
    private RecyclerView recyclerView;

    public static ChatRoomFragment newInstance() {
        return new ChatRoomFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatRoomBinding.inflate(inflater, container, false);
        setKeyboard(binding);
        return binding.getRoot();
    }
    private void setKeyboard(FragmentChatRoomBinding binding) {
        binding.sendText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // 소프트 키보드를 숨깁니다.
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(binding.sendText.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(ChatRoomViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // RecyclerView 설정
        recyclerView = binding.chatField;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 예제 데이터
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new ChatMessage("User message " + i, ChatMessage.TYPE_USER));
            data.add(new ChatMessage("Other message " + i, ChatMessage.TYPE_OTHER));
        }

        recyclerView.scrollToPosition(data.size() - 1);
        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);

        setObserve();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.init();
    }

    private void setObserve() {
        viewModel.sendText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sendText) {
                data.add(new ChatMessage(sendText, ChatMessage.TYPE_USER));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
            }
        });
        viewModel.receivedText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String receivedText) {
                data.add(new ChatMessage(receivedText, ChatMessage.TYPE_OTHER));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
            }
        });
    }
}