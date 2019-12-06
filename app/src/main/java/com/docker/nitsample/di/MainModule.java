package com.docker.nitsample.di;


import android.support.v4.app.Fragment;

import com.bfhd.account.ui.index.FragmentMineIndex;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.nitsample.ui.edit.EditListFragment;
import com.docker.nitsample.ui.edit.EditSpaFragment;
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


//        fragments.add(IndexFragment.newInstance());  //11
        fragments.add(EditSpaFragment.getInstance("", true));  //11


//        CommonListOptions commonListOptions = new CommonListOptions();
//        commonListOptions.falg = 0;
//        commonListOptions.refreshState = 3;
//        commonListOptions.isActParent = true;
//        NitCommonContainerFragmentV2 containerFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions);
////        NitCommonCardFragment containerFragment = NitCommonCardFragment.newinstance(commonListOptions);
//        fragments.add(containerFragment);  // 11

//        CommonListOptions commonListOptions1 = new CommonListOptions();
//        commonListOptions1.falg = 1;
//        commonListOptions1.refreshState = 0;
//        commonListOptions1.RvUi = 0;
//        commonListOptions1.ReqParam.put("t", "goods");
//        commonListOptions1.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
//        commonListOptions1.ReqParam.put("memberid", "64");
//        commonListOptions1.ReqParam.put("companyid", "1");
//
//        //shopType2=&shopType1=&t=goods&page=1&goodsui=product&uuid=8621e837a2a1579710a95143e5862424&memberid=64&companyid=1
//        fragments.add(NitCommonContainerFragment.newinstance(commonListOptions1));

//        fragments.add(VideoListFragment.newInstance());
//        fragments.add(VideoListFragment.newInstance()); //22
//        fragments.add(BaseVideoListFragment.newinstance());s


//        fragments.add(VideoFullListFragment.newInstance());
//        fragments.add(IndexFragment.newInstance());
//        fragments.add(FragmentMineIndex.newInstance()); //33
        fragments.add(EditListFragment.newInstance()); //33
        return fragments;
    }


}
