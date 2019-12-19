package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DynamicDetailActivityBinding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.core.widget.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;

import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_ACTIVE;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_NEWS;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_QREQUESTION;
import static com.docker.common.common.router.AppRouter.CIRCLE_dynamic_v2_detail;

@Route(path = CIRCLE_dynamic_v2_detail)
public class CircleDynamicDetailActivity extends NitCommonActivity<CircleDynamicDetailViewModel, Circlev2DynamicDetailActivityBinding> {

    @Autowired
    String dynamicId;
    public ServiceDataBean mDynamicDetailVo;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_dynamic_detail_activity;
    }

    @Override
    public CircleDynamicDetailViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        dynamicId = getIntent().getStringExtra("dynamicId");
        mBinding.setViewmodel(mViewModel);
    }


    @Override
    public void initObserver() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        param.put("dynamicid", dynamicId);
        param.put("memberid", userInfoVo.uid);
        param.put("uuid", userInfoVo.uuid);
        mViewModel.fechDynamicDetail(param);
        mViewModel.mDynamicDetailLv.observe(this, serviceDataBean -> {
            mDynamicDetailVo = serviceDataBean;
            if (mDynamicDetailVo != null) {
                mBinding.empty.postDelayed(() -> mBinding.empty.hide(), 400);
            } else {
                mBinding.empty.showError();
                mBinding.empty.setOnretryListener(() -> {
                    mViewModel.fechDynamicDetail(param);
                });
                return;
            }
            processData();
            processView();
        });

        mViewModel.mDynamicDelLv.observe(this, s -> finish());

        mViewModel.mServerLiveData.observe(this, o -> {
        });

        mViewModel.mCommentVoMLiveData.observe(this, commentRstVo -> {
        });
        mViewModel.mCollectLv.observe(this, s -> {

        });
    }

    private void processView() {

        // ui
        mBinding.setItem(mDynamicDetailVo);
        if (!mDynamicDetailVo.getUuid().equals(CacheUtils.getUser().uuid)) {
            mBinding.circleJubao.setVisibility(View.VISIBLE); // 举报
        }
//            if (mDynamicDetailVo.getUuid().equals(CacheUtils.getUser().uuid)) {
//                mBinding.ivMenuMore.setVisibility(View.VISIBLE); // 更多
//            }
        mBinding.ivMenuMore.setVisibility(View.VISIBLE); // 更多

        mBinding.ivShare.setOnClickListener(v -> {
            mViewModel.ItemZFClick(mDynamicDetailVo, null); // 转发
        });
        mBinding.circleJubao.setOnClickListener(v -> {
            processReportUi();
        });
        mBinding.ivBack.setOnClickListener(v -> finish()); // 返回
        mBinding.ivMenuMore.setOnClickListener(v -> {  // 更多
            showCircleMenu();
        });


    }

    public void processReportUi() {
        String[] title = new String[]{"举报", "拉黑"};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(title, position -> {
            bottomSheetDialog.dismiss();
            switch (position) {
                case 0:
                    processReportUiStep2();
                    break;
                case 1:
                    mViewModel.circleBlackList(mDynamicDetailVo.getMemberid());
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
            mViewModel.circlePersionReport(mDynamicDetailVo.getMemberid(), title[position]);
        });
        bottomSheetDialog.show(this);
    }


    public void showCircleMenu() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setDataCallback(new String[]{"编辑", "删除"}, position -> {
            bottomSheetDialog.dismiss();
            int type = 0;
            switch (position) {
                case 0:
                    switch (mDynamicDetailVo.getType()) {
                        case "news":
                            type = PUBLISH_TYPE_NEWS;
                            break;

                        case "dynamic":
                            type = PUBLISH_TYPE_ACTIVE;
                            break;

                        case "goods":
//                            UserInfoVo userInfoVo = CacheUtils.getUser();
//                            String weburl = Constant.BaseServeTest + "index.php?m=publish.push_dynamic" +
//                                    "&t=" + serviceDataBean.getType() + "&memberid=" + userInfoVo.uid + "&uuid=" + userInfoVo.uuid + "" +
//                                    "&lat=" + lat + "&lng=" + lng + "&area1=" + province + "&area2=" + city + "&area3=" + district + "&id=" + serviceDataBean.getDynamicid();
//                            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", weburl).withString("title", "编辑").navigation();
                            break;

                        case "answer":
                            type = PUBLISH_TYPE_QREQUESTION;
                            break;
                    }

                    if (type != 0) {
                        StaCirParam staCirParam = new StaCirParam();
                        staCirParam.serviceDataBean = mDynamicDetailVo;
                        ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX).withSerializable("mStartParam", staCirParam).withInt("editType", 2).withInt("type", type).navigation();
                    }

                    break;

                case 1:
                    showConfirmdialog();
                    break;
            }
        });
        bottomSheetDialog.show(this);
    }

    private void showConfirmdialog() {
        String tip = "";
        if ("dynamic".equals(mDynamicDetailVo.getType())) {
            tip = "确定删除该动态?";
        } else {
            tip = "确定删除该商品?";
        }
        ConfirmDialog.newInstance(tip, "删除后无法恢复，请谨慎删除").setConfimLietener(new ConfirmDialog.ConfimLietener() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onConfim() {
                mViewModel.dynamicDel(mDynamicDetailVo.getCircleid(), mDynamicDetailVo.getDynamicid(), mDynamicDetailVo.getUtid());
                RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
            }

            @Override
            public void onConfim(String edit) {

            }
        }).setMargin(24).show(getSupportFragmentManager());
    }


    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    private void processData() {
        if (mDynamicDetailVo != null) {
            switch (mDynamicDetailVo.getType()) {
                case "goods":
                case "news":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicH5Fragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
                case "dynamic":
                case "answer":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicDetailFragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}