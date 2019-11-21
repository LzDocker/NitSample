package com.bfhd.evaluate.di;

import android.support.v4.app.Fragment;
import com.bfhd.evaluate.ui.AudioLessonGenDuFragment;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = UIModule_AudioLessonGenDuFragment.AudioLessonGenDuFragmentSubcomponent.class
)
public abstract class UIModule_AudioLessonGenDuFragment {
  private UIModule_AudioLessonGenDuFragment() {}

  @Binds
  @IntoMap
  @FragmentKey(AudioLessonGenDuFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> bindAndroidInjectorFactory(
      AudioLessonGenDuFragmentSubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonGenDuFragmentSubcomponent
      extends AndroidInjector<AudioLessonGenDuFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonGenDuFragment> {}
  }
}
