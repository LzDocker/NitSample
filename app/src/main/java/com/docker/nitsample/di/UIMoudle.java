package com.docker.nitsample.di;


import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.nitsample.ui.MainActivity;
import com.docker.nitsample.ui.index.IndexFragment;
import com.docker.nitsample.ui.index.SampleFragment;
import com.docker.nitsample.ui.index.SampleListFragment;
//import com.docker.nitsample.ui.index.IndexFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by zhangxindang on 2018/11/21.
 */

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIMoudle {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MainActivity contributeMainActivitytInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity contributeMainActivitytInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract IndexFragment contributeIndexFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SampleFragment contributeSampleFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SampleListFragment contributeSampleListFragmentInjector();

}
