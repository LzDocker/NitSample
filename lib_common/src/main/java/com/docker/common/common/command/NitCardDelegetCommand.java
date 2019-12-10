package com.docker.common.common.command;

import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;


public interface NitCardDelegetCommand {

    /*
     * 初始化完成 所有的vm 后给外部调用
     * */
    void next(NitCommonListVm commonListVm, NitCommonListFragment nitCommonListFragment);

}
