package com.docker.apps.active.vm;

import android.arch.lifecycle.LiveData;

import com.docker.apps.active.vo.PersionVo;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class ActivePersionListViewModel extends NitCommonContainerViewModel {


    @Inject
    public ActivePersionListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        PersionVo persionVo = new PersionVo();
        mItems.add(persionVo);
        mItems.add(persionVo);
        mItems.add(persionVo);
        mItems.add(persionVo);
        mItems.add(persionVo);
        mItems.add(persionVo);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
