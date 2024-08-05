package com.meta.emogi.views.chatlist;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.menu.MenuListAdapter;

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
        return new ChatListViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int characterId);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatListModel chat = chatList.get(position);
        holder.characterName.setText(chat.getCharacter().getCharacterName());
        holder.lastTalkTime.setText(chat.getLastMessageAt());
        holder.lastTalkTime.setText(chat.getLastMessageAt());
        holder.itemMenuCharacter.setSelected(position == selectedPosition);

        List<ChatListModel.ChatLogs> chatLogs = chat.getChatLogs();
        if (chatLogs != null && !chatLogs.isEmpty()) {
            // Here we assume you want the content of the last chat log entry
            String lastLogContent = chatLogs.get(chatLogs.size() - 1).getContents();
            if (lastLogContent.length()>50){
                lastLogContent=lastLogContent.substring(0, 50)+"...";
            }
            Log.d(TAG, lastLogContent);
            holder.lastTalk.setText(lastLogContent);
        } else {
            // If no chat logs are available, set a default or empty message
            holder.lastTalk.setText("No messages yet.");
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
                    onItemClickListener.onItemClick(clickedChatId);

                    // 변경된 선택 사항을 RecyclerView에 반영
                    notifyDataSetChanged();
                }
            }
        });

        Glide.with(holder.itemView.getContext()).load(chat.getCharacter().getCharacterProfile()) // characterProfile은 이미지 URL
                .placeholder(R.drawable.drawable_background_toolbar_profile) // 이미지를 로드하는 동안 보여줄 플레이스홀더 이미지
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
