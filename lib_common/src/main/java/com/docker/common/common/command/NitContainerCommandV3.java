package com.docker.common.common.command;

import com.docker.common.common.vm.NitCommonListVm;

/*
 *
 * 用在fragment中
 *
 * T  真实的viewmodel  集成自
 * */
public interface NitContainerCommandV3 extends NitContainerCommand/*, LifecycleObserver*/ {


    void next(NitCommonListVm commonListVm, NitCommonListVm inner);
}
