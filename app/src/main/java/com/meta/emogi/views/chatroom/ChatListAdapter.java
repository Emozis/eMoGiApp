package com.meta.emogi.views.chatroom;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.data.ChatMessage;

import java.util.List;
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> mData;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView chatText;
        public ImageView profile;

        public UserViewHolder(View view) {
            super(view);
            chatText = view.findViewById(R.id.chat_text);
            profile = view.findViewById(R.id.profile);
        }
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder {
        public TextView chatText;
        public ImageView profile;

        public OtherViewHolder(View view) {
            super(view);
            chatText = view.findViewById(R.id.chat_text);
            profile = view.findViewById(R.id.profile);
        }
    }

    public ChatListAdapter(List<ChatMessage> data) {
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ChatMessage.TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_chat, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ai_chat, parent, false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = mData.get(position);
        if (holder.getItemViewType() == ChatMessage.TYPE_USER) {
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.chatText.setText(message.getMessage());
            // 이미지 설정이 필요하면 여기에 추가
        } else {
            OtherViewHolder otherHolder = (OtherViewHolder) holder;
            otherHolder.chatText.setText(message.getMessage());
            // 이미지 설정이 필요하면 여기에 추가
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
