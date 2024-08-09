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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.meta.emogi.R;
import com.meta.emogi.network.datamodels.ChatListModel;
import com.meta.emogi.views.menu.MenuListAdapter;

import java.time.LocalDate;
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
        return new ChatListViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int characterId, String clickedChatUrl);
    }

    private String parseLastTime(String[] timeArr) {

        Calendar calendar = Calendar.getInstance();
        String nowYear = String.valueOf(calendar.get(Calendar.YEAR));
        String nowMonth = "0"+String.valueOf(calendar.get(Calendar.MONTH) + 1); // 0 부터시작
        String nowDay = "0"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        String result = "";
        if (timeArr[0].equals(nowYear) && timeArr[1].equals(nowMonth) && timeArr[2].equals(nowDay)) {
            int hour = Integer.valueOf(timeArr[3]);
            result += hour / 12 > 0 ? "오후\n" : "오전\n";
            result += hour % 12 ==0 ? "12" : String.valueOf(hour % 12) +":"+timeArr[4];
        }else{
            result += timeArr[1]+"월"+" "+timeArr[2]+"일";
        }

        return result;

    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        ChatListModel chat = chatList.get(position);
        holder.characterName.setText(chat.getCharacter().getCharacterName());

        holder.itemMenuCharacter.setSelected(position == selectedPosition);

        List<ChatListModel.ChatLogs> chatLogs = chat.getChatLogs();
        if (chatLogs != null && !chatLogs.isEmpty()) {
            // Here we assume you want the content of the last chat log entry
            String lastLogContent = chatLogs.get(chatLogs.size() - 1).getContents();
            holder.lastTalk.setText(lastLogContent);
        } else {
            // If no chat logs are available, set a default or empty message
            holder.lastTalk.setText("최근에 대화한 채팅이 없습니다.\n어서 이야기해보세요");
        }


        String lastTalkTime = chat.getLastMessageAt();
        String[] timeArr = lastTalkTime.split("[-T:.]");
        lastTalkTime = parseLastTime(timeArr);

        holder.lastTalkTime.setText(lastTalkTime);

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
                    onItemClickListener.onItemClick(clickedChatId,clickedChatUrl);

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
