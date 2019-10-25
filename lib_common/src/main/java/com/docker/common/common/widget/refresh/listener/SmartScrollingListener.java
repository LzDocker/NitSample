package com.docker.common.common.widget.refresh.listener;

import android.view.MotionEvent;

import com.docker.common.common.widget.refresh.constant.RefreshState;


public interface SmartScrollingListener {

    public void onScrollingListener(MotionEvent e, RefreshState mState, float dy);

}
