package com.bfhd.evaluate.di;

import android.support.v4.app.Fragment;
import com.bfhd.evaluate.ui.AudioLessonFragment;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = UIModule_AudioLessonFragment.AudioLessonFragmentSubcomponent.class)
public abstract class UIModule_AudioLessonFragment {
  private UIModule_AudioLessonFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AudioLessonFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AudioLessonFragmentSubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonFragmentSubcomponent extends AndroidInjector<AudioLessonFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonFragment> {}
  }
}
