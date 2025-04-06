package com.meta.emogi.views.chatlist.chatList;

import android.app.Application;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.network.datamodels.ChatListModel;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListViewModel extends BaseViewModel {
    public ChatListViewModel(Application application) {super(application);}

    private final SingleLiveEvent<Void> _goToProfile = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _goToEditChatList = new SingleLiveEvent<>();
    private final MutableLiveData<List<ChatListModel>> _chatList = new MutableLiveData<>();

    public LiveData<Void> goToProfile() {
        return _goToProfile;
    }
    public LiveData<Void> goToEditChatList() {
        return _goToEditChatList;
    }
    public LiveData<List<ChatListModel>> chatList() {return _chatList;}

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.add_chat) {
            _goToProfile.call();
        }else if (btnResId == R.id.remove_chat){
            _goToEditChatList.call();
        }
    }

    public void getChatList() {
        repository.getChatList( new Callback<List<ChatListModel>>() {
            @Override
            public void onResponse(Call<List<ChatListModel>> call, Response<List<ChatListModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatListModel> ChatList = formatChatList(response.body());
                    _chatList.setValue(ChatList);
                    offLoading();
                } else {
                    failLoading();
                    int statusCode = response.code();
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 로그에 상태 코드와 에러 메시지 출력
                    Log.e("www", "getChatList 응답이 정상적이지 않음. 상태 코드: " + statusCode + ", 에러 본문: " + errorBody);
                }

            }
            @Override
            public void onFailure(Call<List<ChatListModel>> call, Throwable t) {
                failLoading();
                Log.e("www", "getChatList API 호출 실패: " + t.getMessage());
            }
        });
    }

    private List<ChatListModel> formatChatList(List<ChatListModel> chatlist){
        for(ChatListModel chat :chatlist){
            Pair<String,Boolean> lastMessage = getLastMessage(chat.getLastLog());
            chat.setLastMessage(lastMessage.first);
            chat.setEmptyChat(lastMessage.second);

            String[] timeArr = chat.getLastMessageAt().split("[-T:.]");
            String lastMessageAt = parseLastTime(timeArr);
            Log.d("wwwt", "시간은말이지"+lastMessageAt);
            chat.setLastMessageAt(lastMessageAt);
        }
        return chatlist;
    }

    private Pair<String,Boolean> getLastMessage(ChatListModel.LastLog lastLogs){
        String lastMessage;
        Boolean isEmptyChat;
        if (lastLogs != null) {
            lastMessage = lastLogs.getContents();
            isEmptyChat=true;
        } else {
            lastMessage = "최근에 대화한 채팅이 없습니다.\n어서 이야기해보세요";
            isEmptyChat=false;
        }
        return new Pair<>(lastMessage,isEmptyChat);
    }

    private String parseLastTime(String[] timeArr) {

        Calendar calendar = Calendar.getInstance();
        String nowYear = String.valueOf(calendar.get(Calendar.YEAR));
        String nowMonth = "0"+String.valueOf(calendar.get(Calendar.MONTH) + 1); // 0 부터시작
        String nowDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        Log.d("www", "지금시간"+nowYear+nowMonth+nowDay);
        Log.d("www", "지금시간"+timeArr[3]+timeArr[4]);

        nowDay = nowDay.length()==1?"0"+nowDay:nowDay;

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

}