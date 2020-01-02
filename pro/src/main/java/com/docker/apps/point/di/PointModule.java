package com.docker.apps.point.di;
import com.docker.apps.point.api.PointService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {PointUIModule.class, PointVmModule.class})
public class PointModule {
    @Singleton
    @Provides
    PointService providePointService(Retrofit retrofit) {
        return retrofit.create(PointService.class);
    }

}
