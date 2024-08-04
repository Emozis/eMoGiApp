package com.meta.emogi.views.chatroom;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meta.emogi.R;
import com.meta.emogi.data.ChatContent;

import java.util.List;
import java.util.Objects;
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatContent> mData;

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

    public ChatListAdapter(List<ChatContent> data) {
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mData.get(position).getType();
        if (Objects.equals(type, ChatContent.TYPE_USER)) {
            return 0; // Return a unique integer for user type
        } else {
            return 1; // Return a different integer for other type
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) { // User message type
            View view = inflater.inflate(R.layout.item_user_chat, parent, false);
            return new UserViewHolder(view);
        } else { // Other message type
            View view = inflater.inflate(R.layout.item_ai_chat, parent, false);
            return new OtherViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatContent message = mData.get(position);
        if (holder.getItemViewType() == 0) {
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.chatText.setText(message.getContent());
            // Set image if necessary
        } else {
            OtherViewHolder otherHolder = (OtherViewHolder) holder;
            otherHolder.chatText.setText(message.getContent());
            // Set image if necessary
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
