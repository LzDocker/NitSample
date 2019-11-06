package com.docker.message.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.message.vo.SampleItemVo;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleListViewModel extends NitCommonContainerViewModel {


    @Inject
    public SampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        SampleItemVo account_sample = new SampleItemVo("卡片风格", 0);
        mItems.add(account_sample);

        SampleItemVo video_sample = new SampleItemVo("列表风格", 1);
        mItems.add(video_sample);

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
