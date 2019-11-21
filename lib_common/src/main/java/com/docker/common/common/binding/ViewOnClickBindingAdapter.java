package com.docker.common.common.binding;

import android.databinding.BindingAdapter;
import android.view.View;

import com.docker.common.common.utils.ViewClickObservable;

import java.util.concurrent.TimeUnit;

/**
 * kxf -> 2019-11-20
 **/
public class ViewOnClickBindingAdapter {
    ///防止频繁点击
    @BindingAdapter("android:onClick")
    public static void onClick(final View view, final View.OnClickListener listener) {
        new ViewClickObservable(view).throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(v -> {

                    listener.onClick(view);
                });
    }


}



