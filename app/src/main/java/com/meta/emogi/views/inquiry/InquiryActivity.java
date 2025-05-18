package com.meta.emogi.views.inquiry;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityChatRoomBinding;
import com.meta.emogi.databinding.ActivityInquiryBinding;
import com.meta.emogi.views.toolbar.ToolbarView;

public class InquiryActivity extends BaseActivity<ActivityInquiryBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_inquiry;
    }
    @Override
    protected void setToolbar(ToolbarView.ToolbarRequest toolbarRequest) {
        binding.toolbar.settingView(toolbarRequest);
    }
}