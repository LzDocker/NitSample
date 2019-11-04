package com.docker.common.di;

import android.arch.lifecycle.ViewModel;

import com.docker.common.common.vm.NitEmptyVm;
import com.docker.common.common.vm.NitSampleListViewModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.scope.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class VmModule {
//    @Binds
//    @IntoMap
//    @ViewModelKey(ViewDocumentModel.class)
//    abstract ViewModel viewModel(ViewDocumentModel model);

    @Binds
    @IntoMap
    @ViewModelKey(NitEmptyVm.class)
    abstract ViewModel NitEmptyVm(NitEmptyVm model);

    @Binds
    @IntoMap
    @ViewModelKey(NitSampleListViewModel.class)
    abstract ViewModel NitSampleListViewModel(NitSampleListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(NitCommonContainerViewModel.class)
    abstract ViewModel NitCommonContainerViewModel(NitCommonContainerViewModel model);

}