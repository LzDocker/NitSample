package com.docker.cirlev2.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCirclePersonInfoBinding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.card.CirclePersonInfoHeadCardVm;
import com.docker.cirlev2.vo.card.PersonInfoHeadCardVo;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshHeader;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.SimpleMultiPurposeListener;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 *  我的主页
 * */
@Route(path = AppRouter.CIRCLE_person_info)
public class PersonInfoActivity extends NitCommonActivity<CircleDynamicDetailViewModel, Circlev2ActivityCirclePersonInfoBinding> {
    public ArrayList<Fragment> fragments = new ArrayList<>();
    private NitDelegetCommand nitDelegetCommand;

    public PersonInfoHeadVo mPersonInfoHeadVo;
    @Autowired()
    String memberid2;

    @Autowired()
    String uuid2;

    String name;

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
    public CircleDynamicDetailViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        if (TextUtils.isEmpty(memberid2)) {
            UserInfoVo userInfoVo = CacheUtils.getUser();
            if (userInfoVo == null) {
                ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                finish();
            } else {
                memberid2 = userInfoVo.uid;
                uuid2 = userInfoVo.uuid;
            }
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {

        mBinding.ivMore.setOnClickListener(v -> {
            processReportUi();
        });

        mBinding.ivBack.setOnClickListener(v -> finish());


        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.isActParent = true;
        commonListOptions.falg = 0;
        NitBaseProviderCard.providerCardNoRefreshForFrame(PersonInfoActivity.this.getSupportFragmentManager(), R.id.frame, commonListOptions);


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
                if (fragments != null && fragments.size() > 0) {
                    ((NitCommonListFragment) fragments.get(mBinding.viewPager.getCurrentItem())).onReFresh(mBinding.refresh);
                }
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mBinding.titlebar.setAlpha(1 - Math.min(percent, 1));
            }
        });

        mBinding.refresh.setEnableLoadMore(false);

    }


    private void processTab() {
        String[] titles = new String[]{"ta的动态", "ta的问答"};
        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions1.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        commonListOptions1.ReqParam.put("uuid2", uuid2);
        commonListOptions1.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions1.ReqParam.put("memberid2", memberid2);
        commonListOptions1.ReqParam.put("t", "dynamic");
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).withSerializable(CommonListParam, commonListOptions1).navigation());

        CommonListOptions commonListOptions2 = new CommonListOptions();
        commonListOptions2.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions2.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        commonListOptions2.ReqParam.put("uuid2", uuid2);
        commonListOptions2.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions2.ReqParam.put("memberid2", memberid2);
        commonListOptions2.ReqParam.put("t", "answer");
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).withSerializable(CommonListParam, commonListOptions2).navigation());

        // magic
        mBinding.viewPager.setAdapter(new CommonpagerStateAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        switch (flag) {
            case 0:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return null;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        PersonInfoHeadCardVo personInfoHeadVo = new PersonInfoHeadCardVo(0, 0);
                        personInfoHeadVo.isSelf = true;
                        personInfoHeadVo.isNoNetNeed = true;
                        UserInfoVo userInfoVo = CacheUtils.getUser();

                        personInfoHeadVo.mRepParamMap.put("memberid", userInfoVo.uid);
                        personInfoHeadVo.mRepParamMap.put("uuid", userInfoVo.uuid);
                        if (TextUtils.isEmpty(memberid2)) {
                            memberid2 = userInfoVo.uid;
                            uuid2 = userInfoVo.uuid;
                        }
                        processTab();
                        personInfoHeadVo.mRepParamMap.put("memberid2", memberid2);
                        personInfoHeadVo.mRepParamMap.put("uuid2", uuid2);
                        NitBaseProviderCard.providerCard(commonListVm, personInfoHeadVo, nitCommonFragment);
                        ((CirclePersonInfoHeadCardVm) personInfoHeadVo.mNitcommonCardViewModel).mAttenLv.observe(nitCommonFragment, s -> {

                        });
                        personInfoHeadVo.mCardVoLiveData.observe(PersonInfoActivity.this, o -> {
                            if (o != null) {
                                mPersonInfoHeadVo = (PersonInfoHeadVo) o;
                                if (CacheUtils.getUser().uid.equals(mPersonInfoHeadVo.getMemberid())) {
                                    mBinding.ivMore.setVisibility(View.GONE);
                                } else {
                                    mBinding.ivMore.setVisibility(View.VISIBLE);
                                }
                                name = ((PersonInfoHeadVo) o).nickname;
                                mBinding.empty.hide();
                            } else {
                                mBinding.empty.showError();
                            }
                        });
                    }
                };
                break;
            case 1:
            case 2:
                break;
        }

        return nitDelegetCommand;
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
        ImmersionBar.with(this).titleBar(mBinding.titlebar).init();
    }


    public void processReportUi() {
        if (mPersonInfoHeadVo == null) {
            return;
        }
        String[] title = new String[]{"举报", "拉黑"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(title, position -> {
            bottomSheetDialog.dismiss();
            switch (position) {
                case 0:
                    processReportUiStep2();
                    break;
                case 1:
                    mViewModel.circleBlackList(mPersonInfoHeadVo.getMemberid());
                    break;
            }
        });
        bottomSheetDialog.show(this);
    }


    public void processReportUiStep2() {
        String[] title = new String[]{"色情、赌博、毒品", "谣言、社会负面、诈骗", "邪教、非法集会、传销", "医药、整型、虚假广告", "有奖集赞和关注转发", "违反国家政策和法律"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(title, position -> {
            bottomSheetDialog.dismiss();
            mViewModel.circlePersionReport(mPersonInfoHeadVo.getMemberid(), title[position]);
        });
        bottomSheetDialog.show(this);
    }
}
