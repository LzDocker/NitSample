package com.bfhd.evaluate.di;

import android.support.v4.app.Fragment;
import com.bfhd.evaluate.ui.AudioLessonDetailFragment;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = UIModule_AudioLessonDetailFragment.AudioLessonDetailFragmentSubcomponent.class
)
public abstract class UIModule_AudioLessonDetailFragment {
  private UIModule_AudioLessonDetailFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AudioLessonDetailFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AudioLessonDetailFragmentSubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonDetailFragmentSubcomponent
      extends AndroidInjector<AudioLessonDetailFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonDetailFragment> {}
  }
}
