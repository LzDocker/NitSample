package com.docker.nitsample.di;


import android.arch.lifecycle.ViewModel;

import com.bfhd.circle.base.EmptyVm;
import com.docker.core.di.scope.ViewModelKey;
import com.docker.nitsample.vm.EditListViewModel;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.OptimizationModel;
import com.docker.nitsample.vm.PreviewViewModel;
import com.docker.nitsample.vm.SampleEditCoutainerViewModel;
import com.docker.nitsample.vm.SampleEditSpaViewModel;
import com.docker.nitsample.vm.SampleListViewModel;
import com.docker.nitsample.vm.SampleNetListViewModel;
import com.docker.nitsample.vm.card.AppCardViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by zhangxindang on 2018/11/21.
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EmptyVm.class)
    abstract ViewModel EmptyVm(EmptyVm model);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel MainViewModel(MainViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SampleListViewModel.class)
    abstract ViewModel SampleListViewModel(SampleListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SampleNetListViewModel.class)
    abstract ViewModel SampleNetListViewModel(SampleNetListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AppCardViewModel.class)
    abstract ViewModel AppCardViewModel(AppCardViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SampleEditSpaViewModel.class)
    abstract ViewModel SampleEditSpaViewModel(SampleEditSpaViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(EditListViewModel.class)
    abstract ViewModel EditListViewModel(EditListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(PreviewViewModel.class)
    abstract ViewModel PreviewViewModel(PreviewViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SampleEditCoutainerViewModel.class)
    abstract ViewModel SampleEditCoutainerViewModel(SampleEditCoutainerViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(OptimizationModel.class)
    abstract ViewModel OptimizationModel(OptimizationModel model);



//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainTabViewModel.class)
//    abstract ViewModel MainTabViewModel(MainTabViewModel model);


}
