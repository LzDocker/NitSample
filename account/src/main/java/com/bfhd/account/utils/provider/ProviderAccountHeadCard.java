package com.bfhd.account.utils.provider;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bfhd.account.ui.index.FragmentMineIndex;
import com.bfhd.account.vm.card.AccountHeadCardViewModel;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vm.NitCommonListVm;

public class ProviderAccountHeadCard {

    public static AccountHeadCardViewModel providerAccountHead(FragmentActivity activity,
                                                               ViewModelProvider.Factory factory,
                                                               NitCommonListVm outListVm,
                                                               CommonListOptions commonListOptions) {

        AccountHeadCardViewModel accountHeadCardViewModel = ViewModelProviders.of(activity, factory).get(AccountHeadCardViewModel.class);
        int position = 0;
        if (commonListOptions.externs.get("position") != null) {
            position = (int) commonListOptions.externs.get("position");
        }
        if (commonListOptions.externs.get("style") != null) {
            accountHeadCardViewModel.accountHeadCardVo.style = (int) commonListOptions.externs.get("style");
        }
        outListVm.addCardVo(accountHeadCardViewModel.accountHeadCardVo, position);
        activity.getLifecycle().addObserver(accountHeadCardViewModel);
        accountHeadCardViewModel.mServerLiveData.observe(activity, null);
        accountHeadCardViewModel.loadCardData(commonListOptions);
        return accountHeadCardViewModel;
    }


    public static AccountHeadCardViewModel providerAccountHead(FragmentActivity activity,
                                                               ViewModelProvider.Factory factory,
                                                               NitCommonListVm outListVm,
                                                               int position) {
        AccountHeadCardViewModel accountHeadCardViewModel = ViewModelProviders.of(activity, factory).get(AccountHeadCardViewModel.class);
        outListVm.addCardVo(accountHeadCardViewModel.accountHeadCardVo, position);
        activity.getLifecycle().addObserver(accountHeadCardViewModel);
        accountHeadCardViewModel.mServerLiveData.observe(activity, null);
        accountHeadCardViewModel.fetchMyInfo();
        return accountHeadCardViewModel;
    }

    public static AccountHeadCardViewModel providerAccountHead(Fragment fragment,
                                                               ViewModelProvider.Factory factory,
                                                               NitCommonListVm outListVm,
                                                               int position) {
        AccountHeadCardViewModel accountHeadCardViewModel = ViewModelProviders.of(fragment, factory).get(AccountHeadCardViewModel.class);
        outListVm.addCardVo(accountHeadCardViewModel.accountHeadCardVo, position);
        fragment.getLifecycle().addObserver(accountHeadCardViewModel);
        accountHeadCardViewModel.mServerLiveData.observe(fragment, null);
        accountHeadCardViewModel.fetchMyInfo();
        return accountHeadCardViewModel;
    }


    public static void providerAccountHeadForFrame(FragmentManager fragmentManager,
                                                   int layid) {
        FragmentUtils.add(fragmentManager, FragmentMineIndex.newInstance(), layid);

    }


}
