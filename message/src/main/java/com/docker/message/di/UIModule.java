package com.docker.message.di;


import com.docker.core.di.component.BaseActComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.message.ui.MessageFragment;
import com.docker.message.ui.index.MessageActivity;
import com.docker.message.ui.index.MessageListActivity;
import com.docker.message.ui.index.MessageSampleActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {BaseActComponent.class})
public abstract class UIModule {

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MessageSampleActivity contributeMessageSampleActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MessageFragment contributeMessageFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MessageActivity contributeMessageActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
    abstract MessageListActivity contributeMessageListActivityInjector();


}

