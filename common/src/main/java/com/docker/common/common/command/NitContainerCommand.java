package com.docker.common.common.command;

import android.arch.lifecycle.LifecycleObserver;

import java.io.Serializable;

/*
 * 带有生命周期感知的初始化VM接口
 * 用在fragment中
 *
 * T  真实的viewmodel  集成自
 * */
public interface NitContainerCommand extends Serializable/*, LifecycleObserver*/ {

    /*
     * 提供viewmodel  在inactivitycreated中被调用
     * */
    Class exectue();

}
