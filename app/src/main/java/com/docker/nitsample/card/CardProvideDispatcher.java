package com.docker.nitsample.card;

import com.bfhd.account.vm.card.ProviderAccountCard;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.nitsample.vm.card.ProviderAppCard;

import java.util.ArrayList;

public class CardProvideDispatcher {


    public static void dispatcherCard(NitCommonListVm commonListVm, NitCommonListFragment nitCommonListFragment, ArrayList<BaseItemModel> baseItemModels) {

        for (int i = 0; i < baseItemModels.size(); i++) {
            NitBaseProviderCard.providerCard(commonListVm, (BaseCardVo) baseItemModels.get(i), nitCommonListFragment);
        }
    }


    public static void dispatcherCardDefault(NitCommonListVm commonListVm, NitCommonListFragment nitCommonListFragment) {


        ProviderAccountCard.providerAccountDefaultCard(commonListVm, nitCommonListFragment);
//        ProviderAppCard.providerAppDefaultCard(commonListVm, nitCommonListFragment);


    }

}
