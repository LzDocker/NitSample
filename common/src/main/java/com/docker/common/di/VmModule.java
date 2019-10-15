package com.docker.common.di;

import android.arch.lifecycle.ViewModel;

import com.docker.common.common.video.vm.AliPlayerViewModel;
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
    @ViewModelKey(AliPlayerViewModel.class)
    abstract ViewModel aliPlayerModel(AliPlayerViewModel model);

}
