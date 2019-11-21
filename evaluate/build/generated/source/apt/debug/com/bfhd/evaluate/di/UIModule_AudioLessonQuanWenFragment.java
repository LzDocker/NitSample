package com.bfhd.evaluate.di;

import android.support.v4.app.Fragment;
import com.bfhd.evaluate.ui.AudioLessonQuanWenFragment;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = UIModule_AudioLessonQuanWenFragment.AudioLessonQuanWenFragmentSubcomponent.class
)
public abstract class UIModule_AudioLessonQuanWenFragment {
  private UIModule_AudioLessonQuanWenFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AudioLessonQuanWenFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AudioLessonQuanWenFragmentSubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonQuanWenFragmentSubcomponent
      extends AndroidInjector<AudioLessonQuanWenFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonQuanWenFragment> {}
  }
}
