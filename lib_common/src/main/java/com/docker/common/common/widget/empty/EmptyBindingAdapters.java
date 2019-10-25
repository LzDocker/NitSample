package com.docker.common.common.widget.empty;

import android.databinding.BindingAdapter;

public class EmptyBindingAdapters {
    @BindingAdapter(value = {"onRetryCommand"})
    public static void setonRetryCommand(EmptyLayout view, final EmptyCommand onRetryCommand) {
        view.setOnretryListener(() -> onRetryCommand.exectue());
    }
}
