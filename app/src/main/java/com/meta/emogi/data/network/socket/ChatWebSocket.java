package com.meta.emogi.data.network.socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.meta.emogi.data.network.model.ChatSocketResponse;

public class ChatWebSocket extends WebSocketListener {
    private static final String TAG = "www Socket";
    private WebSocket webSocket;
    private OkHttpClient client;
    private MessageCallback callback;
    private MutableLiveData<String> _liveData;
    private static String serverDefaultUrl = "wss://emogi.site/api/v1/chatting/ws/";
//    private static String serverDefaultUrl = "ws://122.128.54.136:8200/api/v1/chatting/ws/";
    private static String serverUrl;
    private String get;
    private final Gson gson = new Gson();

    public interface MessageCallback {
        void onMessageReceived(String message);
        void onGreetReceived(String greetMessage);
        void onEndReceived();
    }

    public ChatWebSocket(int chatId, MessageCallback callback) {
        this.callback = callback;
        client = new OkHttpClient();
        serverUrl = String.format(serverDefaultUrl + "%d", chatId);
        Log.d("www", serverUrl);
    }

    public void start() {
        Request request = new Request.Builder().url(serverUrl).build();
        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.w(TAG, "WebSocket Opened: " + response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            // JSON 파싱
            ChatSocketResponse chatSocketResponse = gson.fromJson(text, ChatSocketResponse.class);

            String type = chatSocketResponse.getChatType();


            switch (type) {
                case ChatSocketResponse.GREET:
                    String greetMessage = chatSocketResponse.getContent();
                    if(!greetMessage.isEmpty()){
                        Log.d("www", "Greet :" +greetMessage);
                        callback.onGreetReceived(greetMessage);
                    }
                    break;
                case ChatSocketResponse.CHAT:
                    if ("character".equals(chatSocketResponse.getType())) {
                        String message = chatSocketResponse.getContent();
                        Log.d("www", "채팅 내용 :" + message);
                        callback.onMessageReceived(message);
                    }
                    break;
                case ChatSocketResponse.END:
                    callback.onEndReceived();
                    break;
            }

//            if ("character".equals(chatSocketResponse.getType())) {
//                String characterFragment = chatSocketResponse.getContent();
//                Log.d("www", "채팅 내용 :" + characterFragment);
//                callback.onMessageReceived(characterFragment);
//                //                _liveData.postValue(characterFragment);
//
//            } else {
//                Log.e(TAG, "Unexpected message type or null chatSocketResponse");
//            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "JSON Parsing Error: " + e.getMessage(), e);
        }
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.w(TAG, "WebSocket Closing: " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG, "WebSocket Failure: " + t.getMessage(), t);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            Log.d(TAG, "sendMessage: ");
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