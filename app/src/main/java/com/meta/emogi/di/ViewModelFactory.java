package com.meta.emogi.di;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.views.characterdetail.CharacterDetailViewModel;
import com.meta.emogi.views.chatlist.chatList.ChatListViewModel;
import com.meta.emogi.views.chatlist.removeChatList.RemoveChatListViewModel;
import com.meta.emogi.views.chatroom.ChatRoomViewModel;
import com.meta.emogi.views.login.LoginViewModel;
import com.meta.emogi.views.makecharacter.MakeCharacterViewModel;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.profile.characterMangage.CharacterManageViewModel;
import com.meta.emogi.views.profile.myPage.MyPageViewModel;
import com.meta.emogi.views.splash.SplashViewModel;
import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(application);
        } else if (modelClass.isAssignableFrom(ChatRoomViewModel.class)) {
            return (T) new ChatRoomViewModel(application);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(application);
        } else if (modelClass.isAssignableFrom(MakeCharacterViewModel.class)) {
            return (T) new MakeCharacterViewModel(application);
        } else if (modelClass.isAssignableFrom(MenuViewModel.class)) {
            return (T) new MenuViewModel(application);
        } else if (modelClass.isAssignableFrom(ChatListViewModel.class)) {
            return (T) new ChatListViewModel(application);
        }else if (modelClass.isAssignableFrom(ToolbarViewModel.class)) {
            return (T) new ToolbarViewModel(application);
        }else if (modelClass.isAssignableFrom(CharacterManageViewModel.class)) {
            return (T) new CharacterManageViewModel(application);
        }else if (modelClass.isAssignableFrom(MyPageViewModel.class)) {
            return (T) new MyPageViewModel(application);
        }else if (modelClass.isAssignableFrom(CharacterDetailViewModel.class)) {
            return (T) new CharacterDetailViewModel(application);
        }else if (modelClass.isAssignableFrom(RemoveChatListViewModel.class)) {
            return (T) new RemoveChatListViewModel(application);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}