package com.meta.emogi.base;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.meta.emogi.R;

public class ViewBindingAdapter {

    @BindingAdapter("app:isVisible")
    public static void setVisibility(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setTextColor")
    public static void setTextColor(TextView v, boolean condition) {
        Context context = v.getContext();
        if (!condition) {
            v.setTextColor(context.getResources().getColor(R.color.red, null));
        } else {
            v.setTextColor(context.getResources().getColor(R.color.white, null));
        }
    }
}