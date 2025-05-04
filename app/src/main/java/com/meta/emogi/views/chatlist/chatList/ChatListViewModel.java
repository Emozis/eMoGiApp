package com.meta.emogi.views.chatlist.chatList;

import android.app.Application;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.ChatResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListViewModel extends BaseViewModel {

    private final SingleLiveEvent<Void> _goToProfile = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _goToEditChatList = new SingleLiveEvent<>();
    private final MutableLiveData<List<ChatResponse>> _chatList = new MutableLiveData<>();

    public LiveData<Void> goToProfile() {
        return _goToProfile;
    }
    public LiveData<Void> goToEditChatList() {
        return _goToEditChatList;
    }
    public LiveData<List<ChatResponse>> chatList() {return _chatList;}

    @Override
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }
        int btnResId = v.getId();
        if (btnResId == R.id.add_chat) {
            _goToProfile.call();
        }else if (btnResId == R.id.remove_chat){
            _goToEditChatList.call();
        }
        return true;
    }

    public void getChatList() {
         loading();
        apiRepository.getChatList(new ApiCallBack.ApiResultHandler<List<ChatResponse>>() {
            @Override
            public void onSuccess(List<ChatResponse> data) {
                loadingSuccess();
                List<ChatResponse> ChatList = formatChatList(data);
                _chatList.setValue(ChatList);
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("채팅 리스트 가져오기 작업");
            }

            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    private List<ChatResponse> formatChatList(List<ChatResponse> chatlist){
        for(ChatResponse chat :chatlist){
            Pair<String,Boolean> lastMessage = getLastMessage(chat.getLastLog());
            chat.setLastMessage(lastMessage.first);
            chat.setEmptyChat(lastMessage.second);

            String[] timeArr = chat.getLastMessageAt().split("[-T:.]");
            String lastMessageAt = parseLastTime(timeArr);
            Log.d("www", "lastMessage: "+lastMessageAt);
            chat.setLastMessageAt(lastMessageAt);
        }
        return chatlist;
    }

    private Pair<String,Boolean> getLastMessage(ChatResponse.LastLog lastLogs){
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