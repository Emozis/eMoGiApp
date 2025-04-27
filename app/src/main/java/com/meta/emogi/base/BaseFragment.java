package com.meta.emogi.base;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.BR;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.R;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.loading.LoadingView;
import com.meta.emogi.views.loading.LoadingViewModel;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.views.toolbar.ToolbarView;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected static String TAG;
    protected V binding;
    protected VM viewModel;
    protected LoadingViewModel loadingViewModel;

    protected abstract ToolbarView.ToolbarRequest toolbarCallback();
    protected abstract @LayoutRes int layoutId();

    protected abstract Class<VM> viewModelClass();

    protected abstract void registerObservers();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        viewModel = new ViewModelFactory(this).get(viewModelClass());
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.viewModel, viewModel);
        registerObservers();

        ToolbarView.ToolbarRequest request = toolbarCallback();
        if (request != null)
            ((BaseActivity<?>) requireActivity()).setToolbar(toolbarCallback());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        LoadingView loadingView = view.findViewById(R.id.loading);
        if (loadingView != null) {
            loadingViewModel = new ViewModelFactory(this).get(LoadingViewModel.class);
            loadingView.setViewModel(loadingViewModel);
            viewModel.setLoadingViewModel(loadingViewModel);
        }
    }

    protected void updateToolbar(ToolbarView.ToolbarRequest request) {
        if (request != null) {
            ((BaseActivity<?>) requireActivity()).setToolbar(request);
        }
    }



}