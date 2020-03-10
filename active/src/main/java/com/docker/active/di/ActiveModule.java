package com.docker.active.di;
import com.docker.active.api.ActiveService;

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
