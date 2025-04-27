package com.meta.emogi.base;
import static com.meta.emogi.MyApplication.getDeviceHeightPx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.meta.emogi.R;
import com.meta.emogi.views.menu.MenuActivity;
import com.meta.emogi.di.ViewModelFactory;
import com.meta.emogi.views.profile.ProfileActivity;
import com.meta.emogi.views.toolbar.ToolbarView;
import com.meta.emogi.views.toolbar.ToolbarViewModel;
public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected V binding;
    private ToolbarViewModel toolbarViewModel;

    private boolean backStatus = false;

    protected abstract @LayoutRes int layoutId();

    protected abstract void setToolbar(ToolbarView.ToolbarRequest toolbarRequest);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutId());
        binding.setLifecycleOwner(this);

        toolbarViewModel = new ViewModelFactory(this).get(ToolbarViewModel.class);

        toolbarViewModel.back().observe(this, unused -> {

            if (backStatus) {
                Log.d("www", "프레그먼트 뒤로가기눌림");
                getOnBackPressedDispatcher().onBackPressed();
                backStatus =false;
            }else{
                Log.d("www", "액티비티 뒤로가기눌림");
                onBackPressedAction();
            }

        });

//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Log.w("www", "onBackPressedDispatcher에서 호출됨");
//                onBackPressedAction();
//            }
//        });

        setStatusBarColor();
    }


    public void changeBackStatus(){
        backStatus =true;
    }

    private void setStatusBarColor(){
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main_black));
    }

    protected void onBackPressedAction() {
        Intent intent = new Intent(BaseActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    protected void setToolbarHeight(ToolbarView toolbar) {
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) toolbar.getLayoutParams();

        int heightInPx = (int) (getDeviceHeightPx() * 0.1);

        layoutParams.height = heightInPx;
        toolbar.setLayoutParams(layoutParams);
    }

}
