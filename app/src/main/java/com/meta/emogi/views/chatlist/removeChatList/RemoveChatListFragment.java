package com.meta.emogi.views.chatlist.removeChatList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentRemoveChatListBinding;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.chatlist.ChatListActivity;
import com.meta.emogi.views.chatlist.chatList.ChatListAdapter;
import com.meta.emogi.views.toolbar.ToolbarView;

import java.util.ArrayList;
import java.util.List;

public class RemoveChatListFragment extends BaseFragment<FragmentRemoveChatListBinding,RemoveChatListViewModel> {

    private ChatListActivity activity;
    private RemoveChatListAdapter adapter;

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("내 채팅방");
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
        viewModel.chatList().observe(this, chatList -> {
            adapter = new RemoveChatListAdapter(chatList);
            binding.rmListChat.setAdapter(adapter);
            setClickListenerRecyclerView(adapter);
        });
        viewModel.isPressDelete().observe(this,deleteChatList->{
            for(int chatId:deleteChatList){
                viewModel.DeleteChat(chatId);
            }
        });
        viewModel.goToChatList().observe(this,unused -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_removeChatListFragment_to_chatListFragment);
        });
        viewModel.selectAll().observe(this,unused -> {
            adapter.selectAll(true);
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
        setAdapter();
        viewModel.setChatList(activity.getChatList());
        List<ChatListModel> chatList = activity.getChatList();
        if (chatList != null && !chatList.isEmpty()) {
            Log.w("www", "onResume: ");
            viewModel.setChatList(chatList);
        } else {
            Log.e("www", "chatList가 비어 있거나 null입니다.");
        }
    }


    private void setAdapter(){
        adapter = new RemoveChatListAdapter(activity.getChatList());
        binding.rmListChat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rmListChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setClickListenerRecyclerView(RemoveChatListAdapter removeChatListAdapter) {
        removeChatListAdapter.setOnItemClickListener((characterId) -> {
            if (characterId != -1) {
                viewModel.selectedChatIdDeleteList(characterId);
            }
        });
    }

}