package com.meta.emogi.data.repository;
import com.meta.emogi.data.network.api.ApiCallBack;
import com.meta.emogi.data.network.api.ApiService;
import com.meta.emogi.data.network.api.RetrofitClient;
import com.meta.emogi.data.network.model.CharacterResponse;
import com.meta.emogi.domain.TokenManager;
import com.meta.emogi.data.network.model.ChatResponse;
import com.meta.emogi.data.network.model.ChatLogResponse;
import com.meta.emogi.data.network.model.CharacterImageResponse;
import com.meta.emogi.data.network.model.CreateChatResponse;
import com.meta.emogi.data.network.model.ResponseModel;
import com.meta.emogi.data.network.model.TokenModel;
import com.meta.emogi.data.network.model.UserData;
import com.meta.emogi.data.network.model.DeleteChatResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
public class ApiRepository {
    private ApiService apiService;

    private final boolean isRetry = true;
    private final Integer maxRetryCount= 3 ;

    String token = TokenManager.getInstance().getToken();

    public ApiRepository() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    private <T> void retryCall(Call<T> call,ApiCallBack.ApiResultHandler<T> handler){
        if(isRetry){
            call.enqueue(new ApiCallBack<>(call, handler,maxRetryCount));
        }else{
            call.enqueue(new ApiCallBack<>(call, handler));
        }
    }

    /// GET

    //Menu, profile_characterMange

    // Repository
    // ApiRepository.java

    public void getMyCharacterList(ApiCallBack.ApiResultHandler<List<CharacterResponse>> handler) {
        Call<List<CharacterResponse>> call = apiService.getMyCharacterList(token);
        retryCall(call, handler);
    }

    //Menu

    public void getRankCharacterList(ApiCallBack.ApiResultHandler<List<CharacterResponse>> handler) {
        Call<List<CharacterResponse>> call = apiService.getRankCharacterList();
        retryCall(call, handler);
    }

    //profile_myPage
    public void getUserData(ApiCallBack.ApiResultHandler<UserData> handler) {
        Call<UserData> call = apiService.getUserData(token);
        retryCall(call, handler);
    }

    //makeCharacter
    public void getDefaultRelationshipList(ApiCallBack.ApiResultHandler<List<CharacterResponse.CharacterRelationships>> handler) {
        Call<List<CharacterResponse.CharacterRelationships>> call = apiService.getDefaultRelationshipList();
        retryCall(call, handler);
    }

    //makeCharacter
    public void getDefaultImageList(ApiCallBack.ApiResultHandler<List<CharacterImageResponse>> handler) {
        Call<List<CharacterImageResponse>> call = apiService.getDefaultImageList();
        retryCall(call, handler);
    }

    //chatRoom
    public void getChatLogList(int chatId, ApiCallBack.ApiResultHandler<List<ChatLogResponse>> handler) {
        Call<List<ChatLogResponse>> call = apiService.getChatLog(token, chatId);
        retryCall(call, handler);
    }

    //chatList
    public void getChatList(ApiCallBack.ApiResultHandler<List<ChatResponse>> handler) {
        Call<List<ChatResponse>> call = apiService.getChatList(token);
        retryCall(call, handler);
    }

    //deletechat
    public void deleteChat(int chatId, ApiCallBack.ApiResultHandler<DeleteChatResponse> handler) {
        Call<DeleteChatResponse> call = apiService.deleteChat(token, chatId);
        retryCall(call, handler);
    }

    //characterDetail
    public void getCharacterDetails(int characterId, ApiCallBack.ApiResultHandler<CharacterResponse> handler) {
        Call<CharacterResponse> call = apiService.getCharacterDetails(token, characterId);
        retryCall(call, handler);
    }


    public void deleteCharacter(int characterId, ApiCallBack.ApiResultHandler<ResponseModel> handler) {
        Call<ResponseModel> call = apiService.deleteCharacter(token, characterId);
        retryCall(call, handler);
    }

    //PUT
    public void updateCharacter(CharacterResponse characterResponse, int characterId, ApiCallBack.ApiResultHandler<CharacterResponse> handler) {
        Call<CharacterResponse> call = apiService.updateCharacter(token, characterResponse, characterId);
        retryCall(call, handler);
    }

    //// POST

    //makeCharacter
    public void createCharacter( CharacterResponse characterResponse, ApiCallBack.ApiResultHandler<CharacterResponse> handler) {
        Call<CharacterResponse> call = apiService.createCharacter(token,characterResponse);
        retryCall(call,handler);
    }

    //login
    public void createAccessToken(TokenModel requestToken, ApiCallBack.ApiResultHandler<TokenModel> handler) {
        Call<TokenModel> call = apiService.createAccessToken(requestToken);
        retryCall(call,handler);
    }

    //characterDetail
    public void createChatRoom(CreateChatResponse createChatResponse, ApiCallBack.ApiResultHandler<CreateChatResponse> handler) {
        Call<CreateChatResponse> call = apiService.createChatRoom(token,createChatResponse);
        retryCall(call,handler);
    }


}
