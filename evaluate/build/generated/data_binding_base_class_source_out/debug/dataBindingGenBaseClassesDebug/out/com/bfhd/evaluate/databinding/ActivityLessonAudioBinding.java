package com.bfhd.evaluate.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.flyco.tablayout.SlidingTabLayout;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityLessonAudioBinding extends ViewDataBinding {
  @NonNull
  public final ImageView lessonAudioBack;

  @NonNull
  public final TextView lessonAudioName;

  @NonNull
  public final RelativeLayout lessonAudioRelative;

  @NonNull
  public final ImageView lessonAudioSrc;

  @NonNull
  public final SlidingTabLayout lessonAudioTab;

  @NonNull
  public final TextView lessonAudioTitle;

  @NonNull
  public final ViewPager lessonAudioViewpager;

  @NonNull
  public final TextView lessonAudioXz;

  @NonNull
  public final TextView lessonAudioZj;

  protected ActivityLessonAudioBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView lessonAudioBack, TextView lessonAudioName, RelativeLayout lessonAudioRelative,
      ImageView lessonAudioSrc, SlidingTabLayout lessonAudioTab, TextView lessonAudioTitle,
      ViewPager lessonAudioViewpager, TextView lessonAudioXz, TextView lessonAudioZj) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lessonAudioBack = lessonAudioBack;
    this.lessonAudioName = lessonAudioName;
    this.lessonAudioRelative = lessonAudioRelative;
    this.lessonAudioSrc = lessonAudioSrc;
    this.lessonAudioTab = lessonAudioTab;
    this.lessonAudioTitle = lessonAudioTitle;
    this.lessonAudioViewpager = lessonAudioViewpager;
    this.lessonAudioXz = lessonAudioXz;
    this.lessonAudioZj = lessonAudioZj;
  }

  @NonNull
  public static ActivityLessonAudioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_lesson_audio, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLessonAudioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityLessonAudioBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_lesson_audio, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityLessonAudioBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_lesson_audio, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityLessonAudioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityLessonAudioBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_lesson_audio, null, false, component);
  }

  public static ActivityLessonAudioBinding bind(@NonNull View view) {
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
  public static ActivityLessonAudioBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityLessonAudioBinding)bind(component, view, com.bfhd.evaluate.R.layout.activity_lesson_audio);
  }
}
