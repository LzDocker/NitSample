package com.docker.apps.active.di;

import com.docker.apps.active.api.ActiveService;
import com.docker.apps.point.api.PointService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {ActiveUIModule.class, ActiveVmModule.class})
public class ActiveModule {
    @Singleton
    @Provides
    ActiveService provideActiveService(Retrofit retrofit) {
        return retrofit.create(ActiveService.class);
    }

}
