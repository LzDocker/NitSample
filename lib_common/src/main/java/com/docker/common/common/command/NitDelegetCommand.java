package com.docker.common.common.command;

import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;

/*
 *
 *
 *
 *
 * */
public interface NitDelegetCommand {

    /*
     *提供外部 listvm
     * */
    Class providerOuterVm();

    /*
     * 提供card vm  数组
     * */
    Class[] providerInnerVm();


    /*
     * 初始化完成 所有的vm 后给外部调用
     * */
    void next(NitCommonListVm commonListVm, NitCommonListVm[] innerArr, NitCommonListFragment nitCommonListFragment);

}
