package com.docker.message.di;


import com.docker.message.api.MessageService;
import com.docker.module_im.di.ImModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {UIModule.class, VmModule.class})
public class MessageModule {
    @Singleton
    @Provides
    MessageService provideMessageService(Retrofit retrofit) {
        return retrofit.create(MessageService.class);
    }

}
