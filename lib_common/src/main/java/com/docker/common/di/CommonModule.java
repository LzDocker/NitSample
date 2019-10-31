package com.docker.common.di;

import com.docker.common.api.OpenService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class CommonModule {

    @Singleton
    @Provides
    OpenService provideOpenService(Retrofit retrofit) {
        return retrofit.create(OpenService.class);
    }

}
