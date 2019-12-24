package com.docker.nitsample.di;


import android.support.v4.app.Fragment;

import com.bfhd.account.ui.index.FragmentMineIndex;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.nitsample.ui.index.IndexTygsFragment;
import com.docker.videobasic.ui.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MainTygsModule {

    @Provides
    List<Fragment> providerFragments() {
        List<Fragment> fragments = new ArrayList<>();
        //公社首页
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 0;
        commonListOptions.refreshState = 0;
        commonListOptions.RvUi = 0;
        commonListOptions.ReqParam.put("t", "goods");
        commonListOptions.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListOptions.ReqParam.put("memberid", "64");
        commonListOptions.ReqParam.put("companyid", "1");
        fragments.add(IndexTygsFragment.newinstance(commonListOptions));


        //公社优选
        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.falg = 1;
        commonListOptions1.isActParent = true;
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions1.ReqParam.put("", "");
        NitCommonContainerFragmentV2 containerFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(containerFragment);


        //江湖消息
        CommonListOptions commonListOptions2 = new CommonListOptions();
        commonListOptions2.falg = 2;
        commonListOptions2.refreshState = 0;
        commonListOptions2.isActParent = true;
        commonListOptions2.RvUi = 0;
        commonListOptions2.ReqParam.put("t", "goods");
        commonListOptions2.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListOptions2.ReqParam.put("memberid", "64");
        commonListOptions2.ReqParam.put("companyid", "1");
        fragments.add(NitCommonContainerFragmentV2.newinstance(commonListOptions2));


        //个人中心

        CommonListOptions commonListOptions3 = new CommonListOptions();
        commonListOptions3.falg = 3;
        commonListOptions3.isActParent = true;
        commonListOptions3.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions3.refreshState = Constant.KEY_REFRESH_PURSE;
        NitCommonCardFragment nitCommonCardFragment = NitCommonCardFragment.newinstance(commonListOptions3);
        fragments.add(nitCommonCardFragment);

//        // 框架index     SampleListViewModel
//        CommonListOptions commonListOptions0 = new CommonListOptions();
//        commonListOptions0.falg = 0;
//        NitCommonContainerFragment containerFragment0 = NitCommonContainerFragment.newinstance(commonListOptions0);
//        fragments.add(containerFragment0);

        return fragments;
    }


}

