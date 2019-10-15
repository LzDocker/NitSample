package com.docker.common.common.widget.refresh.listener;

import android.support.annotation.NonNull;

import com.docker.common.common.widget.refresh.api.RefreshLayout;


/**
 * 刷新监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
