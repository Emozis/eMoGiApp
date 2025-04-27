package com.meta.emogi.views.chatroom;

import static com.meta.emogi.domain.model.ChatUiModel.TYPE_AUTH;
import static com.meta.emogi.domain.model.ChatUiModel.TYPE_USER;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.domain.model.ChatUiModel;
import com.meta.emogi.data.network.socket.ChatWebSocket;
import com.meta.emogi.data.network.model.ChatLogResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomViewModel extends BaseViewModel {
    private ChatWebSocket chatWebSocket;
    private StringBuilder messageBuilder = new StringBuilder();
    private static final String TAG = "www";

    public MutableLiveData<String> inputText = new MutableLiveData<>("");
    private MutableLiveData<String> _sendText = new MutableLiveData<>();
    private MutableLiveData<String> _receivedText = new MutableLiveData<>();
    private MutableLiveData<String> _receivedGreet = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isCanChat = new MutableLiveData<>(true);
    private MutableLiveData<List<ChatLogResponse>> _chatLogList = new MutableLiveData<>();


    public LiveData<String> sendText() {
        return _sendText;
    }
    public LiveData<String> receivedText() {
        return _receivedText;
    }

    public LiveData<String> receivedGreet() {
        return _receivedGreet;
    }
    public LiveData<Boolean> isCanChat() {
        return _isCanChat;
    }
    public LiveData<List<ChatLogResponse>> chatLogList() {
        return _chatLogList;
    }

    public void init(int chatId) {
        connectNetwork(chatId);
    }

    private void connectNetwork(int chatId) {
        loading();
        chatWebSocket = new ChatWebSocket(chatId, new ChatWebSocket.MessageCallback() {

            @Override
            public void onGreetReceived(String greetMessage) {
                _receivedGreet.postValue(greetMessage);
                _isCanChat.postValue(true);
            }
            @Override
            public void onMessageReceived(String message) {
                messageBuilder.append(message);
                _receivedText.postValue(messageBuilder.toString());
            }

            @Override
            public void onEndReceived() {
                _isCanChat.postValue(true);
                messageBuilder = new StringBuilder();
            }

        });

        chatWebSocket.start();
        loadingSuccess();

        ChatUiModel chatUiModel = new ChatUiModel(TYPE_AUTH);
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(chatUiModel);

        Log.w("www", "서버에게 소켓 인증 보내기 : " + jsonMessage);
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
            ChatUiModel chatUiModel = new ChatUiModel(TYPE_USER, message);
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(chatUiModel);
            chatWebSocket.sendMessage(jsonMessage);
            inputText.setValue(""); // 메시지를 보낸 후 EditText를 비웁니다.
            Log.w(TAG, "메세지 보내기 성공: " + message);
        } else {
            Log.w(TAG, "메세지 보내기 실패: " + message);
        }
    }

    public void getChatLogList(int chatId) {
        apiRepository.getChatLogList(chatId, new ApiCallBack.ApiResultHandler<List<ChatLogResponse>>() {
            @Override
            public void onSuccess(List<ChatLogResponse> data) {
                _chatLogList.setValue(data);
            }
            @Override
            public void onFailed(Throwable t) {
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