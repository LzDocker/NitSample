package com.bfhd.evaluate.di;

import android.app.Activity;
import com.bfhd.evaluate.ui.AudioLessonDetailActivity;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = UIModule_AudioLessonDetailActivity.AudioLessonDetailActivitySubcomponent.class
)
public abstract class UIModule_AudioLessonDetailActivity {
  private UIModule_AudioLessonDetailActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(AudioLessonDetailActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      AudioLessonDetailActivitySubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonDetailActivitySubcomponent
      extends AndroidInjector<AudioLessonDetailActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonDetailActivity> {}
  }
}
