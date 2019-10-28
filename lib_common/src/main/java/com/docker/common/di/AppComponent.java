//package com.docker.common.di;
//
//import android.app.ActivityManager;
//import android.content.SharedPreferences;
//
//import com.docker.core.di.httpmodule.GlobalConfigModule;
//import com.google.gson.Gson;
//
//import java.io.File;
//
//import javax.inject.Singleton;
//
//import dagger.Component;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//
//@Singleton
//@Component(modules = GlobalConfigModule.class)
//public interface AppComponent {
//
//    public Gson getGson();
//
//    public File getCacheFile();
//
//    public OkHttpClient getOkHttpClient();
//
//    public ActivityManager getActivityManager();
//
//    public OkHttpClient.Builder getOkHttpClientBuilder();
//
//    public Retrofit getRetrofit();
//
//    public SharedPreferences getSharedPreferences();
//
//    public Retrofit.Builder getRetrofitBuilder();
//
//}
