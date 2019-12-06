package com.docker.nitsample.ui;

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
import com.docker.nitsample.databinding.ActivityMainBinding;
import com.docker.nitsample.vm.MainViewModel;
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
    private ArrayList<BaseItemModel> arrayList;
    private NitCommonListVm[] innerArr;
    private NitCommonListFragment nitCommonListFragment;


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
//        dbCacheUtils.clearCache("main_tab_01", null);
    }

    private BasePopupView popMenu;
    private BasePopupView editMenu;

    @Override
    public void initView() {
        mToolbar.hide();
        initMainTab();
        mBinding.ivCenter.setOnClickListener(v -> {

            if (popMenu == null) {
                popMenu = new XPopup.Builder(MainActivity.this)
                        .hasShadowBg(false)
                        .isCenterHorizontal(true) //是否与目标水平居中对齐
                        .offsetY(20)
                        .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"完成编辑", "取消当前", "预览当前"},
                                new int[]{},
                                (position, text) -> {
                                    switch (position) {
                                        case 0:
                                            if (outervm != null) {
                                                boolean isok = ImageUtils.save(ImageUtils.view2Bitmap(mBinding.getRoot()),
                                                        Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/zxd.jpg",
                                                        Bitmap.CompressFormat.JPEG);

                                                ArrayList<BaseCardVo> baseCardVos = new ArrayList<>();
                                                for (int i = 0; i < outervm.mItems.size(); i++) {
                                                    if (outervm.mItems.get(i) instanceof BaseCardVo) {
                                                        ((BaseCardVo) outervm.mItems.get(i)).position = i;
                                                        baseCardVos.add((BaseCardVo) outervm.mItems.get(i));
                                                    }
                                                }
                                                if (baseCardVos.size() > 0) {
                                                    dbCacheUtils.save("main_tab_01", baseCardVos, () -> {
                                                        ToastUtils.showShort("保存完成");
                                                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                                        ActivityUtils.startActivity(intent);
                                                    });
                                                } else {
                                                    ToastUtils.showShort("未检测到任何card");
                                                }
                                            }
                                            break;
                                        case 1:
                                            dbCacheUtils.clearCache("main_tab_01", null);
                                            ToastUtils.showShort("清空完成");
                                            break;
                                        case 2:
                                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                            ActivityUtils.startActivity(intent);
                                            break;

                                    }
                                })
                        .show();
            } else {
                popMenu.show();
            }


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

    NitCommonListVm outervm;

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {


        NitDelegetCommand nitDelegetCommand = null;
        switch (flag) {
            case 0:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return SampleListViewModel.class;
                    }

                    @Override
                    public Class[] providerInnerVm() {
                        Class[] classes = new Class[]{AccountCardViewModel.class, AppCardViewModel.class};
                        return classes;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonListVm[] innerArr, NitCommonListFragment nitCommonListFragment) {
                        outervm = commonListVm;
                        MainActivity.this.innerArr = innerArr;
                        MainActivity.this.nitCommonListFragment = nitCommonListFragment;

//                        dbCacheUtils.loadFromDb("main_tab_01").observe(MainActivity.this, o -> {
//                            if (o != null) {
//                                arrayList = (ArrayList<BaseItemModel>) o;
//                                CardProvideDispatcher.dispatcherCard(commonListVm, innerArr, nitCommonListFragment, arrayList);
//                            } else {
//                                CardProvideDispatcher.dispatcherCardDefault(outervm, innerArr, nitCommonListFragment);
//                /*                for (int i = 0; i < innerArr.length; i++) {
//                                    if (innerArr[i] instanceof CardMark.AppCardMark) {
//                                        ProviderAppCard.providerAppDefaultCard(commonListVm, innerArr[i], nitCommonListFragment);
//                                    }
//                                    if (innerArr[i] instanceof CardMark.AccountCardMark) {
//                                        ProviderAccountCard.providerAccountDefaultCard(commonListVm, innerArr[i], nitCommonListFragment);
//
//
////                                        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo(0, 0);
////                                        ProviderAppCard.providerAccountCard_menu(commonListVm, innerArr[i], accountIndexItemVo, nitCommonListFragment);
////
////                                        AccountSettingCardVo accountSettingCardVo = new AccountSettingCardVo(0, 2);
////                                        ProviderAppCard.providerAccountCard_setting(commonListVm, innerArr[i], accountSettingCardVo, nitCommonListFragment);
////
////                                        ProviderAppCard.providerAccountCard_header(commonListVm, innerArr[i], null, nitCommonListFragment);
////
////                                        AccountHeadCardVo accountHeadCardVo1 = new AccountHeadCardVo(0, 9);
////                                        HashMap<String, String> postArrMap1 = new HashMap<>();
////                                        postArrMap1.put("uuid", "c6864eec62ff32ced65be0cf2d61db40");
////                                        accountHeadCardVo1.mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap1));
////                                        HashMap<String, Object> disArrMap1 = new HashMap<>();
////                                        String[] userarr1 = new String[]{"all"};
////                                        disArrMap1.put("member", userarr1);
////                                        String[] exarr1 = new String[]{"dynamicNum", "dzNum", "plNum"};
////                                        disArrMap1.put("extData", exarr1);
////                                        accountHeadCardVo1.mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap1));
////                                        ProviderAppCard.providerAccountCard_header(commonListVm, innerArr[i], accountHeadCardVo1, nitCommonListFragment);
//                                    }
//
//
////                                    if(innerArr[i] instanceof AccountCardMark){
////
////                                    }
//
//                                }*/
//                            }
//                        });
                    }
                };
                break;
        }
        return nitDelegetCommand;
    }

    BasePopupView basePopupView;
    @Inject
    DbCacheUtils dbCacheUtils;

    private void initMainTab() {
        mBinding.tlHomeTab.setTabData(new Bottombar().initBotombar());
        mBinding.tlHomeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBinding.viewpager.setCurrentItem(position, false);
//                if (position == 3) {
//                    ImmersionBar.with(MainActivity.this)
//                            .fitsSystemWindows(true)
//                            .init();
//                } else {
//                    ImmersionBar.with(MainActivity.this)
//                            .fitsSystemWindows(true)
//                            .statusBarColor(com.docker.core.R.color.colorPrimary)
//                            .init();
//                }
            }

            @Override
            public void onTabReselect(int tabposition) {

                if (editMenu == null) {
                    editMenu = new XPopup.Builder(MainActivity.this)
                            .hasShadowBg(false)
                            .isCenterHorizontal(true) //是否与目标水平居中对齐
                            .offsetY(20)
                            .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                            .atView(mBinding.tlHomeTab)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                            .asAttachList(new String[]{"选择 card 类型", "全选", "account", "circle"},
                                    new int[]{},
                                    (position, text) -> {
                                        switch (position) {
                                            case 0:
                                                break;
                                            case 1:
                                                CardProvideDispatcher.dispatcherCardDefault(outervm, innerArr, nitCommonListFragment);
                                                break;
                                            case 2:
                                                ProviderAccountCard.providerAccountDefaultCard(outervm, innerArr[0], nitCommonListFragment);
                                                break;
                                            case 3:
                                                ProviderAppCard.providerAppDefaultCard(outervm, innerArr[1], nitCommonListFragment);
                                                break;
                                        }
                                    })
                            .show();
                } else {
                    editMenu.show();
                }

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
