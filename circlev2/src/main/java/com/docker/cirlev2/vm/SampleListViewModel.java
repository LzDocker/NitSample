package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;

import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleListViewModel extends NitCommonContainerViewModel {


    @Inject
    public SampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        SampleItemVo account_sample = new SampleItemVo("圈子入口", 0);
        mItems.add(account_sample);

        SampleItemVo video_sample = new SampleItemVo("圈子创建", 1);
        mItems.add(video_sample);

        SampleItemVo pub_sample = new SampleItemVo("圈子发布", 2);
        mItems.add(pub_sample);

        SampleItemVo detail_sample = new SampleItemVo("圈子详情", 3);
        mItems.add(detail_sample);

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
