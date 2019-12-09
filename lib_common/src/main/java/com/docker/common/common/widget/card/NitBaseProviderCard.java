package com.docker.common.common.widget.card;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;

public class NitBaseProviderCard {


    public static void providerCardForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonCardFragment nitCommonCardFragment = NitCommonCardFragment.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonCardFragment, layid);
    }


    public static BaseCardVo processParam(NitCommonListVm outer, BaseCardVo baseCardVo, NitCommonListFragment nitCommonListFragment) {
        if (!TextUtils.isEmpty(baseCardVo.mVmPath)) {
            try {
                Class clazz = Class.forName(baseCardVo.mVmPath);
                NitcommonCardViewModel nitcommonCardViewModel = (NitcommonCardViewModel) ViewModelProviders.of(nitCommonListFragment, nitCommonListFragment.factory).get(clazz);
                nitcommonCardViewModel.loadCardData(baseCardVo);
                baseCardVo.mNitcommonCardViewModel = nitcommonCardViewModel;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        outer.addCardVo(baseCardVo, baseCardVo.position, true);
        baseCardVo.mCardVoLiveData.observe(nitCommonListFragment, o -> {
            if (o == null) {
                outer.removeData(baseCardVo);
            }
        });
        return baseCardVo;
    }

    public static void providerCard(NitCommonListVm outer,
                                    BaseCardVo baseCardVo,
                                    NitCommonListFragment nitCommonListFragment) {
        processParam(outer, baseCardVo, nitCommonListFragment);
    }

}
