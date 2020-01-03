package com.docker.apps.intvite.di;

import com.docker.apps.intvite.api.InviteService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {InviteUIModule.class, InviteVmModule.class})
public class InviteModule {
    @Singleton
    @Provides
    InviteService provideInviteService(Retrofit retrofit) {
        return retrofit.create(InviteService.class);
    }

}
