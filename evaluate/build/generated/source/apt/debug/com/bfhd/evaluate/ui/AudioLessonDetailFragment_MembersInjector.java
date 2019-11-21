// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.bfhd.evaluate.ui;

import android.arch.lifecycle.ViewModelProvider;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.core.util.AppExecutors;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AudioLessonDetailFragment_MembersInjector
    implements MembersInjector<AudioLessonDetailFragment> {
  private final Provider<ViewModelProvider.Factory> factoryProvider;

  private final Provider<AppExecutors> appExecutorsProvider;

  public AudioLessonDetailFragment_MembersInjector(
      Provider<ViewModelProvider.Factory> factoryProvider,
      Provider<AppExecutors> appExecutorsProvider) {
    assert factoryProvider != null;
    this.factoryProvider = factoryProvider;
    assert appExecutorsProvider != null;
    this.appExecutorsProvider = appExecutorsProvider;
  }

  public static MembersInjector<AudioLessonDetailFragment> create(
      Provider<ViewModelProvider.Factory> factoryProvider,
      Provider<AppExecutors> appExecutorsProvider) {
    return new AudioLessonDetailFragment_MembersInjector(factoryProvider, appExecutorsProvider);
  }

  @Override
  public void injectMembers(AudioLessonDetailFragment instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    ((NitCommonFragment) instance).factory = factoryProvider.get();
    instance.appExecutors = appExecutorsProvider.get();
  }

  public static void injectAppExecutors(
      AudioLessonDetailFragment instance, Provider<AppExecutors> appExecutorsProvider) {
    instance.appExecutors = appExecutorsProvider.get();
  }
}
