package com.meta.emogi.views.chatlist.removeChatList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentRemoveChatListBinding;
import com.meta.emogi.views.chatlist.ChatListActivity;
import com.meta.emogi.views.chatlist.chatList.ChatListAdapter;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;

public class RemoveChatListFragment extends BaseFragment<FragmentRemoveChatListBinding,RemoveChatListViewModel> {

    private ChatListActivity activity;
    private RemoveChatListAdapter adapter;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("채팅 삭제");
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_remove_chat_list;
    }
    @Override
    protected Class<RemoveChatListViewModel> viewModelClass() {
        return RemoveChatListViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChatListActivity) requireActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }


    private void setAdapter(){
        adapter = new RemoveChatListAdapter(activity.getChatList());
        binding.rmListChat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rmListChat.setAdapter(adapter);
    }
}