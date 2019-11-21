package com.bfhd.evaluate.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentLessonRecyclerBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView fragmentLessonRecycler;

  protected FragmentLessonRecyclerBinding(Object _bindingComponent, View _root,
      int _localFieldCount, RecyclerView fragmentLessonRecycler) {
    super(_bindingComponent, _root, _localFieldCount);
    this.fragmentLessonRecycler = fragmentLessonRecycler;
  }

  @NonNull
  public static FragmentLessonRecyclerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_recycler, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentLessonRecyclerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentLessonRecyclerBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.fragment_lesson_recycler, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentLessonRecyclerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_recycler, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentLessonRecyclerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentLessonRecyclerBinding>inflateInternal(inflater, com.bfhd.evaluate.R.layout.fragment_lesson_recycler, null, false, component);
  }

  public static FragmentLessonRecyclerBinding bind(@NonNull View view) {
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
  public static FragmentLessonRecyclerBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentLessonRecyclerBinding)bind(component, view, com.bfhd.evaluate.R.layout.fragment_lesson_recycler);
  }
}
