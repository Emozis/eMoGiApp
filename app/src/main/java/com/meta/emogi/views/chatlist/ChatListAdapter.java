package com.meta.emogi.views.chatlist;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ChatListModel;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private List<ChatListModel> chatList;

    public ChatListAdapter(List<ChatListModel> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chat_list, parent, false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatListModel chat = chatList.get(position);
        holder.characterName.setText(String.valueOf(chat.getChatId()));
        holder.characterDescription.setText(chat.getLastMessageAt());
        // ImageView에 이미지를 로드하는 코드를 추가
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage;
        TextView characterName;
        TextView characterDescription;

        ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.character_image);
            characterName = itemView.findViewById(R.id.character_name);
            characterDescription = itemView.findViewById(R.id.character_description);
        }

    }
}
