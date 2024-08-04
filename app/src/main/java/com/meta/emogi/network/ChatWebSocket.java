package com.meta.emogi.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

public class ChatWebSocket extends WebSocketListener {
    private static final String TAG = "ChatWebSocket";
    private WebSocket webSocket;
    private OkHttpClient client;
    private MutableLiveData<String> liveData;
    private static final String serverUrl ="ws://122.128.54.136:7070/api/chatting/ws/1";

    public ChatWebSocket(MutableLiveData<String> liveData) {
        this.liveData = liveData;
        client = new OkHttpClient();
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
        Log.d(TAG, "Received message: " + text);
        liveData.postValue(text);
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