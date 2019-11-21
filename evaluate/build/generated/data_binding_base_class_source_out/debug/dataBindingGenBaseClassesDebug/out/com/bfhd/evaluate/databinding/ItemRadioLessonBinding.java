package com.bfhd.evaluate.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bfhd.evaluate.vo.RadioLessonVo;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemRadioLessonBinding extends ViewDataBinding {
  @NonNull
  public final ImageView itemRadioI1;

  @NonNull
  public final ImageView itemRadioLw;

  @NonNull
  public final TextView itemRadioName;

  @NonNull
  public final TextView itemRadioNum;

  @NonNull
  public final TextView itemRadioStatus;

  @Bindable
  protected RadioLessonVo mItem;

  protected ItemRadioLessonBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView itemRadioI1, ImageView itemRadioLw, TextView itemRadioName, TextView itemRadioNum,
      TextView itemRadioStatus) {
    super(_bindingComponent, _root, _localFieldCount);
    this.itemRadioI1 = itemRadioI1;
    this.itemRadioLw = itemRadioLw;
    this.itemRadioName = itemRadioName;
    this.itemRadioNum = itemRadioNum;
    this.itemRadioStatus = itemRadioStatus;
  }

  public abstract void setItem(@Nullable RadioLessonVo item);

  @Nullable
  public RadioLessonVo getItem() {
    return mItem;
  }

  @NonNull
  public static ItemRadioLessonBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_radio_lesson, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemRadioLessonBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemRadioLessonBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_radio_lesson, root, attachToRoot, component);
  }

  @NonNull
  public static ItemRadioLessonBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_radio_lesson, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemRadioLessonBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemRadioLessonBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_radio_lesson, null, false, component);
  }

  public static ItemRadioLessonBinding bind(@NonNull View view) {
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
  public static ItemRadioLessonBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemRadioLessonBinding)bind(component, view, com.bfhd.evaluate.R.layout.item_radio_lesson);
  }
}
