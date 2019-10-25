package com.docker.common.common.vm.container;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.common.common.vm.NitCommonListVm;

import javax.inject.Inject;

/*
 *
 * mark
 *
 * 只做继承使用
 * */
public class NitCommonContainerViewModel extends NitCommonListVm {

    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mContainerLiveData = new MediatorLiveData<Object>();


    @Inject
    public NitCommonContainerViewModel() {

    }
}
