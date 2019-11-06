package com.docker.message.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class MessageSampleActivityBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout messageFrame;

  protected MessageSampleActivityBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout messageFrame) {
    super(_bindingComponent, _root, _localFieldCount);
    this.messageFrame = messageFrame;
  }

  @NonNull
  public static MessageSampleActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.message_sample_activity, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static MessageSampleActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<MessageSampleActivityBinding>inflateInternal(inflater, com.docker.message.R.layout.message_sample_activity, root, attachToRoot, component);
  }

  @NonNull
  public static MessageSampleActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.message_sample_activity, null, false, component)
   */
  @NonNull
  @Deprecated
  public static MessageSampleActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<MessageSampleActivityBinding>inflateInternal(inflater, com.docker.message.R.layout.message_sample_activity, null, false, component);
  }

  public static MessageSampleActivityBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static MessageSampleActivityBinding bind(@NonNull View view, @Nullable Object component) {
    return (MessageSampleActivityBinding)bind(component, view, com.docker.message.R.layout.message_sample_activity);
  }
}
