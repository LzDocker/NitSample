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

        SampleItemVo pic_sample = new SampleItemVo("图片选择", 14);
        mItems.add(pic_sample);

        SampleItemVo oss_sample = new SampleItemVo("oss上传", 15);
        mItems.add(oss_sample);

        SampleItemVo locfile_sample = new SampleItemVo("本地文件管理", 16);
        mItems.add(locfile_sample);

        SampleItemVo perr_sample = new SampleItemVo("权限管理", 17);
        mItems.add(perr_sample);

        SampleItemVo train_sample = new SampleItemVo("培训考试模块", 18);
        mItems.add(train_sample);

        SampleItemVo search_sample = new SampleItemVo("搜索", 19);
        mItems.add(search_sample);

        SampleItemVo banner_sample = new SampleItemVo("banner", 20);
        mItems.add(banner_sample);

        SampleItemVo picker_sample = new SampleItemVo("选择picker", 21);
        mItems.add(picker_sample);

        SampleItemVo map_sample = new SampleItemVo("地图", 22);
        mItems.add(map_sample);

        SampleItemVo pdf_sample = new SampleItemVo("pdf..文件预览", 23);
        mItems.add(pdf_sample);

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
