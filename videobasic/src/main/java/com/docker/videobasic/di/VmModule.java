package com.docker.videobasic.di;

import android.arch.lifecycle.ViewModel;

import com.docker.core.di.scope.ViewModelKey;
import com.docker.videobasic.vm.SingleVideoVm;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VmModule {

    //    @Binds
//    @IntoMap
//    @ViewModelKey(AccountViewModel.class)
//    abstract ViewModel AccountViewModel(AccountViewModel model);
//
    @Binds
    @IntoMap
    @ViewModelKey(SingleVideoVm.class)
    abstract ViewModel SingleVideoVm(SingleVideoVm model);


}
