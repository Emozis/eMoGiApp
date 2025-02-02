package com.meta.emogi.views.chatlist.chatList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.MyApplication;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ChatListModel;

import java.util.Calendar;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
    private List<ChatListModel> chatList;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private static final String TAG = "ChatListAdapter";
    private OnItemClickListener onItemClickListener;

    public ChatListAdapter(List<ChatListModel> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chat_list, parent, false);

        int screenHeight = MyApplication.getDeviceHeightPx();
        int itemHeight = (int) (screenHeight * 0.1f);

        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                itemHeight
        );

        int margin = parent.getContext().getResources().getDimensionPixelSize(R.dimen.common_space_semi_medium);

        params.setMargins(margin, margin, margin, 0); // 아이템 간 간격 추가

        view.setLayoutParams(params);
        return new ChatListViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int characterId, String clickedChatUrl,String clickedCharacterName);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatListModel chat = chatList.get(position);
        holder.characterName.setText(chat.getCharacter().getCharacterName());
        holder.itemMenuCharacter.setSelected(position == selectedPosition);
        holder.lastTalk.setText(chat.getLastMessage());
        holder.lastTalkTime.setText(chat.getLastMessageAt());

        if (chat.getEmptyChat()) {
            holder.lastTalk.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.lastTalk.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.yellow));
        }

        holder.itemMenuCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                // 현재 포지션이 RecyclerView.NO_POSITION이 아닌지 확인
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    // 선택된 포지션 업데이트
                    selectedPosition = position;

                    int clickedChatId = chatList.get(selectedPosition).getChatId();
                    String clickedChatUrl = chatList.get(selectedPosition).getCharacter().getCharacterProfile();
                    String clickedCharacterName = chatList.get(selectedPosition).getCharacter().getCharacterName();
                    onItemClickListener.onItemClick(clickedChatId, clickedChatUrl,clickedCharacterName);

                    // 변경된 선택 사항을 RecyclerView에 반영
                    notifyDataSetChanged();
                }
            }
        });

        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(20)); // 반지름 설정

        Glide.with(holder.itemView.getContext()).load(chat.getCharacter().getCharacterProfile()) // characterProfile은 이미지 URL
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
                .apply(requestOptions) // 둥근 모서리 적용
                .error(R.drawable.drawable_background_toolbar_profile) // 이미지 로드 실패 시 보여줄 이미지
                .into(holder.characterImage); // ImageView에 로드

        // ImageView에 이미지를 로드하는 코드를 추가
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatListViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemMenuCharacter;
        ImageView characterImage;
        TextView characterName;
        TextView lastTalk;
        TextView lastTalkTime;

        ChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMenuCharacter = itemView.findViewById(R.id.my_chat_list_layout);
            characterImage = itemView.findViewById(R.id.character_image);
            characterName = itemView.findViewById(R.id.character_name);
            lastTalk = itemView.findViewById(R.id.last_talk);
            lastTalkTime = itemView.findViewById(R.id.last_talk_time);

        }

    }
}
