package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.NitCommonListVm;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;
import com.docker.nitsample.api.SampleService;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleNetListViewModel extends NitCommonListVm {

    @Inject
    SampleService sampleService;

    @Inject
    public SampleNetListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return sampleService.fechCircleInfoList(param);
    }
}
