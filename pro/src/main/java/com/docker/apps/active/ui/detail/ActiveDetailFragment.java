package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.active.ui.index.ActiveContainerFragment;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.card.ActiveDetailHeadCard;
import com.docker.apps.active.widget.CardActivePopup;
import com.docker.apps.databinding.ProActiveDetailFragmentLayoutBinding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.tool.PhotoGalleryUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.databinding.CommonDetailCoutainerLayoutBinding;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;

import java.util.ArrayList;
import java.util.HashMap;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 活动详情
 * */
@Route(path = AppRouter.ACTIVE_DEATIL)
public class ActiveDetailFragment extends NitCommonFragment<ActiveCommonViewModel, ProActiveDetailFragmentLayoutBinding> {

    ActiveDetailHeadCard activeDetailHeadCard;
    ActiveVo activeVo;
    public String activityid;
    private BasePopupView basePopupView;
    public int edit = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_detail_fragment_layout;
    }

    @Override
    protected ActiveCommonViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        mBinding.get().submitArea.setOnClickListener(v -> {
            if (activeVo == null) {
                return;
            }

            if (CacheUtils.getUser() == null) {
                // 去登录
            }


            if (activeVo.status == -1) {
                ToastUtils.showShort("已结束");
                return;
            }
            if (activeVo.enrollNum.equals(activeVo.limitNum)) {
                ToastUtils.showShort("报名人数已满");
                return;
            }
            if (activeVo.status == 1) {

                if (activeVo.signStatus == 0) {
                    ToastUtils.showShort("待审核");
                    return;
                }
                if (activeVo.signStatus == 1) {
//                    return "查看我的凭证"; //绿色
                    showPop(activeVo.evoucherNo, activeVo.AuditUrl);
                    //todo
                }
                if (activeVo.signStatus == 2) {
                    ToastUtils.showShort("已核销");
                    return;
                }
                if (activeVo.signStatus == -1) {
                    ToastUtils.showShort("报名被驳回");
                    return;
                }
                if (activeVo.signStatus == -2) {
                    HashMap<String, String> parm = new HashMap<>();
                    parm.put("sign_memberid", CacheUtils.getUser().uid);
                    parm.put("activityid", activeVo.dataid);
                    parm.put("circleid", activeVo.circleid);
                    mViewModel.activeJoin(parm);
                }
            }
        });
    }

    private void showPop(String evoucherNo, String AuditUrl) {
        if (TextUtils.isEmpty(evoucherNo) || TextUtils.isEmpty(AuditUrl)) {
            return;
        }
        CardActivePopup centerPopup = new CardActivePopup(this.getHoldingActivity());
        basePopupView = new XPopup.Builder(this.getHoldingActivity()).setPopupCallback(new XPopupCallback() {
            @Override
            public void onCreated() {
                TextView textView = basePopupView.findViewById(R.id.tv_pzh);
                TextView title = basePopupView.findViewById(R.id.tv_title);
                title.setText(activeVo.title);
                textView.setText("凭证号：" + evoucherNo);
                ImageView imageView = basePopupView.findViewById(R.id.iv_bar_code);
                GlideApp.with(imageView).load(ThiredPartConfig.BarcoderUrl + AuditUrl).into(imageView);
                imageView.setOnLongClickListener(v1 -> {
                    PhotoGalleryUtils.saveImageToGallery(ActiveDetailFragment.this.getHoldingActivity(), ImageUtils.view2Bitmap(imageView), Constant.BaseFileFloder, "ccc");
                    ToastUtils.showShort("保存成功");
                    return true;
                });
            }

            @Override
            public void beforeShow() {

            }

            @Override
            public void onShow() {

            }

            @Override
            public void onDismiss() {

            }

            @Override
            public boolean onBackPressed() {
                return false;
            }
        }).asCustom(centerPopup).show();


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edit = getArguments().getInt("edit");
        activityid = getArguments().getString("activityid");
//        if (edit == 1) {
//            mViewModel.mActiveDetailLv.observe(this, activeVo -> {
//
//            });
//            HashMap<String, String> parm = new HashMap<>();
//            parm.put("activityid", activityid);
//            mViewModel.fetchActiveVo(parm);
//        }

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), com.docker.common.R.id.frame_head, commonListOptions);


