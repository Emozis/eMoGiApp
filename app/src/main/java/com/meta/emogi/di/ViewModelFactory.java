package com.meta.emogi.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meta.emogi.MyApplication;
import com.meta.emogi.views.characterdetail.CharacterDetailViewModel;
import com.meta.emogi.views.chatlist.chatList.ChatListViewModel;
import com.meta.emogi.views.chatlist.removeChatList.RemoveChatListViewModel;
import com.meta.emogi.views.chatroom.ChatRoomViewModel;
import com.meta.emogi.views.inquiry.checkInquiry.CheckInquiryViewModel;
import com.meta.emogi.views.inquiry.createInquiry.CreateInquiryViewModel;
import com.meta.emogi.views.login.LoginViewModel;
import com.meta.emogi.views.makecharacter.MakeCharacterViewModel;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.profile.characterMangage.CharacterManageViewModel;
import com.meta.emogi.views.profile.myPage.MyPageViewModel;
import com.meta.emogi.views.splash.SplashViewModel;
import com.meta.emogi.views.toolbar.ToolbarViewModel;

public class ViewModelFactory extends ViewModelProvider {

    public ViewModelFactory(@NonNull ViewModelStoreOwner owner) {
        super(owner, new Factory());
    }

    private static class Factory extends AndroidViewModelFactory {


        public Factory() {
            super(MyApplication.getInstance());
        }
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
            } else if (modelClass.isAssignableFrom(ChatListViewModel.class)) {
                return (T) new ChatListViewModel();
            } else if (modelClass.isAssignableFrom(ToolbarViewModel.class)) {
                return (T) new ToolbarViewModel();
            } else if (modelClass.isAssignableFrom(CharacterManageViewModel.class)) {
                return (T) new CharacterManageViewModel();
            } else if (modelClass.isAssignableFrom(MyPageViewModel.class)) {
                return (T) new MyPageViewModel();
            } else if (modelClass.isAssignableFrom(CharacterDetailViewModel.class)) {
                return (T) new CharacterDetailViewModel();
            } else if (modelClass.isAssignableFrom(RemoveChatListViewModel.class)) {
                return (T) new RemoveChatListViewModel();
            } else if (modelClass.isAssignableFrom(CheckInquiryViewModel.class)) {
                return (T) new CheckInquiryViewModel();
            } else if (modelClass.isAssignableFrom(CreateInquiryViewModel.class)) {
                return (T) new CreateInquiryViewModel();
            }

            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}