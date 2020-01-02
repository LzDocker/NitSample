package com.bfhd.account.di;

import android.arch.lifecycle.ViewModel;

import com.bfhd.account.vm.AccountAttentionViewModel;
import com.bfhd.account.vm.AccountBranchViewModel;
import com.bfhd.account.vm.AccountIndexListViewModel;
import com.bfhd.account.vm.AccountOrderViewModel;
import com.bfhd.account.vm.AccountPersonInfoViewModel;
import com.bfhd.account.vm.AccountPointViewModel;
import com.bfhd.account.vm.AccountRewardViewModel;
import com.bfhd.account.vm.AccountSettingViewModel;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vm.card.AccountHeadCardViewModel;
import com.bfhd.account.vm.card.AccountPointHeadCardViewModel;
import com.bfhd.account.vm.card.AccountPointRecycleViewModel;
import com.bfhd.account.vm.card.AccountRewardHeadCardViewModel;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VmModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(AccountViewModel.class)
//    abstract ViewModel AccountViewModel(AccountViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    abstract ViewModel AccountViewModel(AccountViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountIndexListViewModel.class)
    abstract ViewModel AccountIndexListViewModel(AccountIndexListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountSettingViewModel.class)
    abstract ViewModel AccountSettingViewModel(AccountSettingViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountHeadCardViewModel.class)
    abstract ViewModel MineCardInfoViewModel(AccountHeadCardViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountPersonInfoViewModel.class)
    abstract ViewModel AccountPersonInfoViewModel(AccountPersonInfoViewModel model);


    @Binds
    @IntoMap
    @ViewModelKey(AccountAttentionViewModel.class)
    abstract ViewModel AccountAttentionViewModel(AccountAttentionViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountPointViewModel.class)
    abstract ViewModel AccountPointViewModel(AccountPointViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountRewardHeadCardViewModel.class)
    abstract ViewModel AccountRewardHeadCardViewModel(AccountRewardHeadCardViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountPointHeadCardViewModel.class)
    abstract ViewModel AccountPointHeadCardViewModel(AccountPointHeadCardViewModel model);
     @Binds
    @IntoMap
    @ViewModelKey(AccountPointRecycleViewModel.class)
    abstract ViewModel AccountPointRecycleViewModel(AccountPointRecycleViewModel model);
     @Binds
    @IntoMap
    @ViewModelKey(AccountRewardViewModel.class)
    abstract ViewModel AccountRewardViewModel(AccountRewardViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountBranchViewModel.class)
    abstract ViewModel AccountBranchViewModel(AccountBranchViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AccountOrderViewModel.class)
    abstract ViewModel AccountOrderViewModel(AccountOrderViewModel model);


}
