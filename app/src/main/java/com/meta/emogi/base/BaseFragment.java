package com.meta.emogi.base;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meta.emogi.BR;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.meta.emogi.di.ViewModelFactory;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected static String TAG;
    protected V binding;
    protected VM viewModel;

    protected abstract @LayoutRes int layoutId();

    protected abstract Class<VM> viewModelClass();

    protected abstract void registerObservers();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        viewModel = new ViewModelProvider(this, new ViewModelFactory(requireActivity().getApplication())).get(viewModelClass());
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.viewModel, viewModel);
        registerObservers(); // Move this line here
        return binding.getRoot();
    }
}