package com.meta.emogi.views.chatroom;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.data.ChatContent;
import com.meta.emogi.databinding.FragmentChatRoomBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomFragment extends BaseFragment<FragmentChatRoomBinding, ChatRoomViewModel> {

    private ChatListAdapter adapter;
    private List<ChatContent> data;
    private RecyclerView recyclerView;
    private ChatRoomActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("투툴바");
    }

    public static ChatRoomFragment newInstance() {
        return new ChatRoomFragment();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_chat_room;
    }

    @Override
    protected Class<ChatRoomViewModel> viewModelClass() {
        return ChatRoomViewModel.class;
    }

    @Override
    protected void registerObservers() {
        viewModel.sendText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sendText) {
                data.add(new ChatContent(ChatContent.TYPE_USER,sendText));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
            }
        });
        viewModel.receivedText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String receivedText) {
                data.add(new ChatContent(ChatContent.TYPE_CHARACTER,receivedText));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
            }
        });
    }

    private void setKeyboard() {
        binding.sendText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
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

        activity=(ChatRoomActivity) requireActivity();

        setKeyboard();

        // RecyclerView 설정
        recyclerView = binding.chatField;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 예제 데이터
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new ChatContent("User message " + i, ChatContent.TYPE_USER));
            data.add(new ChatContent("Other message " + i, ChatContent.TYPE_CHARACTER));
        }

        recyclerView.scrollToPosition(data.size() - 1);
        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);

        registerObservers();
    }

    @Override
    public void onResume() {
        super.onResume();

        String key = activity.getAccessToken();
        int chatId = activity.getChatId();
        viewModel.init(key,chatId);
    }
}