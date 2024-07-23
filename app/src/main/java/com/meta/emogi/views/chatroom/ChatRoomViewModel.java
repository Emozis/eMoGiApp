package com.meta.emogi.views.chatroom;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.emogi.network.ChatWebSocket;

public class ChatRoomViewModel extends ViewModel {
    private ChatWebSocket chatWebSocket;
    private static final String TAG = "ChatRoomViewModel";

    public MutableLiveData<String> inputText = new MutableLiveData<>("");
    private MutableLiveData<String> _sendText = new MutableLiveData<>();
    private MutableLiveData<String> _receivedText = new MutableLiveData<>();

    public LiveData<String> sendText() {
        return _sendText;
    }

    public LiveData<String> receivedText() {
        return _receivedText;
    }

    public void init() {
        connectNetwork();
    }

    private void connectNetwork() {
        chatWebSocket = new ChatWebSocket(_receivedText);
        chatWebSocket.start();
    }

    public void chatMessage(){
        _sendText.setValue(inputText.getValue());
        sendMessageToServer();
    }

    public void sendMessageToServer() {
        String message = _sendText.getValue();
        if (chatWebSocket != null && message != null && !message.isEmpty()) {
            Log.d(TAG, "보낸 메시지: " + message);
            chatWebSocket.sendMessage(message);
            inputText.setValue(""); // 메시지를 보낸 후 EditText를 비웁니다.
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