package com.bfhd.account.vm.card;

import com.bfhd.account.vo.index.AccountIndexItemVo;
import com.bfhd.account.vo.index.setting.AccountSettingCardVo;
import com.bfhd.account.vo.module.mine.AccountHeadCardVo;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.HashMap;

public class ProviderAccountCard extends NitBaseProviderCard {

    public static void providerAccountDefaultCard(NitCommonListVm outter,
                                                  NitCommonListFragment nitCommonListFragment) {
        providerAccountCard_header(outter, null, nitCommonListFragment);
        providerAccountCard_menu(outter, null, nitCommonListFragment);
        providerAccountCard_setting(outter, null, nitCommonListFragment);
    }


    public static void providerAccountCard_header(NitCommonListVm outer,
                                                  BaseCardVo baseCardVo,
                                                  NitCommonListFragment nitCommonListFragment) {
        if (baseCardVo == null) {  // 提供默认样式 默认接口参数
            baseCardVo = new AccountHeadCardVo(0, 0);
            HashMap<String, String> postArrMap = new HashMap<>();
            postArrMap.put("uuid", CacheUtils.getUser().uuid);
            baseCardVo.mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap));
            HashMap<String, Object> disArrMap = new HashMap<>();
            String[] userarr = new String[]{"all"};
            disArrMap.put("member", userarr);
            String[] exarr = new String[]{"dynamicNum", "dzNum", "plNum"};
            disArrMap.put("extData", exarr);
            baseCardVo.mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap));
        }
        processParam(outer, baseCardVo, nitCommonListFragment);
    }

    public static void providerAccountCard_menu(NitCommonListVm outer,
                                                BaseCardVo baseCardVo,
                                                NitCommonListFragment nitCommonListFragment) {
        if (baseCardVo == null) {  // 提供默认样式
            baseCardVo = new AccountIndexItemVo(0, 0);
        }
        processParam(outer, baseCardVo, nitCommonListFragment);
    }


    public static void providerAccountCard_setting(NitCommonListVm outer,
                                                   BaseCardVo baseCardVo,
                                                   NitCommonListFragment nitCommonListFragment) {
        if (baseCardVo == null) {  // 提供默认样式
            baseCardVo = new AccountSettingCardVo(0, 0);
        }
        processParam(outer, baseCardVo, nitCommonListFragment);
    }

}
