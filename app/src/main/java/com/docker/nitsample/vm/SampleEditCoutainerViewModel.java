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
        for (int i = 0; i < appBannerCardVo.maxSupport; i++) {
            AppBannerCardVo appBannerCardVo1 = new AppBannerCardVo(i, 0);
            appBannerCardVo1.sampleName = "AppBannerCardVo_style_" + i;
            SampleCardVo sampleCardVo = new SampleCardVo(0, 0);
            sampleCardVo.setmCardData(appBannerCardVo1);
            mItems.add(sampleCardVo);
        }

        AccountHeadCardVo baseCardVo = new AccountHeadCardVo(0, 0);
        for (int i = 0; i < baseCardVo.maxSupport; i++) {
            AccountHeadCardVo baseCardVo1 = new AccountHeadCardVo(i, 0);
            HashMap<String, String> postArrMap = new HashMap<>();
            postArrMap.put("uuid", CacheUtils.getUser().uuid);
            baseCardVo1.mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap));
            HashMap<String, Object> disArrMap = new HashMap<>();
            String[] userarr = new String[]{"all"};
            disArrMap.put("member", userarr);
            String[] exarr = new String[]{"dynamicNum", "dzNum", "plNum"};
            disArrMap.put("extData", exarr);
            baseCardVo1.mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap));
            baseCardVo1.sampleName = "AccountHeadCardVo_style_" + i;
            SampleCardVo sampleCardVo = new SampleCardVo(0, 0);
            sampleCardVo.setmCardData(baseCardVo1);
            mItems.add(sampleCardVo);
        }


        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo(0, 0);
        for (int i = 0; i < accountIndexItemVo.maxSupport; i++) {
            AccountIndexItemVo accountIndexItemVo1 = new AccountIndexItemVo(i, 0);
            accountIndexItemVo1.sampleName = "AccountIndexItemVo_style_" + i;
            SampleCardVo sampleCardVo = new SampleCardVo(0, 0);
            sampleCardVo.setmCardData(accountIndexItemVo1);
            mItems.add(sampleCardVo);
        }


        AccountSettingCardVo accountSettingCardVo = new AccountSettingCardVo(0, 0);
        for (int i = 0; i < accountSettingCardVo.maxSupport; i++) {
            AccountSettingCardVo accountSettingCardVo1 = new AccountSettingCardVo(i, 0);
            accountSettingCardVo1.sampleName = "AccountSettingCardVo_style_" + i;
            SampleCardVo sampleCardVo = new SampleCardVo(0, 0);
            sampleCardVo.setmCardData(accountSettingCardVo1);
            mItems.add(sampleCardVo);
        }
    }
}
