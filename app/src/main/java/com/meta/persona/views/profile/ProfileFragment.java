package com.meta.persona.views.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.persona.R;
import com.meta.persona.di.ViewModelFactory;
import com.meta.persona.views.menu.MenuViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ViewModelFactory 사용
        ViewModelFactory factory = new ViewModelFactory();
        mViewModel = factory.create(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}