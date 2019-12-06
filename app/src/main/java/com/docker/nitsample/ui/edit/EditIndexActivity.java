package com.docker.nitsample.ui.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.card.AccountCardViewModel;
import com.bfhd.account.vm.card.ProviderAccountCard;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.versionmanager.AppVersionManager;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.boottomBar.Bottombar;
import com.docker.nitsample.R;
import com.docker.nitsample.card.CardProvideDispatcher;
import com.docker.nitsample.databinding.ActivityEditIndexBinding;
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.SampleEditSpaViewModel;
import com.docker.nitsample.vm.SampleListViewModel;
import com.docker.nitsample.vm.card.AppCardViewModel;
import com.docker.nitsample.vm.card.ProviderAppCard;
import com.docker.videobasic.ui.VideoListFragment;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.router.AppRouter.HOME;
import static com.docker.common.common.router.AppRouter.HOME_edit_index;

@Route(path = HOME_edit_index)
public class EditIndexActivity extends NitCommonActivity<SampleEditSpaViewModel, ActivityEditIndexBinding> {

    private boolean isExit;

    List<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_index;
    }

    @Override
    public SampleEditSpaViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleEditSpaViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        initMainTab();
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        return null;
    }

    private void initMainTab() {
        mBinding.tlHomeTab.setTabData(new Bottombar().initBotombar());
        mBinding.tlHomeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBinding.viewpager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int tabposition) {


            }
        });
        mBinding.tlHomeTab.setCurrentTab(0);
        mBinding.viewpager.setOffscreenPageLimit(2);
        fragments.add(EditSpaFragment.getInstance("", true));
        fragments.add(EditListFragment.newInstance());
        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
