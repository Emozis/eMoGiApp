package com.meta.emogi.views.chatroom;

import static com.meta.emogi.data.ChatContent.TYPE_AUTH;
import static com.meta.emogi.data.ChatContent.TYPE_USER;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.data.ChatContent;
import com.meta.emogi.network.ChatWebSocket;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomViewModel extends BaseViewModel {
    private ChatWebSocket chatWebSocket;
    private static final String TAG = "ChatRoomViewModel";

    public MutableLiveData<String> inputText = new MutableLiveData<>("");
    private MutableLiveData<String> _sendText = new MutableLiveData<>();
    private MutableLiveData<String> _receivedText = new MutableLiveData<>();
    private Map<Integer, ChatContent> messageMap = new HashMap<>();

    public ChatRoomViewModel(Application application) {super(application);}

    public LiveData<String> sendText() {
        return _sendText;
    }

    public LiveData<String> receivedText() {
        return _receivedText;
    }

    public void init(String key,int chatId) {
        connectNetwork(key,chatId);
    }

    private void connectNetwork(String key,int chatId) {
        //test용
        chatWebSocket = new ChatWebSocket(_receivedText,chatId);
        chatWebSocket.start();

        ChatContent chatContent = new ChatContent(TYPE_AUTH, "Bearer " + key, true);

        Gson gson = new Gson();
        String jsonMessage = gson.toJson(chatContent);

        Log.d(TAG, "보낸 메시지: " + jsonMessage);
        chatWebSocket.sendMessage(jsonMessage);
    }

    public void chatMessage() {
        _sendText.setValue(inputText.getValue());
        sendMessageToServer();
    }

    public void sendMessageToServer() {
        String message = _sendText.getValue();
        if (chatWebSocket != null && message != null && !message.isEmpty()) {

            Log.d(TAG, "보낸 메시지: " + message);

            ChatContent chatContent = new ChatContent(TYPE_USER, message);
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(chatContent);
            chatWebSocket.sendMessage(jsonMessage);
            inputText.setValue(""); // 메시지를 보낸 후 EditText를 비웁니다.
        }
    }

    public void updateChatContent(int responseId, String newContent) {
        if (messageMap.containsKey(responseId)) {
            ChatContent existingContent = messageMap.get(responseId);
            existingContent.appendContent(newContent); // Append new message part
        } else {
            ChatContent newContentItem = new ChatContent(ChatContent.TYPE_CHARACTER, newContent);
            messageMap.put(responseId, newContentItem);
            // Notify that a new item should be added
            _receivedText.postValue(newContent);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (chatWebSocket != null) {
            chatWebSocket.close();
        }
    }
}