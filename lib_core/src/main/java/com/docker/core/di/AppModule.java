package com.docker.core.di;


import com.docker.core.base.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private BaseApp application;

    public AppModule(BaseApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public BaseApp provideApplication() {
        return application;
    }
}