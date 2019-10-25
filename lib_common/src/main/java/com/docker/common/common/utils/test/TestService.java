package com.docker.common.common.utils.test;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.docker.common.api.OpenService;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.android.DaggerService;
import retrofit2.Retrofit;

public class TestService extends DaggerService {

    @Inject
    Retrofit retrofit;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        HashMap<String, String> param = new HashMap<>();
        param.put("mtime", "1");

//        retrofit.create(OpenService.class).fetchRouter(param)

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
