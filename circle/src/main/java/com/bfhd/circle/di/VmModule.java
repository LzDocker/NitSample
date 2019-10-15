package com.bfhd.circle.di;

import android.arch.lifecycle.ViewModel;

import com.bfhd.circle.vm.CircleCommentViewModel;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vm.CircleFrameViewModel;
import com.bfhd.circle.vm.CircleGridImageViewModel;
import com.bfhd.circle.vm.CircleListViewModel;
import com.bfhd.circle.vm.CircleNewsViewModel;
import com.bfhd.circle.vm.CirclePersonViewModel;
import com.bfhd.circle.vm.CirclePublishViewModel;
import com.bfhd.circle.vm.CircleRecommendViewModel;
import com.bfhd.circle.vm.CircleTypeViewModel;
import com.bfhd.circle.vm.CircleViewModel;
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
    @ViewModelKey(CircleViewModel.class)
    abstract ViewModel CircleViewModel(CircleViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleNewsViewModel.class)
    abstract ViewModel CircleNewsViewModel(CircleNewsViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleRecommendViewModel.class)
    abstract ViewModel CircleRecommendViewModel(CircleRecommendViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleCommentViewModel.class)
    abstract ViewModel CircleCommentViewModel(CircleCommentViewModel model);



    @Binds
    @IntoMap
    @ViewModelKey(CircleGridImageViewModel.class)
    abstract ViewModel CircleGridImageViewModel(CircleGridImageViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleFrameViewModel.class)
    abstract ViewModel CircleFrameViewModel(CircleFrameViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleListViewModel.class)
    abstract ViewModel CircleListViewModel(CircleListViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirclePersonViewModel.class)
    abstract ViewModel CirclePersonViewModel(CirclePersonViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleTypeViewModel.class)
    abstract ViewModel CircleTypeViewModel(CircleTypeViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CirclePublishViewModel.class)
    abstract ViewModel CirclePublishViewModel(CirclePublishViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(CircleDynamicViewModel.class)
    abstract ViewModel CircleDynamicViewModel(CircleDynamicViewModel model);

}
