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

    private static final String TAG = "ChatWebSocket";
    private static final String TAG_LIFECYCLE = "WebSocket_Lifecycle";
    private static final String TAG_MESSAGE = "WebSocket_Message";
    private static final String TAG_ERROR = "WebSocket_Error";
    private WebSocket webSocket;
    private OkHttpClient client;
    private MessageCallback callback;
    private static String serverDefaultUrl = "wss://emogi.site/api/v1/chatting/ws/";
    //    private static String serverDefaultUrl = "ws://122.128.54.136:8200/api/v1/chatting/ws/";
    private static String serverUrl;
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
        Log.i(TAG_LIFECYCLE, "=== WebSocket 연결 성공 ===");
        Log.i(TAG_LIFECYCLE, "Response Code: " + response.code());
        Log.i(TAG_LIFECYCLE, "Response Message: " + response.message());
        Log.i(TAG_LIFECYCLE, "Server URL: " + serverUrl);
        Log.i(TAG_LIFECYCLE, "연결 시간: " + System.currentTimeMillis());
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            Log.d(TAG_MESSAGE, "=== 메시지 수신 ===");
            Log.d(TAG_MESSAGE, "Raw Message: " + text);
            Log.d(TAG_MESSAGE, "Message Length: " + text.length());
            // JSON 파싱
            ChatSocketResponse chatSocketResponse = gson.fromJson(text, ChatSocketResponse.class);

            Log.d(TAG_MESSAGE, "파싱된 ChatType: " + chatSocketResponse.getChatType());
            Log.d(TAG_MESSAGE, "파싱된 Type: " + chatSocketResponse.getType());
            Log.d(TAG_MESSAGE, "파싱된 Content: " + chatSocketResponse.getContent());

            String type = chatSocketResponse.getChatType();

            switch (type) {
                case ChatSocketResponse.GREET:
                    String greetMessage = chatSocketResponse.getContent();
                    if (!greetMessage.isEmpty()) {
                        Log.i(TAG_MESSAGE, "GREET 메시지 처리: " + greetMessage);
                        callback.onGreetReceived(greetMessage);
                    }
                    break;
                case ChatSocketResponse.CHAT:
                    Log.i(TAG_MESSAGE, "CHAT 메시지 처리 시작");
                    if ("character".equals(chatSocketResponse.getType())) {
                        String message = chatSocketResponse.getContent();
                        Log.i(TAG_MESSAGE, "캐릭터 메시지: " + message);
                        callback.onMessageReceived(message);
                    }else {
                        Log.w(TAG_MESSAGE, "캐릭터 메시지가 아님: " + chatSocketResponse.getType());
                    }
                    break;
                case ChatSocketResponse.END:
                    Log.w(TAG_MESSAGE, "=== END 신호 수신 - 연결 종료 예정 ===");
                    callback.onEndReceived();
                    break;
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG_ERROR, "JSON 파싱 실패!");
            Log.e(TAG_ERROR, "원본 메시지: " + text);
            Log.e(TAG_ERROR, "에러 상세: " + e.getMessage(), e);
        }
    }
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.w(TAG_LIFECYCLE, "=== WebSocket 종료 중 ===");
        Log.w(TAG_LIFECYCLE, "종료 코드: " + code);
        Log.w(TAG_LIFECYCLE, "종료 이유: " + reason);
        Log.w(TAG_LIFECYCLE, "종료 시간: " + System.currentTimeMillis());
        webSocket.close(1000, null);
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.w(TAG_LIFECYCLE, "=== WebSocket 완전 종료 ===");
        Log.w(TAG_LIFECYCLE, "최종 종료 코드: " + code);
        Log.w(TAG_LIFECYCLE, "최종 종료 이유: " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e(TAG_ERROR, "=== WebSocket 연결 실패 ===");
        Log.e(TAG_ERROR, "실패 이유: " + t.getMessage());

        if (response != null) {
            Log.e(TAG_ERROR, "Response Code: " + response.code());
            Log.e(TAG_ERROR, "Response Message: " + response.message());
            try {
                String responseBody = response.body() != null ? response.body().string() : "No body";
                Log.e(TAG_ERROR, "Response Body: " + responseBody);
            } catch (Exception e) {
                Log.e(TAG_ERROR, "Response Body 읽기 실패: " + e.getMessage());
            }
        } else {
            Log.e(TAG_ERROR, "Response is null");
        }

        Log.e(TAG_ERROR, "Stack Trace: ", t);
    }

    public void sendMessage(String message) {
        Log.d(TAG_MESSAGE, "=== 메시지 전송 시도 ===");
        Log.d(TAG_MESSAGE, "전송할 메시지: " + message);
        Log.d(TAG_MESSAGE, "WebSocket 상태: " + (webSocket != null ? "연결됨" : "null"));

        if (webSocket != null) {
            boolean success = webSocket.send(message);
            Log.d(TAG_MESSAGE, "메시지 전송 결과: " + (success ? "성공" : "실패"));
            Log.d(TAG_MESSAGE, "전송 시간: " + System.currentTimeMillis());
        } else {
            Log.e(TAG_ERROR, "WebSocket이 연결되지 않음 - 메시지 전송 불가");
        }
    }

    public void close() {
        Log.i(TAG_LIFECYCLE, "=== WebSocket 수동 종료 요청 ===");
        if (webSocket != null) {
            webSocket.close(1000, "Manual close");
            Log.i(TAG_LIFECYCLE, "종료 요청 완료");
        } else {
            Log.w(TAG_LIFECYCLE, "이미 종료된 상태");
        }
    }
}