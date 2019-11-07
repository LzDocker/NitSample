// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.docker.message.vm;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;

public final class SampleListViewModel_Factory implements Factory<SampleListViewModel> {
  private final MembersInjector<SampleListViewModel> sampleListViewModelMembersInjector;

  public SampleListViewModel_Factory(
      MembersInjector<SampleListViewModel> sampleListViewModelMembersInjector) {
    assert sampleListViewModelMembersInjector != null;
    this.sampleListViewModelMembersInjector = sampleListViewModelMembersInjector;
  }

  @Override
  public SampleListViewModel get() {
    return MembersInjectors.injectMembers(
        sampleListViewModelMembersInjector, new SampleListViewModel());
  }

  public static Factory<SampleListViewModel> create(
      MembersInjector<SampleListViewModel> sampleListViewModelMembersInjector) {
    return new SampleListViewModel_Factory(sampleListViewModelMembersInjector);
  }
}