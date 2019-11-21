package com.bfhd.evaluate.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bfhd.evaluate.view.PlayPauseView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityAudioLessonDetailBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout audioDetailFrame;

  @NonNull
  public final TextView studyReadBeisu;

  @NonNull
  public final LinearLayout studyReadBliner;

  @NonNull
  public final TextView studyReadGendu;

  @NonNull
  public final ImageView studyReadHide;

  @NonNull
  public final TextView studyReadKcsp;

  @NonNull
  public final ImageView studyReadNext;

  @NonNull
  public final LinearLayout studyReadNextLin;

  @NonNull
  public final ProgressBar studyReadProgress;

  @NonNull
  public final TextView studyReadQwkh;

  @NonNull
  public final TextView studyReadQwzs;

  @NonNull
  public final PlayPauseView studyReadResume;

  @NonNull
  public final ImageView studyReadShang;

  @NonNull
  public final LinearLayout studyReadShangLin;

  @NonNull
  public final TextView studyReadXunhuan;

  protected ActivityAudioLessonDetailBinding(Object _bindingComponent, View _root,
      int _localFieldCount, FrameLayout audioDetailFrame, TextView studyReadBeisu,
      LinearLayout studyReadBliner, TextView studyReadGendu, ImageView studyReadHide,
      TextView studyReadKcsp, ImageView studyReadNext, LinearLayout studyReadNextLin,
      ProgressBar studyReadProgress, TextView studyReadQwkh, TextView studyReadQwzs,
      PlayPauseView studyReadResume, ImageView studyReadShang, LinearLayout studyReadShangLin,
      TextView studyReadXunhuan) {
    super(_bindingComponent, _root, _localFieldCount);
    this.audioDetailFrame = audioDetailFrame;
    this.studyReadBeisu = studyReadBeisu;
    this.studyReadBliner = studyReadBliner;
    this.studyReadGendu = studyReadGendu;
    this.studyReadHide = studyReadHide;
    this.studyReadKcsp = studyReadKcsp;
    this.studyReadNext = studyReadNext;
    this.studyReadNextLin = studyReadNextLin;
    this.studyReadProgress = studyReadProgress;
    this.studyReadQwkh = studyReadQwkh;
    this.studyReadQwzs = studyReadQwzs;
    this.studyReadResume = studyReadResume;
    this.studyReadShang = studyReadShang;
    this.studyReadShangLin = studyReadShangLin;
    this.studyReadXunhuan = studyReadXunhuan;
  }

  @NonNull
  public static ActivityAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_audio_lesson_detail, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityAudioLessonDetailBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_audio_lesson_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_audio_lesson_detail, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityAudioLessonDetailBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_audio_lesson_detail, null, false, component);
  }

  public static ActivityAudioLessonDetailBinding bind(@NonNull View view) {
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
  public static ActivityAudioLessonDetailBinding bind(@NonNull View view,
      @Nullable Object component) {
    return (ActivityAudioLessonDetailBinding)bind(component, view, com.bfhd.evaluate.R.layout.activity_audio_lesson_detail);
  }
}
