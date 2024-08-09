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
    private MutableLiveData<String> _liveData;
    private static String serverDefaultUrl="ws://122.128.54.136:7070/api/v1/chatting/ws/";
    private static String serverUrl;
    private String get;

    private final Gson gson = new Gson();
    private Map<Integer, StringBuilder> messageBufferMap = new HashMap<>();


    public LiveData<String> liveData() {
        return _liveData;
    }


    public ChatWebSocket(MutableLiveData<String> liveData,int chatId) {
        this._liveData = liveData;
        client = new OkHttpClient();
        serverUrl=String.format(serverDefaultUrl+"%d", chatId);
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

            // "character" 타입의 메시지 처리
            if ("character".equals(chatResponse.getType())) {  // 상수를 왼쪽에 두어 null 체크
                int responseId = chatResponse.getResponseId();
                String characterFragment = chatResponse.getContent();

                // 기존에 수집 중인 메시지가 있는지 확인
                messageBufferMap.computeIfAbsent(responseId, k -> new StringBuilder());

                // 메시지 추가
                messageBufferMap.get(responseId).append(characterFragment);

                get+=characterFragment;
                Log.i(TAG, get);

                _liveData.postValue(characterFragment);
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