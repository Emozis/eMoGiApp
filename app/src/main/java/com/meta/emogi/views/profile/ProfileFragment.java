package com.meta.emogi.views.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentProfileBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.splash.SplashViewModel;
import com.meta.emogi.views.toolbar.ToolbarView;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding,ProfileViewModel> {

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("투툴바");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_profile;
    }
    @Override
    protected Class<ProfileViewModel> viewModelClass() {
        return ProfileViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }

}