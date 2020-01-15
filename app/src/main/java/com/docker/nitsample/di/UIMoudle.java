package com.docker.nitsample.di;


import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.message.ui.index.MessageImFragment;
import com.docker.nitsample.ui.IndexSearchActivity;
import com.docker.nitsample.ui.MainActivity;
import com.docker.nitsample.ui.MainTygsActivity;
import com.docker.nitsample.ui.SplashActivity;
import com.docker.nitsample.ui.WelcomeFragment;
import com.docker.nitsample.ui.WelocomeActivity;
import com.docker.nitsample.ui.edit.EditCoutainerFragment;
import com.docker.nitsample.ui.edit.EditIndexActivity;
import com.docker.nitsample.ui.edit.EditListFragment;
import com.docker.nitsample.ui.edit.EditSpaFragment;
import com.docker.nitsample.ui.edit.PreviewEditActivity;
import com.docker.nitsample.ui.index.IndexFragment;
import com.docker.nitsample.ui.index.IndexTygsFragment;
import com.docker.nitsample.ui.index.SampleFragment;
import com.docker.nitsample.ui.index.SampleListFragment;
import com.docker.nitsample.ui.optimization.OptimizationFragment;
import com.docker.nitsample.ui.search.SearchActivity;
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
    @ContributesAndroidInjector(modules = MainTygsModule.class)
    abstract MainTygsActivity contributeMainTygsActivitytInjector();


    @ActivityScope
    @ContributesAndroidInjector
    abstract IndexFragment contributeIndexFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SampleFragment contributeSampleFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SampleListFragment contributeSampleListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract EditSpaFragment contributeEditSpaFragmenttInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract EditListFragment contributeEditListFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract PreviewEditActivity contributePreviewEditActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract EditIndexActivity contributeEditIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract EditCoutainerFragment contributeEditCoutainerFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract IndexSearchActivity indexSearchActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract IndexTygsFragment contributeIndexTygsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract MessageImFragment contributeMessageImFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract OptimizationFragment contributeOptimizationFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SearchActivity contributeSearchActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract WelcomeFragment contributeWelcomeFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract WelocomeActivity contributeWelocomeActivityInjector();

}
