package com.meta.emogi.views.chatlist.removeChatList;

import android.app.Application;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.base.SingleLiveEvent;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.model.ChatResponse;
import com.meta.emogi.data.network.model.DeleteChatResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveChatListViewModel extends BaseViewModel {

    private final MutableLiveData<List<ChatResponse>> _chatList = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> _isPressDelete = new MutableLiveData<>();
    private final SingleLiveEvent<Void> _goToChatList = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> _selectAll = new SingleLiveEvent<>();




    public LiveData<List<ChatResponse>> chatList() {return _chatList;}
    public LiveData<List<Integer>> isPressDelete() {return _isPressDelete;}
    public LiveData<Void> goToChatList() {return _goToChatList;}
    public LiveData<Void> selectAll() {return _selectAll;}

    private ArrayList<Integer> DeleteChatIdList;

    public RemoveChatListViewModel() {
        DeleteChatIdList = new ArrayList<>();
    }

    @Override
    public boolean onButtonClicked(View v) {
        if (!super.onButtonClicked(v)) {
            return false;
        }

        int btnResId = v.getId();
        if (btnResId == R.id.remove_chat) {
            _isPressDelete.setValue(DeleteChatIdList);
        }else if(btnResId == R.id.cancel){
            _goToChatList.call();
        }else if(btnResId == R.id.select_all){
            _selectAll.call();
        }
        return true;

    }

    public void selectedChatIdDeleteList(int intChatId) {
        Integer chatId= intChatId;
        if (DeleteChatIdList.contains(chatId)) {
            DeleteChatIdList.remove(chatId);
        } else {
            DeleteChatIdList.add(chatId);
        }
    }

    public void setChatList(List<ChatResponse> chatList){
        _chatList.setValue(chatList);
    }

    public void DeleteChat( int chatId) {
        loading();
        apiRepository.deleteChat(chatId, new ApiCallBack.ApiResultHandler<DeleteChatResponse>() {
            @Override
            public void onSuccess(DeleteChatResponse data) {
                loadingSuccess();
                Log.w("www", chatId+"번 채팅 제거 성공 "+data.getMessage());
                _goToChatList.call();
            }
            @Override
            public void onFailed(Throwable t) {
                loadingFailed("캐릭터 삭제하기 작업");
            }

            @Override
            public void onRetry() {
                loadingRetry();
            }
        });
    }

    private List<ChatResponse> formatChatList(List<ChatResponse> chatlist) {
        for (ChatResponse chat : chatlist) {
            Pair<String, Boolean> lastMessage = getLastMessage(chat.getLastLog());
            chat.setLastMessage(lastMessage.first);
            chat.setEmptyChat(lastMessage.second);

            String[] timeArr = chat.getLastMessageAt().split("[-T:.]");
            String lastMessageAt = parseLastTime(timeArr);
            chat.setLastMessageAt(lastMessageAt);
        }
        return chatlist;
    }

    private Pair<String, Boolean> getLastMessage(ChatResponse.LastLog lastLog) {
        String lastMessage;
        Boolean isEmptyChat;
        if (lastLog != null) {
            lastMessage = lastLog.getContents();
            isEmptyChat = true;
        } else {
            lastMessage = "최근에 대화한 채팅이 없습니다.\n어서 이야기해보세요";
            isEmptyChat = false;
        }
        return new Pair<>(lastMessage, isEmptyChat);
    }

    private String parseLastTime(String[] timeArr) {

        Calendar calendar = Calendar.getInstance();
        String nowYear = String.valueOf(calendar.get(Calendar.YEAR));
        String nowMonth = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1); // 0 부터시작
        String nowDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        nowDay = nowDay.length() == 1 ? "0" + nowDay : nowDay;

        String result = "";

        if (timeArr[0].equals(nowYear) && timeArr[1].equals(nowMonth) && timeArr[2].equals(nowDay)) {
            int hour = Integer.valueOf(timeArr[3]);
            result += hour / 12 > 0 ? "오후\n" : "오전\n";
            result += hour % 12 == 0 ? "12" : String.valueOf(hour % 12) + ":" + timeArr[4];
        } else {
            result += timeArr[1] + "월" + " " + timeArr[2] + "일";
        }

        return result;
    }

}