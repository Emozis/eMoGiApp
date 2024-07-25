package com.meta.emogi.views.makecharacter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.meta.emogi.R;
import com.meta.emogi.base.BaseActivity;
import com.meta.emogi.databinding.ActivityMakeCharacterBinding;

public class MakeCharacterActivity extends BaseActivity<ActivityMakeCharacterBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_make_character;
    }
}