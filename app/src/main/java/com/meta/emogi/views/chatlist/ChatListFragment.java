package com.meta.emogi.views.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentChatListBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.network.ApiService;
import com.meta.emogi.network.RetrofitClient;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.menu.MenuListAdapter;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChatListFragment extends BaseFragment<FragmentChatListBinding, ChatListViewModel> {

    private ChatListActivity activity;
    private ChatListAdapter adapter;

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
        viewModel.goToProfile().observe(getViewLifecycleOwner(), unused -> {
            activity.moveToMyProfile();
        });
        viewModel.chatList().observe(this, chatList -> {
            adapter = new ChatListAdapter(chatList);
            binding.listChat.setAdapter(adapter);
            setClickListenerRecyclerView(adapter);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChatListActivity) requireActivity();
    }
    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
        String key = activity.getAccessToken();
        viewModel.getChatList(key);
    }

    private void setupRecyclerView() {
        adapter = new ChatListAdapter(new ArrayList<>());
        binding.listChat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.listChat.setAdapter(adapter);
    }

    private void setClickListenerRecyclerView(ChatListAdapter chatListAdapter) {
        chatListAdapter.setOnItemClickListener((characterId, clickedChatUrl) -> {
            // 클릭된 아이템의 CharacterId와 clickedChatUrl을 가져와서 처리
            if (characterId != -1) {
                activity.moveToChatRoom(characterId, clickedChatUrl);
            }
        });
    }

}