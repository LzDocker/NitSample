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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.card.AccountCardViewModel;
import com.bfhd.account.vm.card.ProviderAccountCard;
import com.bfhd.circle.widget.popmenu.PopmenuWj;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonListOptions;
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
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.databinding.ActivityPreviewBinding;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.PreviewViewModel;
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
import static com.docker.common.common.router.AppRouter.HOME_preview;

@Route(path = HOME_preview)
public class PreviewEditActivity extends NitCommonActivity<PreviewViewModel, ActivityPreviewBinding> {

    @Autowired
    String dbTabid;

    @Autowired
    boolean isEdit;

    private ArrayList<BaseItemModel> config;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public PreviewViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PreviewViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getSerializableExtra("config") != null) {
            FragmentUtils.add(getSupportFragmentManager(),
                    EditSpaFragment.getInstance((ArrayList<BaseCardVo>) getIntent().getSerializableExtra("config"),
                            getIntent().getBooleanExtra("isEdit", false)),
                    R.id.frame_spa);
        } else {
            FragmentUtils.add(getSupportFragmentManager(),
                    EditSpaFragment.getInstance(getIntent().getStringExtra("dbTabid"),
                            getIntent().getBooleanExtra("isEdit", false)),
                    R.id.frame_spa);
        }
    }

    @Override
    public void initView() {
        mToolbar.hide();

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

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
