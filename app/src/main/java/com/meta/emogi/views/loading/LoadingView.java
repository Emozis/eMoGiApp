package com.meta.emogi.views.loading;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;

import com.meta.emogi.BR;
import com.meta.emogi.R;
import com.meta.emogi.base.BaseViewModel;
import com.meta.emogi.databinding.ViewLoadingBinding;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.inquiry.InquiryActivity;
public class LoadingView extends ConstraintLayout {

    private ViewLoadingBinding binding;
    private boolean isInitialized = false;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }
    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.view_loading, this, true);
    }


    public void setViewModel(Object viewModel) {
        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner is null");
        }

        binding.setLifecycleOwner(lifecycleOwner);
        binding.setVariable(BR.viewModel, viewModel);

        if (viewModel instanceof BaseViewModel) {
            BaseViewModel vm = (BaseViewModel) viewModel;

            vm.loadingState().observe(lifecycleOwner, state -> {
                if (state == null) return;
                switch (state) {
                    case LOADING:
                        Log.d("www", "로딩중: ");
                        setVisibility(VISIBLE);
                        binding.loadingPb.setVisibility(VISIBLE);
                        binding.loadingText.setVisibility(VISIBLE);
                        binding.goToInquiry.setVisibility(GONE);
                        break;
                    case SUCCESS:
                        Log.d("www", "성공: ");
                        setVisibility(GONE);
                        binding.loadingPb.setVisibility(GONE);
                        binding.loadingText.setVisibility(GONE);
                        binding.goToInquiry.setVisibility(GONE);
                        break;
                    case FAILED:
                        Log.d("www", "실패: ");
                        setVisibility(VISIBLE);
                        binding.loadingPb.setVisibility(GONE);
                        binding.loadingText.setVisibility(VISIBLE);
                        binding.goToInquiry.setVisibility(VISIBLE);
                        break;
                    case RETRY:
                        Log.d("www", "재시도: ");
                        setVisibility(VISIBLE);
                        binding.loadingPb.setVisibility(VISIBLE);
                        binding.loadingText.setVisibility(VISIBLE);
                        binding.goToInquiry.setVisibility(GONE);
                        break;
                    default:
                        Log.d("www", "기본: ");
                        setVisibility(GONE);
                        binding.loadingPb.setVisibility(GONE);
                        binding.loadingText.setVisibility(GONE);
                        binding.goToInquiry.setVisibility(GONE);
                }
            });

            vm.loadingMessage().observe(lifecycleOwner,message ->{
                binding.loadingText.setText(message);
            });

            vm.goToInquiryPage().observe(lifecycleOwner,unused -> {
                Intent intent = new Intent(getContext(), InquiryActivity.class);
                getContext().startActivity(intent);
            });
        }
    }
}
