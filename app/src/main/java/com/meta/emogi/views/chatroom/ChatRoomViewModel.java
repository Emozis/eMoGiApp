package com.meta.emogi.views.chatroom;

import static com.meta.emogi.data.ChatContent.TYPE_AUTH;
import static com.meta.emogi.data.ChatContent.TYPE_USER;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.data.ChatContent;
import com.meta.emogi.network.ChatWebSocket;
import com.meta.emogi.network.datamodels.ChatLogModel;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomViewModel extends BaseViewModel {
    private ChatWebSocket chatWebSocket;
    private StringBuilder messageBuilder = new StringBuilder();
    private static final String TAG = "ChatRoomViewModel";

    public MutableLiveData<String> inputText = new MutableLiveData<>("");
    private MutableLiveData<String> _sendText = new MutableLiveData<>();
    private MutableLiveData<String> _receivedText = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isCanChat = new MutableLiveData<>(true);
    private MutableLiveData<List<ChatLogModel>> _chatLogList = new MutableLiveData<>();

    public ChatRoomViewModel(Application application) {super(application);}

    public LiveData<String> sendText() {
        return _sendText;
    }
    public LiveData<String> receivedText() {
        return _receivedText;
    }
    public LiveData<Boolean> isCanChat() {
        return _isCanChat;
    }
    public LiveData<List<ChatLogModel>> chatLogList() {
        return _chatLogList;
    }

    public void init(String key, int chatId) {
        connectNetwork(key, chatId);
    }

    private void connectNetwork(String key, int chatId) {
        chatWebSocket = new ChatWebSocket(chatId, new ChatWebSocket.MessageCallback() {
            @Override
            public void onMessageReceived(String message) {
                if (message.equals("[EOS]")) {
                    _isCanChat.postValue(true);
                    messageBuilder = new StringBuilder();
                } else {
                    messageBuilder.append(message);
                    _receivedText.postValue(messageBuilder.toString());
                }
            }
        });

        chatWebSocket.start();

        ChatContent chatContent = new ChatContent(TYPE_AUTH, key, true);
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(chatContent);

        Log.d("www", "보낸 메시지: " + jsonMessage);
        chatWebSocket.sendMessage(jsonMessage);
    }

    @Override
    public void onButtonClicked(View v) {
        int btnResId = v.getId();
        if (btnResId == R.id.transmit) {
            _isCanChat.setValue(false);
            _sendText.setValue(inputText.getValue());
            sendMessageToServer();
        }
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

    public void getChatLogList(String accessToken,int chatId){
        repository.getChatLogList(accessToken, chatId, new Callback<List<ChatLogModel>>() {
            @Override
            public void onResponse(Call<List<ChatLogModel>> call, Response<List<ChatLogModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _chatLogList.setValue(response.body());
                } else {
                    failLoading();
                    Log.e("www", "getChatLogList 응답이 정상적이지 않음");
                }
            }
            @Override
            public void onFailure(Call<List<ChatLogModel>> call, Throwable t) {
                Log.e("www", "getChatLogList API 호출 실패: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (chatWebSocket != null) {
            chatWebSocket.close();
        }
    }
}