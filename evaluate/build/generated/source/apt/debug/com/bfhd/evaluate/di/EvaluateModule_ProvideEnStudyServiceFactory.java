// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.bfhd.evaluate.di;

import com.bfhd.evaluate.api.EnStudyService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class EvaluateModule_ProvideEnStudyServiceFactory implements Factory<EnStudyService> {
  private final EvaluateModule module;

  private final Provider<Retrofit> retrofitProvider;

  public EvaluateModule_ProvideEnStudyServiceFactory(
      EvaluateModule module, Provider<Retrofit> retrofitProvider) {
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public EnStudyService get() {
    return Preconditions.checkNotNull(
        module.provideEnStudyService(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<EnStudyService> create(
      EvaluateModule module, Provider<Retrofit> retrofitProvider) {
    return new EvaluateModule_ProvideEnStudyServiceFactory(module, retrofitProvider);
  }

  /** Proxies {@link EvaluateModule#provideEnStudyService(Retrofit)}. */
  public static EnStudyService proxyProvideEnStudyService(
      EvaluateModule instance, Retrofit retrofit) {
    return instance.provideEnStudyService(retrofit);
  }
}
