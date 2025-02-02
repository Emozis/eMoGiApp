package com.meta.emogi.views.chatroom;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
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
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.ChatLogModel;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatRoomFragment extends BaseFragment<FragmentChatRoomBinding, ChatRoomViewModel> {

    private ChatListAdapter adapter;
    private List<ChatContent> data;
    private RecyclerView recyclerView;
    private ChatRoomActivity activity;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("채팅방테스트");
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
        viewModel.sendText().observe(getViewLifecycleOwner(), sendText -> {
            data.add(new ChatContent(ChatContent.TYPE_USER, sendText));
            adapter.notifyItemInserted(data.size() - 1);
            recyclerView.scrollToPosition(data.size() - 1);

            data.add(new ChatContent(ChatContent.TYPE_CHARACTER, "", activity.getChatUrl()));
            adapter.notifyItemInserted(data.size() - 1);
            recyclerView.scrollToPosition(data.size() - 1);
        });

        viewModel.receivedText().observe(getViewLifecycleOwner(), recevied -> {
            if (!recevied.isEmpty() && !data.isEmpty() && data.get(data.size() - 1).getType().equals(
                    ChatContent.TYPE_CHARACTER)) {
                Log.w("www", "메세지 받음 :" + recevied);

                Markwon markwon = Markwon.create(requireContext());
                Spanned markdownContent = markwon.toMarkdown(recevied);

                data.get(data.size() - 1).setSpannedContent(markdownContent);
                adapter.notifyItemChanged(data.size() - 1);
            }
        });

        viewModel.isCanChat().observe(getViewLifecycleOwner(), isCanChat -> {
            if (isCanChat) {
                binding.transmit.setEnabled(true);
            } else {
                binding.transmit.setEnabled(false);
            }
        });

        viewModel.chatLogList().observe(this, chatLogList -> {
            for (ChatLogModel log : chatLogList) {
                data.add(new ChatContent(log.getRole(), log.getContents(), activity.getChatUrl()));
            }

            adapter = new ChatListAdapter(data);
            adapter.notifyItemChanged(data.size() - 1);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(data.size() - 1);
        });
    }

    private void setKeyboard() {
        binding.sendText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    // 완료 또는 전송 액션일 때만 키보드를 숨깁니다.
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(binding.sendText.getWindowToken(), 0);
                    }
                    return true;
                } else if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 엔터 키를 눌렀을 때 줄바꿈을 추가합니다.
                    int start = binding.sendText.getSelectionStart();
                    int end = binding.sendText.getSelectionEnd();
                    binding.sendText.getText().replace(Math.min(start, end), Math.max(start, end), "\n", 0, 1);
                    binding.sendText.setSelection(start + 1);
                    return true;
                }
                return false;
            }
        });

        binding.sendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    binding.transmit.setEnabled(false);
                } else {
                    binding.transmit.setEnabled(true);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (ChatRoomActivity) requireActivity();

        setKeyboard();

        recyclerView = binding.chatField;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        data = new ArrayList<>();
        adapter = new ChatListAdapter(data);
        recyclerView.setAdapter(adapter);


        viewModel.getChatLogList(activity.getAccessToken(), activity.getChatId());
    }

    @Override
    public void onResume() {
        super.onResume();
        data = new ArrayList<>();
        binding.transmit.setEnabled(false);
        viewModel.init(activity.getAccessToken(), activity.getChatId());
        ToolbarView.ToolbarRequest newToolbarRequest = new ToolbarView.ToolbarRequest(activity.getCharacterName());
        updateToolbar(newToolbarRequest);
    }
}