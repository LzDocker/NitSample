package com.docker.common.common.binding;

import android.databinding.BindingAdapter;
import android.view.View;

public class visibleGoneBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
