package com.docker.cirlev2.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCirclePersonInfoBinding;
import com.docker.cirlev2.vm.CirclePersonInfoViewModel;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 *  我的主页
 * */
@Route(path = AppRouter.CIRCLE_person_info)
public class PersonInfoActivity extends NitCommonActivity<NitEmptyViewModel, Circlev2ActivityCirclePersonInfoBinding> {
    public ArrayList<Fragment> fragments = new ArrayList<>();
    @Inject
    ViewModelProvider.Factory factory;
    private int mOffset = 0;
    private int mScrollY = 0;
    CommentVo commentVo;
    //    NitAbsSampleAdapter adapter;
    private String replay;
    private ServiceDataBean serviceDataBean;
    private RelativeLayout relativeLayout;
    private NitCommonListVm outerVm;
    private NitDelegetCommand nitDelegetCommand;

    public static void startMe(Context context, CommentVo commentVo, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentVo", commentVo);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_person_info;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.isActParent = true;
        NitBaseProviderCard.providerCardNoRefreshForFrame(PersonInfoActivity.this.getSupportFragmentManager(), R.id.frame, commonListOptions);
        List<CircleTitlesVo> circleTitlesVos = new ArrayList<>();
        CircleTitlesVo circleTitlesVo = new CircleTitlesVo();
        circleTitlesVo.setName("ta的动态");
        CircleTitlesVo circleTitlesVo1 = new CircleTitlesVo();
        circleTitlesVo1.setName("ta的问答");
        circleTitlesVos.add(circleTitlesVo);
        circleTitlesVos.add(circleTitlesVo1);
        peocessTab(circleTitlesVos);

        mBinding.appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        mBinding.title.setText("");
                        break;
                    case EXPANDED:
                        mBinding.title.setText("");
                        ImmersionBar.with(PersonInfoActivity.this)
                                .statusBarDarkFont(false)   //状态栏字体是深色，不写默认为亮色
                                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                                .init();
                        break;
                    case COLLAPSED:
                        String name = "gjw";
                        if (!TextUtils.isEmpty(name)) {
                            mBinding.title.setText(name);
                            ImmersionBar.with(PersonInfoActivity.this)
                                    .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                                    .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                                    .init();
                        }
                        break;
                }
            }
        });

        mBinding.refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
                mBinding.titlebar.setAlpha(1 - Math.min(percent, 1));
            }
        });

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        switch (flag) {
            case 0:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return CirclePersonInfoHeadCardVm.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        PersonInfoHeadVo personInfoHeadVo = new PersonInfoHeadVo(0, 0);
                        NitBaseProviderCard.providerCard(commonListVm, personInfoHeadVo, nitCommonFragment);
//                outerVm = commonListVm;
//                mBinding.setViewmodel((CircleShoppingViewModel) commonListVm);
                    }
                };
                break;
            case 1:
            case 2:
                break;
        }

        return nitDelegetCommand;
    }

    public void peocessTab(List<CircleTitlesVo> circleTitlesVos) {
        String[] titles = new String[circleTitlesVos.size()];
        for (int i = 0; i < circleTitlesVos.size(); i++) {
            titles[i] = circleTitlesVos.get(i).getName();
            fragments.add((Fragment) ARouter.getInstance()
                    .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME_COUTAINER)
                    .withSerializable("tabVo", (Serializable) circleTitlesVos)
                    .withInt("pos", i)
                    .navigation());
        }
        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }

    @Override
    public void initObserver() {
        mViewModel.mServerLiveData.observe(this, o -> {

        });

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(mBinding.titlebar)
                .init();
    }
}
