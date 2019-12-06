package com.docker.common.common.widget.card;

import android.support.v4.app.FragmentManager;

import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonCardFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;

public class NitBaseProviderCard {


    public static void providerCardForFrame(FragmentManager fragmentManager, int layid, CommonListOptions commonListOptions) {
        NitCommonCardFragment nitCommonCardFragment = NitCommonCardFragment.newinstance(commonListOptions);
        FragmentUtils.add(fragmentManager, nitCommonCardFragment, layid);
    }


    public static BaseCardVo processParam(NitCommonListVm outer, BaseCardVo baseCardVo, NitCommonListFragment nitCommonListFragment) {
        outer.addCardVo(baseCardVo, baseCardVo.position);
        baseCardVo.mCardVoLiveData.observe(nitCommonListFragment, null);
        return baseCardVo;
    }

}
