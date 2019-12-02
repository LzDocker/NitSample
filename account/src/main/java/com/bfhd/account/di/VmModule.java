package com.bfhd.account.di;

import android.arch.lifecycle.ViewModel;

import com.bfhd.account.vm.AccountIndexListViewModel;
import com.bfhd.account.vm.AccountSettingViewModel;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vm.card.AccountHeadCardViewModel;
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


}
