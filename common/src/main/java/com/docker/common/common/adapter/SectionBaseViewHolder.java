package com.docker.common.common.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SectionBaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private B binding;

    public SectionBaseViewHolder(View itemView) {
        super(itemView);
    };

    public B getBinding() {
        return binding;
    }

    public void setBinding(B binding) {
        this.binding = binding;
    }
}
