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
import com.meta.emogi.databinding.FragmentChatRoomBinding;
import com.meta.emogi.databinding.FragmentSplashBinding;
import com.meta.emogi.views.chatroom.ChatRoomViewModel;

public class SplashFragment extends Fragment {

    private SplashViewModel viewModel;
    private FragmentSplashBinding binding;
    public static SplashFragment newInstance() {
        return new SplashFragment();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

}