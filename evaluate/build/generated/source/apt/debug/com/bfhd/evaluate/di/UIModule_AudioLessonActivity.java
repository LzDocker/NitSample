package com.bfhd.evaluate.di;

import android.app.Activity;
import com.bfhd.evaluate.ui.AudioLessonActivity;
import com.docker.core.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = UIModule_AudioLessonActivity.AudioLessonActivitySubcomponent.class)
public abstract class UIModule_AudioLessonActivity {
  private UIModule_AudioLessonActivity() {}

  @Binds
  @IntoMap
  @ActivityKey(AudioLessonActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      AudioLessonActivitySubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface AudioLessonActivitySubcomponent extends AndroidInjector<AudioLessonActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AudioLessonActivity> {}
  }
}
