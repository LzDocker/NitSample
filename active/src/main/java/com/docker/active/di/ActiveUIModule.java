package com.docker.active.di;


import com.docker.active.ui.detail.ActiveDetailActivity;
import com.docker.active.ui.detail.ActiveDetailFragment;
import com.docker.active.ui.detail.ActiveResultActivity;
import com.docker.active.ui.detail.ActiveSuccActivity;
import com.docker.active.ui.index.ActiveContainerFragment;
import com.docker.active.ui.index.ActiveIndexActivity;
import com.docker.active.ui.index.ActiveListFragment;
import com.docker.active.ui.manager.ActiveManagerDetailActivity;
import com.docker.active.ui.manager.ActiveManagerListActivity;
import com.docker.active.ui.manager.ActivePersionDetailActivity;
import com.docker.active.ui.manager.ActivePersionManagerListActivity;
import com.docker.active.ui.manager.ActiveRegistListActivity;
import com.docker.active.ui.manager.ActiveSearchActivity;
import com.docker.active.ui.manager.ActiveVerificationActivity;
import com.docker.active.ui.publish.ActiveBannerPreviewActivity;
import com.docker.active.ui.publish.ActiveContentEditActivity;
import com.docker.active.ui.publish.ActivePublishFragment;
import com.docker.active.ui.publish.ActiveTypeSelectActivity;
import com.docker.core.di.component.BaseActComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class ActiveUIModule {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract PointSortActivity contributePointSortActivityInjector();


    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveContainerFragment contributeActiveContainerFragmentInjector();


    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveIndexActivity contributeActiveIndexActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveDetailFragment contributeActiveDetailFragmentInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveSuccActivity contributeActiveSuccActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveResultActivity contributeActiveResultActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveManagerListActivity contributeActiveManagerActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveRegistListActivity contributeActiveRegistListActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveManagerDetailActivity contributeActiveManagerDetailActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveVerificationActivity contributeActiveVerificationActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActivePersionManagerListActivity contributeActivePersionManagerListActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActivePersionDetailActivity contributeActivePersionDetailActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActivePublishFragment contributeActivePublishFragmentInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveBannerPreviewActivity contributeActiveBannerPreviewActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveContentEditActivity contributeActiveContentEditActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveTypeSelectActivity contributeActiveTypeSelectActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveDetailActivity contributeActiveDetailActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveSearchActivity contributeActiveSearchActivityInjector();

    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ActiveListFragment contributeActiveListFragmentInjector();
}

