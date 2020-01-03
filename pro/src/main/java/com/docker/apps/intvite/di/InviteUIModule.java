package com.docker.apps.intvite.di;


import com.docker.apps.intvite.ui.index.InviteCodeScanActivity;
import com.docker.apps.intvite.ui.index.InviteIndexActivity;
import com.docker.apps.point.ui.index.PointSortActivity;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class InviteUIModule {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract PointSortActivity contributePointSortActivityInjector();
//

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract InviteIndexActivity contributeInviteIndexActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract InviteCodeScanActivity contributeInviteCodeScanActivityInjector();


}

