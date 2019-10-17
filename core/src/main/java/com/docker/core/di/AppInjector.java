package com.docker.core.di;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.docker.core.base.BaseApp;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class AppInjector {
    public static void init(BaseApp mApplication) {
        mApplication
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }
                    @Override
                    public void onActivityStarted(Activity activity) {

                    }
                    @Override
                    public void onActivityResumed(Activity activity) {

                    }
                    @Override
                    public void onActivityPaused(Activity activity) {

                    }
                    @Override
                    public void onActivityStopped(Activity activity) {

                    }
                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }
                    @Override
                    public void onActivityDestroyed(Activity activity) {
                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector || activity instanceof Injectable) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                    super.onFragmentAttached(fm, f, context);
                                }

                                @Override
                                public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                                    super.onFragmentDestroyed(fm, f);
                                }
                            }, true);
        }
    }
}
