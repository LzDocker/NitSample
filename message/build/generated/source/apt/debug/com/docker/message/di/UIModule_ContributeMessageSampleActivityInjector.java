package com.docker.message.di;

import android.app.Activity;
import com.docker.core.di.scope.ActivityScope;
import com.docker.message.ui.index.MessageSampleActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      UIModule_ContributeMessageSampleActivityInjector.MessageSampleActivitySubcomponent.class
)
public abstract class UIModule_ContributeMessageSampleActivityInjector {
  private UIModule_ContributeMessageSampleActivityInjector() {}

  @Binds
  @IntoMap
  @ActivityKey(MessageSampleActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindAndroidInjectorFactory(
      MessageSampleActivitySubcomponent.Builder builder);

  @Subcomponent
  @ActivityScope
  public interface MessageSampleActivitySubcomponent
      extends AndroidInjector<MessageSampleActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MessageSampleActivity> {}
  }
}
