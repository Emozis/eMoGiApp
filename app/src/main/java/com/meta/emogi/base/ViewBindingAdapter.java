package com.meta.emogi.base;

import android.view.View;
import androidx.databinding.BindingAdapter;

public class ViewBindingAdapter {

    @BindingAdapter("app:isVisible")
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}