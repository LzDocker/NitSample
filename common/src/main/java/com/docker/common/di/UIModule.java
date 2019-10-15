package com.docker.common.di;


import com.docker.common.common.ui.AliVideoPlayerActivity;
import com.docker.common.common.ui.AliVideoPlayerFragment;
import com.docker.common.common.ui.ViewDocumentActivity;
import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract ViewDocumentActivity viewDocumentActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract AliVideoPlayerActivity aliVideoPlayerActivity();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract AliVideoPlayerFragment aliVideoPlayerFragment();
}
