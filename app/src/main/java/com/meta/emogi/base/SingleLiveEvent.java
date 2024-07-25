package com.meta.emogi.base;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 기존 값과 동일한 값을 설정하는 경우에도 onChanged 이벤트가 발생할 수 있도록 하는 MutableLiveData를 상속받는 클래스
 * <br/>
 * 동일한 값이 설정될 일이 없다면 사용하지 않아도 됨.
 *
 * @param <T> 값의 제너릭 타입
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "CustomLiveData";

    private final AtomicBoolean pending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, t -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }

    /**
     * 값이 필요하지 않은 경우
     */
    @MainThread
    public void call() {
        postValue(null);
    }
}
