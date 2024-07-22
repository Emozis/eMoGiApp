package com.meta.persona.views.chatroom;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.meta.persona.network.ChatWebSocket;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

public class ChatRoomViewModel extends ViewModel {
    private ChatWebSocket chatWebSocket;
    private static final String TAG = "ChatRoomViewModel";

    private MutableLiveData<String> _inputText = new MutableLiveData<>("2223223");

    public LiveData<String> inputText() {
        return _inputText;
    }


    public void init() {
        Log.d(TAG, "init: ");
        _inputText.setValue("222");
        networking();
    }

    private void networking() {
        chatWebSocket = new ChatWebSocket();
        chatWebSocket.start();
        chatWebSocket.sendMessage(_inputText.getValue());
        chatWebSocket.close();
    }
}