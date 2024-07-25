package com.meta.emogi.views.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseFragment;
import com.meta.emogi.databinding.FragmentMenuBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.splash.SplashViewModel;

public class MenuFragment extends BaseFragment<FragmentMenuBinding, MenuViewModel> {

    @Override
    protected int layoutId() {
        return R.layout.fragment_menu;
    }
    @Override
    protected Class<MenuViewModel> viewModelClass() {
        return MenuViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }
}