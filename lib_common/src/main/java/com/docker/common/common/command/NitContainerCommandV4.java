package com.docker.common.common.command;

import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.vm.NitCommonListVm;

/*
 *
 * 用在fragment中
 *
 * T  真实的viewmodel  集成自
 * */
public interface NitContainerCommandV4 extends NitContainerCommand/*, LifecycleObserver*/ {


    void next(NitCommonListVm commonListVm, NitCommonListVm inner, NitCommonListFragment nitCommonListFragment);

}
