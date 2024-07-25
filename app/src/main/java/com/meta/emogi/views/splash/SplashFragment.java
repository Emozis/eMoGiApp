package com.meta.emogi.views.splash;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentChatRoomBinding;
import com.meta.emogi.databinding.FragmentSplashBinding;
import com.meta.emogi.views.chatroom.ChatRoomViewModel;

public class SplashFragment extends BaseFragment<FragmentSplashBinding,SplashViewModel> {

    @Override
    protected int layoutId() {
        return R.layout.fragment_splash;
    }
    @Override
    protected Class<SplashViewModel> viewModelClass() {
        return SplashViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }

}