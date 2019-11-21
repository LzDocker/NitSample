package com.bfhd.evaluate.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityRxdemoBinding extends ViewDataBinding {
  @NonNull
  public final Button rxdemoConcat;

  @NonNull
  public final TextView rxdemoData;

  @NonNull
  public final Button rxdemoMerge;

  protected ActivityRxdemoBinding(Object _bindingComponent, View _root, int _localFieldCount,
      Button rxdemoConcat, TextView rxdemoData, Button rxdemoMerge) {
    super(_bindingComponent, _root, _localFieldCount);
    this.rxdemoConcat = rxdemoConcat;
    this.rxdemoData = rxdemoData;
    this.rxdemoMerge = rxdemoMerge;
  }

  @NonNull
  public static ActivityRxdemoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_rxdemo, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityRxdemoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityRxdemoBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_rxdemo, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityRxdemoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_rxdemo, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityRxdemoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityRxdemoBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.activity_rxdemo, null, false, component);
  }

  public static ActivityRxdemoBinding bind(@NonNull View view) {
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
  public static ActivityRxdemoBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityRxdemoBinding)bind(component, view, com.bfhd.evaluate.R.layout.activity_rxdemo);
  }
}
