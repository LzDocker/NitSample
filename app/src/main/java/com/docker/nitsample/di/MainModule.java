package com.docker.nitsample.di;


import android.support.v4.app.Fragment;

import com.bfhd.account.ui.index.FragmentMineIndex;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.nitsample.ui.index.IndexFragment;
import com.docker.nitsample.ui.index.SampleFragment;
import com.docker.nitsample.ui.index.SampleListFragment;
import com.docker.videobasic.ui.VideoFullListFragment;
import com.docker.videobasic.ui.VideoListFragment;
import com.docker.videobasic.util.videolist.BaseVideoListFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    List<Fragment> providerFragments() {
        List<Fragment> fragments = new ArrayList<>();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 0;
//        fragments.add(SampleFragment.newInstance());
        NitCommonContainerFragment containerFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        fragments.add(containerFragment);


//        fragments.add(SampleVideoListFragment.newInstance());

        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.falg = 1;
        commonListOptions1.refreshState = 0;
        commonListOptions1.RvUi = 0;
        commonListOptions1.ReqParam.put("t", "goods");
        commonListOptions1.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListOptions1.ReqParam.put("memberid", "64");
        commonListOptions1.ReqParam.put("companyid", "1");

        //shopType2=&shopType1=&t=goods&page=1&goodsui=product&uuid=8621e837a2a1579710a95143e5862424&memberid=64&companyid=1
        fragments.add(NitCommonContainerFragment.newinstance(commonListOptions1));

        fragments.add(VideoListFragment.newInstance());
        fragments.add(VideoListFragment.newInstance());
//        fragments.add(BaseVideoListFragment.newinstance());


//        fragments.add(VideoFullListFragment.newInstance());
//        fragments.add(IndexFragment.newInstance());
//        fragments.add(FragmentMineIndex.newInstance());
        return fragments;
    }


}
