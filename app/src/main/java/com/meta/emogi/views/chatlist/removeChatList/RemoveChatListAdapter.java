package com.meta.emogi.views.chatlist.removeChatList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.chatlist.chatList.ChatListAdapter;

import java.util.List;
public class RemoveChatListAdapter extends RecyclerView.Adapter<RemoveChatListAdapter.RemoveChatListViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<ChatListModel> chatList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    // 생성자 추가
    public RemoveChatListAdapter(List<ChatListModel> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public RemoveChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rm_chat_list, parent, false);
        return new RemoveChatListViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int characterId, String clickedChatUrl);
    }


    @Override
    public void onBindViewHolder(@NonNull RemoveChatListViewHolder holder, int position) {
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
                    onItemClickListener.onItemClick(clickedChatId, clickedChatUrl);

                    if(holder.radioButton.isSelected()){
                        holder.radioButton.setSelected(false);
                    }else{
                        holder.radioButton.setSelected(true);
                    }

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
    }


    @Override
    public int getItemCount() {
        return chatList != null ? chatList.size() : 0;
    }

    public class RemoveChatListViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemMenuCharacter;
        RadioButton radioButton;
        ImageView characterImage;
        TextView characterName;
        TextView lastTalk;
        TextView lastTalkTime;

        public RemoveChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMenuCharacter = itemView.findViewById(R.id.rm_my_chat_list_layout);
            radioButton = itemView.findViewById(R.id.rm_select_Button);
            characterImage = itemView.findViewById(R.id.rm_character_image);
            characterName = itemView.findViewById(R.id.rm_character_name);
            lastTalk = itemView.findViewById(R.id.rm_last_talk);
            lastTalkTime = itemView.findViewById(R.id.rm_last_talk_time);
        }
    }
}
