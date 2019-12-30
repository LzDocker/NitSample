package com.docker.common.di;

import android.arch.lifecycle.ViewModel;

import com.docker.common.common.vm.CommonAddressViewModel;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.common.common.vm.NitSampleListViewModel;
import com.docker.common.common.vm.RxDemoViewModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
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

    @Binds
    @IntoMap
    @ViewModelKey(RxDemoViewModel.class)
    abstract ViewModel rxDemoViewModel(RxDemoViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(NitcommonCardViewModel.class)
    abstract ViewModel NitcommonCardViewModel(NitcommonCardViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(NitEmptyViewModel.class)
    abstract ViewModel NitEmptyViewModel(NitEmptyViewModel model);


    @Binds
    @IntoMap
    @ViewModelKey(CommonAddressViewModel.class)
    abstract ViewModel CommonAddressViewModel(CommonAddressViewModel model);
}
