package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

@Deprecated
public class NitSampleListViewModel extends NitCommonListVm {


    @Inject
    public NitSampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);


    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
