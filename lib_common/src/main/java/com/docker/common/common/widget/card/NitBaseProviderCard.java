package com.docker.common.common.widget.card;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.dcbfhd.utilcode.utils.CloneUtils;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.ui.container.NitCommonCardNoRefreshFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.ui.container.NitCommonContainerNoRefreshFragmentV2;
import com.docker.common.common.utils.lv.MserialMedatorLv;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.google.gson.reflect.TypeToken;

public class NitBaseProviderCard {

    public static void providerCardForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonCardFragment nitCommonCardFragment = NitCommonCardFragment.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonCardFragment, layid);
    }

    public static void providerCoutainerForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonContainerFragmentV2, layid);
    }

    public static void providerCardNoRefreshForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonCardNoRefreshFragment nitCommonCardNoRefreshFragment = NitCommonCardNoRefreshFragment.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonCardNoRefreshFragment, layid);
    }

    public static void providerCoutainerNoRefreshForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonContainerNoRefreshFragmentV2 nitCommonCardNoRefreshFragment = NitCommonContainerNoRefreshFragmentV2.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonCardNoRefreshFragment, layid);
    }


    public static BaseCardVo processParam(NitCommonListVm outer, BaseCardVo baseCardVo, NitCommonFragment nitCommonListFragment) {
        if (!TextUtils.isEmpty(baseCardVo.mVmPath)) {
            try {
                Class clazz = Class.forName(baseCardVo.mVmPath);
                if (baseCardVo.mCardVoLiveData == null) {
                    baseCardVo.mCardVoLiveData = new MserialMedatorLv();
                }
                NitcommonCardViewModel nitcommonCardViewModel = (NitcommonCardViewModel) ViewModelProviders.of(nitCommonListFragment, nitCommonListFragment.factory).get(clazz);
                nitcommonCardViewModel.loadCardData(baseCardVo);
                baseCardVo.mNitcommonCardViewModel = nitcommonCardViewModel;
                nitCommonListFragment.getLifecycle().addObserver(baseCardVo.mNitcommonCardViewModel);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (baseCardVo.mCardVoLiveData == null) {
            baseCardVo.mCardVoLiveData = new MserialMedatorLv();
        }
        outer.addCardVo(baseCardVo, baseCardVo.position, true);
        baseCardVo.mCardVoLiveData.observe(nitCommonListFragment, o -> {
            if (o == null) {
                outer.mItems.remove(baseCardVo);
            }
        });
        return baseCardVo;
    }

    public static void providerCard(NitCommonListVm outer,
                                    BaseCardVo baseCardVo,
                                    NitCommonFragment nitCommonListFragment) {
        processParam(outer, baseCardVo, nitCommonListFragment);
    }

}
