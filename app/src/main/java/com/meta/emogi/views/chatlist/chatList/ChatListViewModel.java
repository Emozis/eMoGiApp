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
import java.util.ArrayList;
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

//    @Override
//    public boolean onButtonClicked(View v) {
//        if (!super.onButtonClicked(v)) {
//            return false;
//        }
//        int btnResId = v.getId();
//        if (btnResId == R.id.add_chat) {
//            _goToProfile.call();
//        }else if (btnResId == R.id.remove_chat){
//            _goToEditChatList.call();
//        }
//        return true;
//    }

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

    public void setDebugData() {
        List<ChatResponse> dummyData = new ArrayList<>();

        // Dummy data 1
        ChatResponse chat1 = new ChatResponse();
        chat1.setChatId(1);
        ChatResponse.Character character1 = new ChatResponse.Character();
        character1.setCharacterId(101);
        character1.setCharacterName("덤프");
        character1.setCharacterProfile("https://picsum.photos/300/300?random=1");
        chat1.setCharacter(character1);
        chat1.setLastMessage("안녕! 무슨 일이야?");
        chat1.setLastMessageAt("오후\n3:45");
        chat1.setEmptyChat(false);
        dummyData.add(chat1);

        // Dummy data 2
        ChatResponse chat2 = new ChatResponse();
        chat2.setChatId(2);
        ChatResponse.Character character2 = new ChatResponse.Character();
        character2.setCharacterId(102);
        character2.setCharacterName("에러");
        character2.setCharacterProfile("https://picsum.photos/300/300?random=2");
        chat2.setCharacter(character2);
        chat2.setLastMessage("최근에 대화한 채팅이 없습니다.\n어서 이야기해보세요");
        chat2.setLastMessageAt("10월 24일");
        chat2.setEmptyChat(true);
        dummyData.add(chat2);

        // Dummy data 3
        ChatResponse chat3 = new ChatResponse();
        chat3.setChatId(3);
        ChatResponse.Character character3 = new ChatResponse.Character();
        character3.setCharacterId(103);
        character3.setCharacterName("테스트");
        character3.setCharacterProfile("https://picsum.photos/300/300?random=3");
        chat3.setCharacter(character3);
        chat3.setLastMessage("오늘 날씨 좋다!");
        chat3.setLastMessageAt("오전\n9:10");
        chat3.setEmptyChat(false);
        dummyData.add(chat3);

        _chatList.postValue(dummyData);
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