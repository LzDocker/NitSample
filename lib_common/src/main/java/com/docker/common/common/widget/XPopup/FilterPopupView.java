package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.docker.common.R;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.lxj.xpopup.impl.PartShadowPopupView;

import io.reactivex.annotations.NonNull;

public class FilterPopupView extends PartShadowPopupView {
    public FilterPopupView(@NonNull Context context) {
        super(context);
    }

    public RecyclerView recyclerView;

    public TextView tvClear;

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_filter_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    public void providerPopAdapter(NitAbsSampleAdapter mAdapter) {
        recyclerView = findViewById(R.id.poprecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

}
