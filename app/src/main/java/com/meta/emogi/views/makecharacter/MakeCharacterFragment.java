package com.meta.emogi.views.makecharacter;

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
import com.meta.emogi.databinding.FragmentMakeCharacterBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.menu.MenuViewModel;
import com.meta.emogi.views.toolbar.ToolbarView;

public class MakeCharacterFragment extends BaseFragment<FragmentMakeCharacterBinding,MakeCharacterViewModel> {

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("투툴바");
    }

    private MakeCharacterViewModel viewModel;
    private FragmentMakeCharacterBinding binding;
    public static MakeCharacterFragment newInstance() {
        return new MakeCharacterFragment();
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_make_character;
    }
    @Override
    protected Class<MakeCharacterViewModel> viewModelClass() {
        return MakeCharacterViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }

}