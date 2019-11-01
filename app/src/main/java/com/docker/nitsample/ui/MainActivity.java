package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.common.common.widget.boottomBar.Bottombar;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.SampleListViewModel;
import com.docker.nitsample.vm.SampleNetListViewModel;
import com.docker.videobasic.ui.SingleVideoActivity;
import com.docker.videobasic.ui.VideoListActivity;
import com.docker.videobasic.util.videolist.TestActivity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.router.AppRouter.HOME;

@Route(path = HOME)
public class MainActivity extends NitCommonActivity<MainViewModel, ActivityMainBinding> {

    @Inject
    AppVersionManager versionManager;
    private boolean isExit;

    @Inject
    List<Fragment> fragments;

    private PopmenuWj mpopMenu;
    private String type;
    private Disposable disposable;

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

            Intent intent = new Intent(MainActivity.this, VideoListActivity.class);
            startActivity(intent);

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

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {

        NitContainerCommand nitContainerCommand = null;
        switch (flag) {
            case 0:
                nitContainerCommand = (NitContainerCommand) () -> (SampleListViewModel.class);
                break;
            case 1:
                nitContainerCommand = (NitContainerCommand) () -> (SampleNetListViewModel.class);
                break;
        }
        return nitContainerCommand;
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
                    ImmersionBar.with(MainActivity.this)
                            .fitsSystemWindows(true)
                            .init();
                } else {
                    ImmersionBar.with(MainActivity.this)
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

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(() -> isExit = false, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) disposable.dispose();
    }
}
