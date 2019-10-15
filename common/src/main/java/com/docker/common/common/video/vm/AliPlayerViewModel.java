package com.docker.common.common.video.vm;

import com.docker.common.api.OpenService;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.CommonRepository;

import javax.inject.Inject;

public class AliPlayerViewModel extends NitCommonVm {
    @Inject
    public AliPlayerViewModel() {
    }

    @Override
    public void initCommand() {

    }

    @Inject
    CommonRepository commonRepository;

    @Inject
    OpenService openService;


}
