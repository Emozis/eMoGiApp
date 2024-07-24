package com.meta.emogi.di;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
public interface IViewModelFactory {
    @NonNull
    <T extends ViewModel> T create(@NonNull Class<T> modelClass);
}
