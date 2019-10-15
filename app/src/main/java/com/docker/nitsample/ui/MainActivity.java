package com.docker.nitsample.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.ui.MineFragmentWj;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.common.common.widget.boottomBar.Bottombar;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.ui.index.IndexFragment;
import com.docker.nitsample.vm.MainViewModel;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends NitCommonActivity<MainViewModel, ActivityMainBinding> {

    @Inject
    AppVersionManager versionManager;
    private boolean isExit;
    private List<Fragment> fragments = new ArrayList<>();
    private PopmenuWj mpopMenu;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        initMainTab();
        mBinding.ivCenter.setOnClickListener(v -> {
            showPopMenu();
        });
    }

    @Override
    public void initObserver() {
        this.getLifecycle().addObserver(versionManager.Bind(this,
                this, mViewModel.checkUpData(),
                versionManager.TYPE_DIALOG, "com.bfhd.tjxq"));
    }

    @Override
    public void initRouter() {

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
                            .fitsSystemWindows(false)
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
        fragments.add(IndexFragment.newInstance());
        fragments.add(IndexFragment.newInstance());
        fragments.add(IndexFragment.newInstance());
        fragments.add(MineFragmentWj.newInstance());
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
