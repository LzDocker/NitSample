package com.docker.common.common.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.docker.core.util.adapter.CommonRecyclerAdapter;
import com.docker.core.util.adapter.OnchildViewClickListener;


public class NitSampleAdapter<T> extends CommonRecyclerAdapter<T, CommonRecyclerAdapter.ViewHolder<ViewDataBinding>> {

    private OnchildViewClickListener onchildViewClickListener;

    private int[] childId;

    public void setOnchildViewClickListener(int[] childId, OnchildViewClickListener onchildViewClickListener) {
        this.childId = childId;
        this.onchildViewClickListener = onchildViewClickListener;
    }

    public NitSampleAdapter(int layoutId, int brId) {
        super(layoutId, brId);
    }

    @Override
    public ViewHolder<ViewDataBinding> getHolder(View view) {
        return new ViewHolder<ViewDataBinding>(view, childId, onchildViewClickListener);
    }


}
