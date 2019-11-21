package com.bfhd.evaluate.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bfhd.evaluate.vo.LessonDetailVo;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemAudioLessonDetailBinding extends ViewDataBinding {
  @NonNull
  public final TextView studyReadCh;

  @NonNull
  public final TextView studyReadEn;

  @NonNull
  public final TextView studyReadShow;

  @Bindable
  protected LessonDetailVo.SortContentBean mItem;

  protected ItemAudioLessonDetailBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView studyReadCh, TextView studyReadEn, TextView studyReadShow) {
    super(_bindingComponent, _root, _localFieldCount);
    this.studyReadCh = studyReadCh;
    this.studyReadEn = studyReadEn;
    this.studyReadShow = studyReadShow;
  }

  public abstract void setItem(@Nullable LessonDetailVo.SortContentBean item);

  @Nullable
  public LessonDetailVo.SortContentBean getItem() {
    return mItem;
  }

  @NonNull
  public static ItemAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_audio_lesson_detail, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemAudioLessonDetailBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_audio_lesson_detail, root, attachToRoot, component);
  }

  @NonNull
  public static ItemAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_audio_lesson_detail, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemAudioLessonDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemAudioLessonDetailBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_audio_lesson_detail, null, false, component);
  }

  public static ItemAudioLessonDetailBinding bind(@NonNull View view) {
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
  public static ItemAudioLessonDetailBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemAudioLessonDetailBinding)bind(component, view, com.bfhd.evaluate.R.layout.item_audio_lesson_detail);
  }
}
