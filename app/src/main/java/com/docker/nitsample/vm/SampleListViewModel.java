package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;
import com.docker.nitsample.vo.SampleItemVo;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleListViewModel extends NitCommonListVm {


    @Inject
    public SampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        SampleItemVo account_sample = new SampleItemVo("登录注册", 0);
        mItems.add(account_sample);

        SampleItemVo video_sample = new SampleItemVo("视频模块", 1);
        mItems.add(video_sample);

        SampleItemVo address_sample = new SampleItemVo("城市选择", 2);
        mItems.add(address_sample);

        SampleItemVo circle_sample = new SampleItemVo("圈子", 3);
        mItems.add(circle_sample);

        SampleItemVo audio_sample = new SampleItemVo("音频", 4);
        mItems.add(audio_sample);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
