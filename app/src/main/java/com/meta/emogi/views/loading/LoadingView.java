package com.meta.emogi.views.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.meta.emogi.databinding.ViewLoadingBinding;
public class LoadingView extends ConstraintLayout {

    private ViewLoadingBinding binding;
    private LoadingViewModel viewModel;

    public LoadingView(@NonNull Context context) {
        super(context);
        init(context);
    }
    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        binding = ViewLoadingBinding.inflate(LayoutInflater.from(context), this, true);


    }


}
