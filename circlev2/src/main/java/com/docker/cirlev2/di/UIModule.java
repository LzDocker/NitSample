package com.docker.cirlev2.di;


import com.docker.cirlev2.ui.create.CircleCreateActivity;
import com.docker.cirlev2.ui.create.CircleCreateIndexActivity;
import com.docker.cirlev2.ui.detail.CircleDetailIndexActivity;
import com.docker.cirlev2.ui.index.CircleIndexActivity;
import com.docker.cirlev2.ui.index.CircleIndexFragment;
import com.docker.cirlev2.ui.index.CircleSampleActivity;
import com.docker.cirlev2.ui.list.CircleDynamicCoutainerFragment;
import com.docker.cirlev2.ui.list.CircleDynamicListFragment;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleSampleActivity contributeCircleSampleActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleIndexFragment contributeCircleIndexFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleIndexActivity contributeCircleIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicListFragment contributeCircleDynamicListFragmentInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateActivity contributeCircleCreateActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleCreateIndexActivity contributeCircleCreateIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDetailIndexActivity contributeCircleDetailIndexActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract CircleDynamicCoutainerFragment contributeCircleDynamicCoutainerFragmentInjector();




}

