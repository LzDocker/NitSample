package com.docker.nitsample.card;

import com.bfhd.account.vm.card.ProviderAccountCard;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.card.mark.CardMark;
import com.docker.nitsample.vm.card.ProviderAppCard;

import java.util.ArrayList;

public class CardProvideDispatcher {


    public static void dispatcherCard(NitCommonListVm commonListVm, NitCommonListVm[] innerArr, NitCommonListFragment nitCommonListFragment, ArrayList<BaseItemModel> baseItemModels) {

        for (int i = 0; i < innerArr.length; i++) {

            if (innerArr[i] instanceof CardMark.AccountCardMark) {
                for (int j = 0; j < baseItemModels.size(); j++) {
                    if (baseItemModels.get(j) instanceof BaseCardVo) {
                        ProviderAccountCard.providerAccountCardForConfig(commonListVm, innerArr[i], (BaseCardVo) baseItemModels.get(j), nitCommonListFragment);
                    }
                }
            }

            if (innerArr[i] instanceof CardMark.AppCardMark) {
                for (int j = 0; j < baseItemModels.size(); j++) {
                    if (baseItemModels.get(j) instanceof BaseCardVo) {
                        ProviderAppCard.providerAppCardForConfig(commonListVm, innerArr[i], (BaseCardVo) baseItemModels.get(j), nitCommonListFragment);
                    }
                }
            }

        }
    }


    public static void dispatcherCardDefault(NitCommonListVm commonListVm, NitCommonListVm[] innerArr, NitCommonListFragment nitCommonListFragment) {

        for (int i = 0; i < innerArr.length; i++) {

            if (innerArr[i] instanceof CardMark.AccountCardMark) {
                ProviderAccountCard.providerAccountDefaultCard(commonListVm, innerArr[i], nitCommonListFragment);
            }

            if (innerArr[i] instanceof CardMark.AppCardMark) {
                ProviderAppCard.providerAppDefaultCard(commonListVm, innerArr[i], nitCommonListFragment);
            }
        }
    }

}
