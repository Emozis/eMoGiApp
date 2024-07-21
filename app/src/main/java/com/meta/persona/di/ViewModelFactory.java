package com.meta.persona.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.meta.persona.views.chatlist.ChatListViewModel;
import com.meta.persona.views.chatroom.ChatRoomViewModel;
import com.meta.persona.views.login.LoginViewModel;
import com.meta.persona.views.makecharacter.MakeCharacterViewModel;
import com.meta.persona.views.menu.MenuViewModel;
import com.meta.persona.views.profile.ProfileViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory, IViewModelFactory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChatListViewModel.class)) {
            return (T) new ChatListViewModel();
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
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}