package com.meta.emogi.views.characterdetail;

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
import com.meta.emogi.databinding.FragmentCharacterDetailBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class CharacterDetailFragment extends BaseFragment<FragmentCharacterDetailBinding,CharacterDetailViewModel> {

    @Override
    protected ToolbarView.ToolbarRequest toolbarCallback() {
        return new ToolbarView.ToolbarRequest("캐릭터 상세");
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_character_detail;
    }
    @Override
    protected Class<CharacterDetailViewModel> viewModelClass() {
        return CharacterDetailViewModel.class;
    }
    @Override
    protected void registerObservers() {

    }
}