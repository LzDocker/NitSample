package com.docker.nitsample.vm.card;

import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.nitsample.vo.card.AppBannerCardVo;

public class ProviderAppCard extends NitBaseProviderCard {

    public static void providerAppDefaultCard(NitCommonListVm outter,
                                              NitCommonFragment nitCommonListFragment) {

        providerAppCard_banner(outter, null, nitCommonListFragment);
    }


    public static void providerAppCard_banner(NitCommonListVm outer,
                                              BaseCardVo baseCardVo,
                                              NitCommonFragment nitCommonListFragment) {
        if (baseCardVo == null) {  // 提供默认样式
            baseCardVo = new AppBannerCardVo(0, 0);
        }
        processParam(outer, baseCardVo, nitCommonListFragment);
    }

}
