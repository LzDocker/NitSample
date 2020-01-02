package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.common.common.vm.NitCommonListVm;
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

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
//            showPopMenu();
            ARouter.getInstance().build(AppRouter.HOME_edit_index).navigation();

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
                versionManager.TYPE_DIALOG, "com.bfhd.tjxq"));

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("Badger")) {
                int num = (int) rxEvent.getR();
                if (num > 0) {
                    mBinding.tlHomeTab.showDot(3);
                } else {
                    mBinding.tlHomeTab.hideMsg(3);
                }
            }
            if (rxEvent.getT().equals("change")) {
                int num = (int) rxEvent.getR();
                mBinding.tlHomeTab.setCurrentTab(num);
                mBinding.viewpager.setCurrentItem(num - 1, false);
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
                mBinding.viewpager.setCurrentItem(position, false);
                if (position == 3) {
                    ImmersionBar.with(MainTygsActivity.this)
                            .fitsSystemWindows(true)
                            .init();
                } else {
                    ImmersionBar.with(MainTygsActivity.this)
                            .fitsSystemWindows(true)
                            .statusBarColor(com.docker.core.R.color.colorPrimary)
                            .init();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mBinding.tlHomeTab.setCurrentTab(0);
//        CommonListOptions commonListOptions = new CommonListOptions();
//        commonListOptions.falg = 0;
////        fragments.add(SampleFragment.newInstance());
//        NitCommonContainerFragment containerFragment = NitCommonContainerFragment.newinstance(commonListOptions);
//        fragments.add(containerFragment);
//
//        CommonListOptions commonListOptions1 = new CommonListOptions();
//        commonListOptions1.falg = 1;
//        commonListOptions1.refreshState = 0;
//        commonListOptions1.RvUi = 0;
//        commonListOptions1.ReqParam.put("t", "dynamic");
//        commonListOptions1.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
//        commonListOptions1.ReqParam.put("memberid", "64");
//
//        fragments.add(NitCommonContainerFragment.newinstance(commonListOptions1));
////        fragments.add(SampleListFragment.newInstance());
//
//        fragments.add(IndexFragment.newInstance());
//        fragments.add(FragmentMineIndex.newInstance());

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }


    @Override
    public void onBackPressed() {
        if (mBinding.viewpager.getCurrentItem() == 2) {
            if (fragments.get(2) != null) {
                ((VideoListFragment) (fragments.get(2))).onBackPressed();
            }
        }

        if (isExit) {
            this.finish();
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

