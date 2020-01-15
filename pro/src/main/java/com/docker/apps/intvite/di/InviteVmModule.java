package com.docker.apps.intvite.di;

import android.arch.lifecycle.ViewModel;

import com.docker.apps.intvite.vm.ProInviteVm;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class InviteVmModule {

    //    @Binds
//    @IntoMap
//    @ViewModelKey(SampleListViewModel.class)
//    abstract ViewModel messageSampleListViewModel(SampleListViewModel model);
//
    @Binds
    @IntoMap
    @ViewModelKey(ProInviteVm.class)
    abstract ViewModel ProInviteVm(ProInviteVm model);


}
