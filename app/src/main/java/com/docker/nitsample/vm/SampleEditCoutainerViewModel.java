package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.bfhd.account.vo.card.AccountIndexItemVo;
import com.bfhd.account.vo.card.AccountSettingCardVo;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.card.AppBannerCardVo;
import com.docker.nitsample.vo.card.SampleCardVo;

import java.util.HashMap;

import javax.inject.Inject;

public class SampleEditCoutainerViewModel extends NitCommonContainerViewModel {

    @Inject
    SampleService sampleService;

    @Inject
    public SampleEditCoutainerViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        AppBannerCardVo appBannerCardVo = new AppBannerCardVo(0, 0);
        SampleCardVo sampleCardVo = new SampleCardVo(0, 0);
        sampleCardVo.setmCardData(appBannerCardVo);
        mItems.add(sampleCardVo);


        AccountHeadCardVo baseCardVo = new AccountHeadCardVo(0, 0);
        HashMap<String, String> postArrMap = new HashMap<>();
        postArrMap.put("uuid", CacheUtils.getUser().uuid);
        baseCardVo.mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap));
        HashMap<String, Object> disArrMap = new HashMap<>();
        String[] userarr = new String[]{"all"};
        disArrMap.put("member", userarr);
        String[] exarr = new String[]{"dynamicNum", "dzNum", "plNum"};
        disArrMap.put("extData", exarr);
        baseCardVo.mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap));
        SampleCardVo sampleCardVo1 = new SampleCardVo(0, 0);
        sampleCardVo1.setmCardData(baseCardVo);
        mItems.add(sampleCardVo1);


        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo(0, 0);
        SampleCardVo sampleCardVo2 = new SampleCardVo(0, 0);
        sampleCardVo2.setmCardData(accountIndexItemVo);
        mItems.add(sampleCardVo2);


        AccountSettingCardVo accountSettingCardVo = new AccountSettingCardVo(0, 0);
        SampleCardVo sampleCardVo3 = new SampleCardVo(0, 0);
        sampleCardVo3.setmCardData(accountSettingCardVo);
        mItems.add(sampleCardVo3);
    }
}
