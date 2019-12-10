package com.docker.common.common.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.docker.core.util.LayoutManager;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.LayoutManagers;

public class PagerRecycleBindAdapter {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"pagerSnapHelper"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView, PagerSnapHelper pagerSnapHelper) {
        if (pagerSnapHelper == null) {
            pagerSnapHelper = new PagerSnapHelper();
        }
        pagerSnapHelper.attachToRecyclerView(recyclerView);

    }

}
