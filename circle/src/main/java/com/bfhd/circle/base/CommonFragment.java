package com.bfhd.circle.base;

import android.content.res.Configuration;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
public abstract class CommonFragment<VM extends HivsBaseViewModel, VB extends ViewDataBinding> extends HivsBaseFragment<VM, VB> implements ImmersionOwner {

    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    @Override
    public void onLazyBeforeView() {
    }

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    @Override
    public void onLazyAfterView() {
    }

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    @Override
    public void onVisible() {
    }

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    @Override
    public void onInvisible() {

    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return true;
    }


    /*
     * 初始化fragment状态栏
     * */
    public void superInitImmersionBar(int barColor, @Nullable String navColor) {
        if (TextUtils.isEmpty(navColor)) {
            navColor = "#ffffff";
        }
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(barColor)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor(navColor)
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    /*
     * fragment 嵌套层次太多 刷新只能给父控件来调度了。。
     * */
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
    }

    /*
     * fragment 嵌套层次太多 刷新只能给父控件来调度了。。
     * */
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
    }


    public void setData(Object obj) {

    }
}
