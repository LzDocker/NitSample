package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.utils.AccountConstant;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.cirlev2.widget.popmen.SuperPopmenu;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.boottomBar.Bottombar;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.ui.mine.MineProcess;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.OptimizationModel;
import com.docker.nitsample.vm.SampleListViewModel;
import com.docker.videobasic.ui.VideoListFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_ACTIVE;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_NEWS;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_QREQUESTION;
import static com.docker.common.common.router.AppRouter.HOME;

@Route(path = HOME)
public class MainTygsActivity extends NitCommonActivity<MainViewModel, ActivityMainBinding> {

    @Inject
    AppVersionManager versionManager;
    private boolean isExit;
    @Inject
    List<Fragment> fragments;
    private PopmenuWj mpopMenu;
    private String type;
    private Disposable disposable;

    /*
     个人中心的vm
    * */
    private NitCommonListVm mineVm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initView() {
        mToolbar.hide();
        initMainTab();
        mBinding.ivCenter.setOnClickListener(v -> {
            if (CacheUtils.getUser() == null) {
                ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                return;
            }
            showSuperPop();

//            showPopMenu();
//            ARouter.getInstance().build(AppRouter.HOME_edit_index).navigation();

//            Intent intent = new Intent(MainActivity.this, VideoListActivity.class);
//            startActivity(intent);

//            Intent intent = new Intent(MainActivity.this, VideoListActivity.class);
//            startActivity(intent);

        });
    }

