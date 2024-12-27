package com.meta.emogi.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meta.emogi.network.datamodels.ChatResponse;

import java.util.HashMap;
import java.util.Map;

public class ChatWebSocket extends WebSocketListener {
    private static final String TAG = "ChatWebSocket";
    private WebSocket webSocket;
    private OkHttpClient client;
    private MessageCallback callback;
    private MutableLiveData<String> _liveData;
    private static String serverDefaultUrl = "ws://122.128.54.136:8200/api/v1/chatting/ws/";
    private static String serverUrl;
    private String get;
    private final Gson gson = new Gson();

    public interface MessageCallback {
        void onMessageReceived(String message);
    }

    public ChatWebSocket(int chatId, MessageCallback callback) {
        this.callback = callback;
        client = new OkHttpClient();
        serverUrl = String.format(serverDefaultUrl + "%d", chatId);
    }

    public void start() {
        Request request = new Request.Builder().url(serverUrl).build();
        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(TAG, "WebSocket Opened: " + response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {

        try {
            // JSON 파싱
            ChatResponse chatResponse = gson.fromJson(text, ChatResponse.class);

            if ("character".equals(chatResponse.getType())) {
                String characterFragment = chatResponse.getContent();
                callback.onMessageReceived(characterFragment);

//                _liveData.postValue(characterFragment);


            } else {
                Log.e(TAG, "Unexpected message type or null chatResponse");
            }

        } catch (JsonSyntaxException e) {
            Log.e(TAG, "JSON Parsing Error: " + e.getMessage(), e);
        }
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.d(TAG, "WebSocket Closing: " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "WebSocket Failure: " + t.getMessage(), t);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        } else {
            Log.e(TAG, "WebSocket is not connected");
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Closing connection");
        }
    }
}