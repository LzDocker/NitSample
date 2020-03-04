package com.docker.core.base;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.docker.core.R;
import com.docker.core.di.Injectable;
import com.docker.core.widget.ToolBar;
import com.gyf.immersionbar.ImmersionBar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity<VM extends BaseVm, VB extends ViewDataBinding> extends AppCompatActivity
        implements HasFragmentInjector, HasSupportFragmentInjector, Injectable {
    protected VB mBinding;
    public VM mViewModel;
    protected ToolBar mToolbar;
    private int mThemeColor = -1;

    protected abstract int getLayoutId();

    public abstract VM getmViewModel();

    private InputMethodManager mInputMethodManager;

    public boolean isOverrideContentView = false;

    public boolean isFitWindow = true;

    @Inject
    DispatchingAndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> frameworkFragmentInjector;

    @CallSuper
    @MainThread
    protected void inject() {
//        AndroidInjection.inject(this);
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
        if (isOverrideContentView) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        } else {
            if (this.mThemeColor == -1) {
                TypedValue typedValue = new TypedValue();
                this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                this.mThemeColor = typedValue.data;
            }
            setContentView(R.layout.activity_base);
            LinearLayout rootView = (LinearLayout) findViewById(R.id.root_layout);
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), rootView, false);
            initToolBar(rootView);
            if (this.mBinding != null) {
                rootView.addView(this.mBinding.getRoot(), new ViewGroup.LayoutParams(-1, -1));
            } else {
                rootView.addView(this.getLayoutInflater().inflate(this.getLayoutId(), (ViewGroup) null));
            }
        }
        if (isFitWindow) {
            initImmersionBar();
        }
        mViewModel = getmViewModel();
        getLifecycle().addObserver(mViewModel);
    }


    public void initImmersionBar() {
        if (isOverrideContentView) {
            ImmersionBar.with(this).statusBarDarkFont(true).navigationBarDarkIcon(true).navigationBarColor("#ffffff").init();
        } else {
            ImmersionBar.with(this).fitsSystemWindows(true).navigationBarDarkIcon(true).navigationBarColor("#ffffff").statusBarDarkFont(true).titleBar(getToolBar()).statusBarColor(R.color.colorPrimary).init();
        }
    }

    /*
     *
     * 未覆盖父布局的默认包含一个toolbar
     * */
    protected void initToolBar(ViewGroup rootView) {
        View toolBar = this.getToolBar();
        if (toolBar != null) {
            rootView.addView(toolBar);
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            if (toolbar != null) {
                this.setSupportActionBar(toolbar);
            }
            this.mToolbar = new ToolBar(toolbar, this.getSupportActionBar(), this.mThemeColor);
            this.mToolbar.setTitle(String.valueOf(this.getTitle()));
            toolbar.setNavigationOnClickListener((v) -> {
                this.finish();
            });
        }

    }

    protected View getToolBar() {
        return this.getLayoutInflater().inflate(R.layout.toolbar, (ViewGroup) null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
}
