package com.bfhd.account.di;

import android.arch.lifecycle.ViewModel;

import com.bfhd.account.vm.AccountViewModel;
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


}
