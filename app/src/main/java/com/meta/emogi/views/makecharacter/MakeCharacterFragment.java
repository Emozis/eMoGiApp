package com.meta.emogi.views.makecharacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.R;
import com.meta.emogi.di.ViewModelFactory;

public class MakeCharacterFragment extends Fragment {

    private MakeCharacterViewModel mViewModel;
    public static MakeCharacterFragment newInstance() {
        return new MakeCharacterFragment();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_make_character, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ViewModelFactory 사용
        ViewModelFactory factory = new ViewModelFactory();
        mViewModel = factory.create(MakeCharacterViewModel.class);
        // TODO: Use the ViewModel
    }

}