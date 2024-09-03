package com.meta.emogi.views.chatroom;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
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
    private ApiService apiService;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("채팅");
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
            if (!recevied.isEmpty() && !data.isEmpty() && data.get(data.size() - 1).getType().equals(ChatContent.TYPE_CHARACTER)) {

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
    }

    private void setKeyboard() {
        binding.sendText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    // 완료 또는 전송 액션일 때만 키보드를 숨깁니다.
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (ChatRoomActivity) requireActivity();

        setKeyboard();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);

        // RecyclerView 설정
        recyclerView = binding.chatField;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChatLog();

    }

    public void getChatLog() {
        Call<List<ChatLogModel>> call = apiService.getChatLog(activity.getAccessToken(), activity.getChatId());

        call.enqueue(new Callback<List<ChatLogModel>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<ChatLogModel>> call,
                    @NonNull Response<List<ChatLogModel>> response) {
                if (response.isSuccessful()) {
                    List<ChatLogModel> chatLogs = response.body();
                    data = new ArrayList<>();
                    for (ChatLogModel log : chatLogs) {
                        data.add(new ChatContent(log.getRole(), log.getContents(), activity.getChatUrl()));
                    }

                    adapter = new ChatListAdapter(data);
                    adapter.notifyItemChanged(data.size() - 1);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(data.size() - 1);
                } else {
                    Log.e("API Error", "Failed to fetch chat logs: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ChatLogModel>> call, @NonNull Throwable t) {
                Log.e("Network Error", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        String key = activity.getAccessToken();
        int chatId = activity.getChatId();
        viewModel.init(key, chatId);
    }
}