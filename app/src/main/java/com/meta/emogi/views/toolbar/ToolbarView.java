package com.meta.emogi.views.toolbar;

import android.app.Application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meta.emogi.R;
import com.meta.emogi.databinding.ViewToolbarBinding;
import com.meta.emogi.di.ViewModelFactory;

public class ToolbarView extends ConstraintLayout {

    private ToolbarViewModel viewModel;
    private ViewToolbarBinding binding;

    public ToolbarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToolbarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolbarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ToolbarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Application application = (Application) context.getApplicationContext();

        viewModel = new ViewModelFactory((ViewModelStoreOwner) getContext()).get(ToolbarViewModel.class);

        binding = DataBindingUtil.inflate(li, R.layout.view_toolbar, null, false);
        binding.setLifecycleOwner((LifecycleOwner) getContext());
        binding.setViewModel(viewModel);
        addView(binding.getRoot(), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        viewModel.buttonClicked().observe((LifecycleOwner) getContext(), btnResId -> {
            if (clickListener != null)
                clickListener.onClick(btnResId);
        });
    }

    public void settingView(ToolbarRequest toolbarRequest) {
        if (toolbarRequest != null) {
            this.clickListener = toolbarRequest.callback;
            viewModel.setTitle(toolbarRequest.title);
        }
    }

    public static class ToolbarRequest {

        private String title = null;
        private String button = null;
        private ToolbarButtonClickListener callback = null;

        public ToolbarRequest(String title) {
            this.title = title;
        }

        public ToolbarRequest(String title, ToolbarButtonClickListener callback) {
            this.title = title;
            this.callback = callback;
        }

        public ToolbarRequest(String title,String button, ToolbarButtonClickListener callback) {
            this.title = title;
            this.button = button;
            this.callback = callback;
        }
    }

    public void setLogout(ToolbarRequest toolbarRequest,Boolean isVisible) {
        if (toolbarRequest != null) {
            this.clickListener = toolbarRequest.callback;
            int visible = isVisible?View.VISIBLE : View.GONE;
            binding.logout.setVisibility(visible);
        }
    }

    public void setTemporarySave(ToolbarRequest toolbarRequest,Boolean isVisible) {
        if (toolbarRequest != null) {
            this.clickListener = toolbarRequest.callback;
            // ViewModel도 업데이트하여 Data Binding과 동기화
            viewModel.setVisibleSaveButton(isVisible);
            int visible = isVisible?View.VISIBLE : View.GONE;
            binding.btnTempSave.setVisibility(visible);
        }
    }

    private ToolbarButtonClickListener clickListener;

    public interface ToolbarButtonClickListener {
        void onClick(int btnId);
    }
}