    @Override
    public void initObserver() {
        this.getLifecycle().addObserver(versionManager.Bind(this,
                this, mViewModel.checkUpData(),
                versionManager.TYPE_DIALOG, "com.bfhd.tygs"));

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("Badger")) {
                if (mBinding != null && mBinding.tlHomeTab != null) {
                    int num = (int) rxEvent.getR();
                    if (num > 0) {
                        mBinding.tlHomeTab.showDot(3);
                    } else {
                        mBinding.tlHomeTab.hideMsg(3);
                    }
                }
            }
            if (rxEvent.getT().equals("change")) {
                int num = (int) rxEvent.getR();
                mBinding.tlHomeTab.setCurrentTab(num);
                if (num > 2) {
                    num = num - 1;
                }
                mBinding.viewpager.setCurrentItem(num, false);
            }
        });
    }

    @Override
    public void initRouter() {

    }

    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;
        switch (flag) {
            case 1:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return OptimizationModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
//                        AppBannerHeaderCardVo appBannerHeaderCardVo = new AppBannerHeaderCardVo(0, 0);
//                        NitBaseProviderCard.providerCard(commonListVm, appBannerHeaderCardVo, nitCommonFragment);
                    }
                };
                break;
            case 2:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return SampleListViewModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

                    }
                };
                break;
            case 3:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return null;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        mineVm = commonListVm;
                        MineProcess.processMineFrame(mineVm, nitCommonFragment);

                        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
                            if (rxEvent.getT().equals("login_state_change")) {
                                MineProcess.processMineHeaderFrame(commonListVm, nitCommonFragment);
                            }
                        });
                    }
                };
                break;
        }
        return nitDelegetCommand;
    }

    private void initMainTab() {
        mBinding.tlHomeTab.setTabData(new Bottombar().initBotombar());
        mBinding.tlHomeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position > 2) {
                    position = position - 1;
                }
                if (position > 1 && CacheUtils.getUser() == null) {
                    return;
                }
                mBinding.viewpager.setCurrentItem(position, false);
                if (position <= 1) {
                    ImmersionBar.with(MainTygsActivity.this)
                            .statusBarColor("#ffffff")
                            .fullScreen(false)
                            .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                            .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                            .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                            .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                            .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                            .init();
                } else if (position == 2) {
                    ImmersionBar.with(MainTygsActivity.this)
                            .fullScreen(false)
                            .statusBarColor(R.color.colorPrimaryDark)
                            .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                            .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                            .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                            .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                            .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色

                            .init();
                } else {
                    ImmersionBar.with(MainTygsActivity.this)
                            .navigationBarColor("#FFFFFF")
                            .fullScreen(false).statusBarColor(R.color.colorPrimaryDark)
                            .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                            .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                            .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                            .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                            .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                            .init();
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mBinding.tlHomeTab.setCurrentTab(0);
        mBinding.viewpager.setOffscreenPageLimit(4);
        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments));
    }


    private void showPopMenu() {

        if (null == mpopMenu) {
            mpopMenu = new PopmenuWj(this);
            mpopMenu.init(mBinding.getRoot());
        }
        mpopMenu.showMoreWindow(mBinding.relCoutainer);
        mpopMenu.setReplyCommand(o -> {
            type = (String) o;
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("title", "发布")
                    .withString("location", "loc")
                    .withString("type", type)
                    .navigation();
//            processLocation();
//            int type = (int) o;
//            switch (type) {
//                case 1:
//                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_ACTIVE, 1);
//                    break;
//                case 2:
//                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_NEWS, 1);
//                    break;
//                case 3:
//                    processCirclePublish(CirclePublishActivity.PUBLISH_TYPE_QREQUESTION, 1);
//                    break;
//            }
        });
    }


    SuperPopmenu mPublishMenu;

    private void showSuperPop() {
        // public String reg_type; // 注册类型 1 个人 2企业
        HashMap<String, String> hashMap = new HashMap<>();
        if (CacheUtils.getUser() == null) {
            //
        } else {
            hashMap.put("isShowBot", CacheUtils.getUser().reg_type);  // no
        }
        if (mPublishMenu == null) {
            mPublishMenu = new SuperPopmenu(this);
        }
        mPublishMenu.init(mBinding.getRoot());
        processPro(mPublishMenu.getAdapter());
        mPublishMenu.setReplyCommandParam((ReplyCommandParam) o -> {
            switch (((AppVo) o).id) {
                case "0":
                    //.withSerializable("mStartParam", staCirParam)
                    ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                            .withInt("editType", 1)
                            .withInt("type", PUBLISH_TYPE_ACTIVE)
                            .withSerializable("extens", hashMap)
                            .navigation();


                    break;
                case "1":
                    ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                            .withInt("editType", 1)
                            .withString("title", "活动")
                            .withSerializable("extens", hashMap)
                            .withString("pubRoterPath", AppRouter.ACTIVE_PUBLISH)
                            .navigation();
                    break;
                case "2":
                    ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                            .withInt("editType", 1)
                            .withSerializable("extens", hashMap)
                            .withInt("type", PUBLISH_TYPE_NEWS)
                            .navigation();
                    break;
                case "3":
                    ARouter.getInstance().build(AppRouter.ACCOUNT_point).withString("type", "point").navigation();
                    break;

                case "4": // 客服h5
                    ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", AccountConstant.CustomerService).withString("title", "客服").navigation();
                    break;
                case "5":// 推广公社
                    ARouter.getInstance().build(AppRouter.INVITE_INDEX).navigation();
                    break;
                case "6":// 加入公社
                    ARouter.getInstance().build(AppRouter.CIRCLE_INDEX).navigation();
                    break;
                case "7": // 成为股东
                    ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", AccountConstant.BecomeToBoss).withString("title", "成为股东").navigation();
                    break;
                case "8":
                    ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                            .withInt("editType", 1)
                            .withSerializable("extens", hashMap)
                            .withInt("type", PUBLISH_TYPE_QREQUESTION)
                            .navigation();
                    break;
            }
        });
        mPublishMenu.showMoreWindow(mBinding.getRoot());
    }

    private void processPro(NitAbsSampleAdapter nitAbsSampleAdapter) {
        nitAbsSampleAdapter.getmObjects().clear();

        AppVo appVo = new AppVo();
        appVo.name = "发动态";
        appVo.id = "0";
        appVo.icon = R.mipmap.publish_dy;

        AppVo appVo1 = new AppVo();
        appVo1.name = "发活动";
        appVo1.id = "1";
        appVo1.icon = R.mipmap.publish_act;


        AppVo appVo2 = new AppVo();
        appVo2.name = "发文章";
        appVo2.id = "2";
        appVo2.icon = R.mipmap.publish_art;

        AppVo appVo8 = new AppVo();
        appVo8.name = "发问答";
        appVo8.id = "8";
        appVo8.icon = R.mipmap.publish_ask;

        AppVo appVo3 = new AppVo();
        appVo3.name = "赚积分";
        appVo3.id = "3";
        appVo3.icon = R.mipmap.publish_point;


        AppVo appVo4 = new AppVo();
        appVo4.name = "找客服";
        appVo4.id = "4";
        appVo4.icon = R.mipmap.publish_cust;


        AppVo appVo5 = new AppVo();
        appVo5.name = "推广公社";
        appVo5.id = "5";
        appVo5.icon = R.mipmap.publish_push;


        AppVo appVo6 = new AppVo();
        appVo6.name = "加入公社";
        appVo6.id = "6";
        appVo6.icon = R.mipmap.publish_join;

        AppVo appVo7 = new AppVo();
        appVo7.name = "成为股东";
        appVo7.id = "7";
        appVo7.icon = R.mipmap.publish_ch;

        ArrayList<AppVo> appVos = new ArrayList();
        appVos.add(appVo);
        if (CacheUtils.getUser() != null && "2".equals(CacheUtils.getUser().reg_type)) {
            appVos.add(appVo1);
            appVos.add(appVo2);
            appVos.add(appVo8);
        }

        appVos.add(appVo3);
        appVos.add(appVo4);
        appVos.add(appVo5);
        appVos.add(appVo6);
        appVos.add(appVo7);
        nitAbsSampleAdapter.add(appVos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }


    @Override
    public void onBackPressed() {
//        if (mBinding.viewpager.getCurrentItem() == 2) {
//            if (fragments.get(2) != null) {
//                ((VideoListFragment) (fragments.get(2))).onBackPressed();
//            }
//        }

        if (isExit) {
            this.finish();
            ActivityUtils.finishAllActivities();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            isExit = true;
            new Handler().postDelayed(() -> isExit = false, 2000);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mBinding.tlHomeTab.setVisibility(View.GONE);
            mBinding.ivCenter.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mBinding.tlHomeTab.setVisibility(View.VISIBLE);
            mBinding.ivCenter.setVisibility(View.VISIBLE);
        }
    }
}

