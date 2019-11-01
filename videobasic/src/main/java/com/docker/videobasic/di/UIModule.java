package com.docker.videobasic.di;


import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.videobasic.ui.SingleVideoActivity;
import com.docker.videobasic.ui.VideoListActivity;
import com.docker.videobasic.ui.VideoListFragment;
import com.docker.videobasic.util.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    //    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MainActivity contributeMainActivitytInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract SingleVideoActivity contributeSingleVideoActivitytInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract VideoListFragment contributeVideoListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract VideoListActivity contributeVideoListActivityInjector();

}

