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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bfhd.evaluate.view.WaveLineView;
import com.bfhd.evaluate.vo.LessonDetailVo;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemGenduCompareBinding extends ViewDataBinding {
  @NonNull
  public final RelativeLayout genduItemRelative;

  @NonNull
  public final TextView readCompareCancel;

  @NonNull
  public final TextView readCompareCh;

  @NonNull
  public final TextView readCompareEn;

  @NonNull
  public final ImageView readComparePause;

  @NonNull
  public final ImageView readComparePlay;

  @NonNull
  public final ImageView readComparePlayback;

  @NonNull
  public final ImageView readCompareRecording;

  @NonNull
  public final RelativeLayout readCompareRelative;

  @NonNull
  public final TextView readCompareScore;

  @NonNull
  public final WaveLineView readCompareWave;

  @Bindable
  protected LessonDetailVo.SortContentBean mItem;

  protected ItemGenduCompareBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RelativeLayout genduItemRelative, TextView readCompareCancel, TextView readCompareCh,
      TextView readCompareEn, ImageView readComparePause, ImageView readComparePlay,
      ImageView readComparePlayback, ImageView readCompareRecording,
      RelativeLayout readCompareRelative, TextView readCompareScore, WaveLineView readCompareWave) {
    super(_bindingComponent, _root, _localFieldCount);
    this.genduItemRelative = genduItemRelative;
    this.readCompareCancel = readCompareCancel;
    this.readCompareCh = readCompareCh;
    this.readCompareEn = readCompareEn;
    this.readComparePause = readComparePause;
    this.readComparePlay = readComparePlay;
    this.readComparePlayback = readComparePlayback;
    this.readCompareRecording = readCompareRecording;
    this.readCompareRelative = readCompareRelative;
    this.readCompareScore = readCompareScore;
    this.readCompareWave = readCompareWave;
  }

  public abstract void setItem(@Nullable LessonDetailVo.SortContentBean item);

  @Nullable
  public LessonDetailVo.SortContentBean getItem() {
    return mItem;
  }

  @NonNull
  public static ItemGenduCompareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_gendu_compare, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemGenduCompareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemGenduCompareBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_gendu_compare, root, attachToRoot, component);
  }

  @NonNull
  public static ItemGenduCompareBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_gendu_compare, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemGenduCompareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemGenduCompareBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.item_gendu_compare, null, false, component);
  }

  public static ItemGenduCompareBinding bind(@NonNull View view) {
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
  public static ItemGenduCompareBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemGenduCompareBinding)bind(component, view, com.bfhd.evaluate.R.layout.item_gendu_compare);
  }
}
