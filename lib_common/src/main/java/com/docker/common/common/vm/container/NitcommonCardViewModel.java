package com.docker.common.common.vm.container;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

/*
 * NitcommonCardFragment 的外层vm
 * */
public class NitcommonCardViewModel extends NitCommonContainerViewModel {


    @Inject
    public NitcommonCardViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void onOuterVmRefresh(NitCommonListVm outerVm) {

    }
}
