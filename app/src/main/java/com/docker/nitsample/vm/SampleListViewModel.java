package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.SampleItemVo;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleListViewModel extends NitCommonContainerViewModel {


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

        SampleItemVo xPopup_sample = new SampleItemVo("XPopup", 5);
        mItems.add(xPopup_sample);

        SampleItemVo mine_sample = new SampleItemVo("个人中心", 6);
        mItems.add(mine_sample);

        SampleItemVo message_sample = new SampleItemVo("消息中心", 7);
        mItems.add(message_sample);

        SampleItemVo loc_sample = new SampleItemVo("定位", 8);
        mItems.add(loc_sample);

        SampleItemVo im_sample = new SampleItemVo("im", 9);
        mItems.add(im_sample);

        SampleItemVo pub_sample = new SampleItemVo("发布", 10);
        mItems.add(pub_sample);

        SampleItemVo version_sample = new SampleItemVo("版本更新", 11);
        mItems.add(version_sample);

        SampleItemVo don_sample = new SampleItemVo("文件下载", 12);
        mItems.add(don_sample);

        SampleItemVo rx_sample = new SampleItemVo("RX", 13);
        mItems.add(rx_sample);



    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
