package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DynamicDetailActivityBinding;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.ShoppingCartDbVo;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.command.ReplyCommand;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.XPopup.BottomPopup;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.core.widget.BottomSheetDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.umeng.socialize.UMShareAPI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_ACTIVE;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_NEWS;
import static com.docker.cirlev2.ui.publish.CirclePublishActivity.PUBLISH_TYPE_QREQUESTION;
import static com.docker.common.common.router.AppRouter.CIRCLE_dynamic_v2_detail;

//todo/*
// 扩展为个应用内自己处理详情content head默认共享，footer如需扩展在应用内实现 统一入口，降低耦合
// */
@Route(path = CIRCLE_dynamic_v2_detail)
public class CircleDynamicDetailActivity extends NitCommonActivity<CircleDynamicDetailViewModel, Circlev2DynamicDetailActivityBinding> {

    @Autowired
    String dynamicId;
    public ServiceDataBean mDynamicDetailVo;
    private BasePopupView basePopupView;

    private Disposable disposable;

    @Inject
    DbCacheUtils dbCacheUtils;


    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_dynamic_detail_activity;
    }

    @Override
    public CircleDynamicDetailViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("dynamic_refresh")) {
                UserInfoVo userInfoVo = CacheUtils.getUser();
                HashMap<String, String> param = new HashMap();
                param.put("dynamicid", dynamicId);
                param.put("memberid", userInfoVo.uid);
                param.put("uuid", userInfoVo.uuid);
                mViewModel.fechDynamicDetail(param);
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.hide();
        dynamicId = getIntent().getStringExtra("dynamicId");
        mBinding.setViewmodel(mViewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
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
        mViewModel.mOrderAddLv.observe(this, s -> {

        });

        mViewModel.mCartAddLv.observe(this, integer -> {

            switch (integer) {
                case 1:
                    if (basePopupView == null) {
                        initBottomPopup();
                    } else {
                        if (mReplyCommand != null) {
                            mReplyCommand.exectue();
                            mReplyCommand = null;
                        }
                    }
                    break;
                case 2:
                    break;
                case 3:
                    ToastUtils.showShort("网络错误请重试");
                    break;
            }
            mReplyCommand = null;
        });
    }

    private void processView() {

        // ui
        mBinding.setItem(mDynamicDetailVo);

        if (mDynamicDetailVo.getType().equals("goods")) {
            mBinding.dynamicDetailFootGoods.setVisibility(View.VISIBLE);
            mBinding.dynamicDetailFoot.setVisibility(View.GONE);
        } else {
            mBinding.dynamicDetailFoot.setVisibility(View.VISIBLE);
            mBinding.dynamicDetailFootGoods.setVisibility(View.GONE);
        }

        if (!mDynamicDetailVo.getUuid().equals(CacheUtils.getUser().uuid)) {
            mBinding.circleJubao.setVisibility(View.VISIBLE); // 举报
        }
        if (mDynamicDetailVo.getUuid().equals(CacheUtils.getUser().uuid)) {
            mBinding.ivMenuMore.setVisibility(View.VISIBLE); // 更多
        }

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

        //购物车的点击事件
        mBinding.tvShoppingCart.setOnClickListener(view -> {
            if (basePopupView == null) {

                initBottomPopup();
                // 请求加入购物车接口
//                requestServerCart("1", null);
            } else {
                basePopupView.show();
            }

        });
    }

    private ReplyCommand mReplyCommand;

    private void requestServerCart(String option, ReplyCommand replyCommand) {
        if (replyCommand != null) {
            mReplyCommand = replyCommand;
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("goodsid", mDynamicDetailVo.getDataid());
        param.put("operation", option);
        mViewModel.PushCartDataToServer(param);
    }


    private void requestServerCartNum(String num, ReplyCommand replyCommand) {
        if (replyCommand != null) {
            mReplyCommand = replyCommand;
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("goodsid", mDynamicDetailVo.getDataid());
        param.put("num", num);
        mViewModel.PushCartDataToServer(param);
    }

    private void initBottomPopup() {
        BottomPopup bottomPopup = new BottomPopup(this, "detail_foot_style");
        basePopupView = new XPopup.Builder(this).setPopupCallback(new XPopupCallback() {
            @Override
            public void onCreated() {
                TextView tv_add = bottomPopup.findViewById(com.docker.common.R.id.tv_add);
                TextView tv_reduce = bottomPopup.findViewById(com.docker.common.R.id.tv_reduce);
                TextView tv_num = bottomPopup.findViewById(com.docker.common.R.id.tv_num);
                TextView tv_shopping_cart = bottomPopup.findViewById(com.docker.common.R.id.tv_shopping_cart);
                TextView tv_buy = bottomPopup.findViewById(com.docker.common.R.id.tv_buy);
                TextView tv_barcode = bottomPopup.findViewById(com.docker.common.R.id.tv_barcode);
                TextView tv_money = bottomPopup.findViewById(com.docker.common.R.id.tv_money);

                ImageView iv_close = bottomPopup.findViewById(com.docker.common.R.id.iv_close);
                ImageView iv_goods_icon = bottomPopup.findViewById(com.docker.common.R.id.iv_goods_icon);

                GlideApp.with(iv_goods_icon).load(BdUtils.getDynamicSingleImg(mDynamicDetailVo)).into(iv_goods_icon);

                tv_barcode.setText("编号：" + mDynamicDetailVo.getExtData().goodsSn);
                tv_money.setText(mDynamicDetailVo.getExtData().price);
                tv_add.setOnClickListener(view -> {
                    String num = tv_num.getText().toString();

                    tv_num.setText(String.valueOf(Integer.valueOf(num) + 1));

//                    requestServerCart("1", () -> {
//                        tv_num.setText(String.valueOf(Integer.valueOf(num) + 1));
//                    });


//                    BigDecimal bigPrice = new BigDecimal(mDynamicDetailVo.getExtData().price);
//                    BigDecimal bigNum = new BigDecimal(tv_num.getText().toString());
//                    tv_money.setText(String.valueOf(bigPrice.multiply(bigNum)));
                });

                tv_reduce.setOnClickListener(view -> {
                    String num = tv_num.getText().toString();
                    if ("0".equals(num)) {
                        ToastUtils.showShort("已减至最低购买数量");
                        return;
                    }
                    if (!"1".equals(num)) {
                        tv_num.setText(String.valueOf(Integer.valueOf(num) - 1));
//                        requestServerCart("2", () -> tv_num.setText(String.valueOf(Integer.valueOf(num) - 1)));
//                        BigDecimal bigPrice = new BigDecimal(mDynamicDetailVo.getExtData().price);
//                        BigDecimal bigNum = new BigDecimal(tv_num.getText().toString());
//                        tv_money.setText(String.valueOf(bigPrice.multiply(bigNum)));
                    }
                });
                tv_shopping_cart.setOnClickListener(view -> {

                    requestServerCartNum(tv_num.getText().toString().trim(), () -> {
                        basePopupView.dismiss();
                        ARouter.getInstance().build(AppRouter.CIRCLE_shopping_car).navigation();
                    });


//                    dbCacheUtils.loadFromDb("shopcart").observe(CircleDynamicDetailActivity.this, o -> {
//                        if (o != null && ((ArrayList<ShoppingCartDbVo>) o).size() > 0) {
//                            boolean isAdded = false;
//                            for (int i = 0; i < ((ArrayList<ShoppingCartDbVo>) o).size(); i++) {
//                                if (((ArrayList<ShoppingCartDbVo>) o).get(i).id.equals(mDynamicDetailVo.getDataid())) {
//                                    isAdded = true;
//                                    ((ArrayList<ShoppingCartDbVo>) o).get(i).num = Integer.parseInt(tv_num.getText().toString().trim());
//                                }
//                            }
//                            if (!isAdded) {
//                                ShoppingCartDbVo shoppingCartDbVo = new ShoppingCartDbVo();
//                                shoppingCartDbVo.id = mDynamicDetailVo.getDataid();
//                                shoppingCartDbVo.num = Integer.parseInt(tv_num.getText().toString().trim());
//                                ((ArrayList<ShoppingCartDbVo>) o).add(shoppingCartDbVo);
//                            }
//                            dbCacheUtils.save("shopcart", ((ArrayList<ShoppingCartDbVo>) o), () -> {
//                                ARouter.getInstance().build(AppRouter.CIRCLE_shopping_car).navigation();
//                            });
//
//                        } else {
//                            ShoppingCartDbVo shoppingCartDbVo = new ShoppingCartDbVo();
//                            shoppingCartDbVo.id = mDynamicDetailVo.getDataid();
//                            shoppingCartDbVo.num = Integer.parseInt(tv_num.getText().toString().trim());
//
//                            ArrayList<ShoppingCartDbVo> shoppingCartDbVos = new ArrayList<>();
//                            shoppingCartDbVos.add(shoppingCartDbVo);
//                            dbCacheUtils.save("shopcart", shoppingCartDbVos, () -> {
//                                ARouter.getInstance().build(AppRouter.CIRCLE_shopping_car).navigation();
//                            });
//                        }
//                    });


                });
                tv_buy.setOnClickListener(view -> {
                    ARouter.getInstance().build(AppRouter.ORDER_MAKER).withSerializable("ServiceDataBean", mDynamicDetailVo).navigation();
                    basePopupView.dismiss();
                });

                iv_close.setOnClickListener(view -> {
                    basePopupView.dismiss();
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
        }).asCustom(bottomPopup).show();

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
                        StaCirParam staCirParam = new StaCirParam(mDynamicDetailVo.getCircleid(), mDynamicDetailVo.getUtid(), mDynamicDetailVo.getCircleName());
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
//                    Fragment frag = (Fragment) ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL).withSerializable("config", mDynamicDetailVo).navigation();
//                    FragmentUtils.add(getSupportFragmentManager(), frag, R.id.frame_content);
                    FragmentUtils.add(getSupportFragmentManager(), DynamicH5Fragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
                case "news":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicH5Fragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
                case "dynamic":
                case "answer":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicDetailFragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
                case "active":
                    Fragment fragment = (Fragment) ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL).withSerializable("config", mDynamicDetailVo).navigation();
                    FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame_content);
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