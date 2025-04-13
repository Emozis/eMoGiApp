package com.meta.emogi.views.chatroom;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.R;
import com.meta.emogi.domain.model.ChatUiModel;

import java.util.List;
import java.util.Objects;
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatUiModel> mData;

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

    public ChatListAdapter(List<ChatUiModel> data) {
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mData.get(position).getType();
        if (Objects.equals(type, ChatUiModel.TYPE_USER)) {
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
        ChatUiModel message = mData.get(position);
        if (holder.getItemViewType() == 0) {
            UserViewHolder userHolder = (UserViewHolder) holder;
            userHolder.chatText.setText(message.getContent());
            // Set image if necessary
        } else {
            OtherViewHolder otherHolder = (OtherViewHolder) holder;
            if (message.getSpannedContent() != null) {
                otherHolder.chatText.setText(message.getSpannedContent()); // Spanned 설정
            } else {
                otherHolder.chatText.setText(message.getContent()); // 일반 텍스트 설정
            }
//            otherHolder.chatText.setText(message.getContent());
            RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(20)); // 반지름 설정

            Glide.with(otherHolder.itemView.getContext()).load(message.getAiUrl()) // characterProfile은 이미지 URL
                    .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
                    .apply(requestOptions) // 둥근 모서리 적용
                    .error(R.drawable.drawable_background_toolbar_profile) // 이미지 로드 실패 시 보여줄 이미지
                    .into(otherHolder.profile); // ImageView에 로드
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
