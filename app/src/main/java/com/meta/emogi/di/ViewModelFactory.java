package com.meta.emogi.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.views.chatlist.ChatListViewModel;
import com.meta.emogi.views.chatroom.ChatRoomViewModel;
import com.meta.emogi.views.login.LoginViewModel;
import com.meta.emogi.views.makecharacter.MakeCharacterViewModel;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.profile.ProfileViewModel;
import com.meta.emogi.views.splash.SplashViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory, IViewModelFactory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel();
        } else if (modelClass.isAssignableFrom(ChatRoomViewModel.class)) {
            return (T) new ChatRoomViewModel();
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel();
        } else if (modelClass.isAssignableFrom(MakeCharacterViewModel.class)) {
            return (T) new MakeCharacterViewModel();
        } else if (modelClass.isAssignableFrom(MenuViewModel.class)) {
            return (T) new MenuViewModel();
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel();
        } else if (modelClass.isAssignableFrom(ChatListViewModel.class)) {
            return (T) new ChatListViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}