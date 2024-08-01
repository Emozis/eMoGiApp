package com.meta.emogi.views.chatlist;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.views.menu.MenuListAdapter;
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.CharacterViewHolder> {



    @NonNull
    @Override
    public ChatListAdapter.CharacterViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(
            @NonNull ChatListAdapter.CharacterViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }
    public class CharacterViewHolder {}
}
