package com.meta.persona.views.menu;

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

public class MenuFragment extends Fragment {

    private MenuViewModel mViewModel;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ViewModelFactory 사용
        ViewModelFactory factory = new ViewModelFactory();
        mViewModel = factory.create(MenuViewModel.class);
        // TODO: Use the ViewModel
    }
}