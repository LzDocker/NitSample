package com.bfhd.account.di;

import com.bfhd.account.api.AccountService;
import com.bfhd.circle.api.CircleService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Singleton
    @Provides
    AccountService provideCircleService(Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }
    
}
