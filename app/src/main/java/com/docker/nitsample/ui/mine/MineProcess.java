package com.docker.nitsample.ui.mine;

import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.bfhd.account.vo.card.AccountIndexItemVo;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.HashMap;

public class MineProcess {

    public static void processMineFrame(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
//        if (CacheUtils.getUser() == null) {
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
//            return;
//        }

        AccountHeadCardVo accountHeadCardVo = new AccountHeadCardVo(2, 0);
        accountHeadCardVo.isNoNetNeed = true;

        if (CacheUtils.getUser() != null) {
            HashMap<String, String> postArrMap = new HashMap<>();
            postArrMap.put("uuid", CacheUtils.getUser().uuid);
            accountHeadCardVo.mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap));
            HashMap<String, Object> disArrMap = new HashMap<>();
            String[] userarr = new String[]{"all"};
            disArrMap.put("member", userarr);
            String[] exarr = new String[]{"dynamicNum", "dzNum", "plNum"};
            disArrMap.put("extData", exarr);
            accountHeadCardVo.mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap));
        }

        accountHeadCardVo.sampleName = "AccountHeadCardVo_style_";
        NitBaseProviderCard.providerCard(commonListVm, accountHeadCardVo, nitCommonFragment);


        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo(2, 1);
        NitBaseProviderCard.providerCard(commonListVm, accountIndexItemVo, nitCommonFragment);


        AccountIndexItemVo accountIndexItemVo2 = new AccountIndexItemVo(3, 2);
        NitBaseProviderCard.providerCard(commonListVm, accountIndexItemVo2, nitCommonFragment);

    }
}
