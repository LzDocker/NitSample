package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.api.SampleService;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleEditSpaViewModel extends NitCommonContainerViewModel {

    @Inject
    SampleService sampleService;

    @Inject
    public SampleEditSpaViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    @Override
    public void loadData() {

    }
}