//        mBinding.get().refresh.setEnableLoadMore(true);

        mViewModel.mSignSuccLv.observe(this, s -> {
            //
            if (s != null) {
                // succ
                ARouter.getInstance()
                        .build(AppRouter.ACTIVE_SUCC).withSerializable("ActiveSucVo", s)
                        .withString("activityid", activeVo.dataid)
                        .navigation();
                getHoldingActivity().finish();
            }
        });

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                activeDetailHeadCard = new ActiveDetailHeadCard(0, 0);
                activeDetailHeadCard.mRepParamMap.put("activityid", getArguments().getString("activityid"));
                if (CacheUtils.getUser() != null) {
                    activeDetailHeadCard.mRepParamMap.put("memberid", CacheUtils.getUser().uid);
                }
                NitBaseProviderCard.providerCard(commonListVm, activeDetailHeadCard, nitCommonFragment);
                activeDetailHeadCard.voObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        activeVo = activeDetailHeadCard.voObservableField.get();
                        if (!TextUtils.isEmpty(activeVo.dataid)) {
                            mBinding.get().setItem(activeVo);
                            processBot(activeVo);
                            processTab();
                            mBinding.get().empty.hide();
                        } else {
                            mBinding.get().empty.showError();
                            mBinding.get().empty.setOnretryListener(() -> {
                                activeDetailHeadCard.mNitcommonCardViewModel.loadCardData(activeDetailHeadCard);
                            });
                        }
                    }
                });
            }
        };
        return nitDelegetCommand;
    }

    public void processBot(ActiveVo activeVo) {
        mBinding.get().submitArea.setBackgroundResource(getBackGround(activeVo));
        mBinding.get().submitArea.setText(getActiveStr(activeVo));
    }

    public static String getActiveStr(ActiveVo activeVo) {
        String str = "";

        /*
        * 活动状态：
进行中 status=1      显示按钮：立即报名（紫色）
已结束 status=-1    显示按钮：已结束（灰色） 不可点击

报名状态：
待审核：signStatus=0  显示按钮：待审核（红色）不可点击
待参加：signStatus=1  显示按钮：查看我的凭证（绿色）点击弹框展示
已核销 signStatus=2
忽略：signStatus=-1
        * */


        if (activeVo == null) {
            return str;
        }
        if (activeVo.status == -1) {
            return "已结束"; //灰色
        }
        if (activeVo.enrollNum.equals(activeVo.limitNum)) {
            return "报名人数已满";//灰色
        }
        if (activeVo.status == 1) {

            if (activeVo.signStatus == 0) {
                return "待审核"; //红色
            }
            if (activeVo.signStatus == 1) {
                return "查看我的凭证"; //绿色
            }
            if (activeVo.signStatus == 2) {
                return "已核销"; // 灰色
            }
            if (activeVo.signStatus == -1) {
                return "报名被驳回";//灰色
            }
            if (activeVo.signStatus == -2) {
                return "立即报名"; //紫色
            }
        }
        return str;
    }

    public int getBackGround(ActiveVo activeVo) {

        int str = R.drawable.common_radius30_hui;
        if (activeVo == null) {
            return str;
        }
        if (activeVo.status == -1) {
            return R.drawable.common_radius30_hui; //灰色
        }
        if (activeVo.enrollNum.equals(activeVo.limitNum)) {
            return R.drawable.common_radius30_hui;
        }
        if (activeVo.status == 1) {

            if (activeVo.signStatus == 0) {
                //红色
                return R.drawable.common_radius30_hong;
            }

            if (activeVo.signStatus == 1) {
                //绿色
                return R.drawable.common_radius30_lv;
            }
            if (activeVo.signStatus == 2) {
                // 灰色
                return R.drawable.common_radius30_hui;
            }
            if (activeVo.signStatus == -1) {
                return R.drawable.common_radius30_hui;
            }
            //紫色
            return R.drawable.common_radius30_zi;
        }
        return str;
    }


    private void processTab() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[]{"活动介绍", "活动动态"};


        fragments.add((Fragment) ARouter.getInstance()
                //webcontent
                .build(AppRouter.CIRCLEV3_COMMONH5)
                .withString("webcontent", activeVo.content)
                .navigation());

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("t", "idle");
        commonListOptions.ReqParam.put("activityid",activeVo.dataid);
        fragments.add((Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .withSerializable(CommonListParam, commonListOptions)
                .navigation());

        // magic
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic

    }

    @Override
    public void initImmersionBar() {

    }

}
