package com.docker.core.di.module.cachemodule;

import android.arch.persistence.room.Room;

import com.docker.core.base.BaseApp;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangxindang on 2018/11/21.
 */

@Module
public class CacheModule {

    @Inject
    public CacheModule(BaseApp application) {
    }

    @Provides
    @Singleton
    CacheDatabase provideCacheDatabase(BaseApp application) {
        return Room.databaseBuilder(application, CacheDatabase.class, "Cache.db").build();
    }

}
