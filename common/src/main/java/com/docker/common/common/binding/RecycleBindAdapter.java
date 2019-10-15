package com.docker.common.common.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.docker.core.util.LayoutManager;

public class RecycleBindAdapter {
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManager.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }
}
