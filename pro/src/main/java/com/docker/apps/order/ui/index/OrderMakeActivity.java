package com.docker.apps.order.ui.index;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderMakerActivityBinding;
import com.docker.apps.order.vm.OrderMakerViewModel;
import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.entity.SubmitOrderGoodsVo;
import com.docker.apps.order.vo.entity.SubmitOrderShopVo;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarVoV3;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.PayOrederVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.empty.EmptyStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 填写订单
 **/
@Route(path = AppRouter.ORDER_MAKER)
public class OrderMakeActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderMakerActivityBinding> {

    private CircleShoppingViewModel innerVm;

    public ServiceDataBean mDynamicDetailVo;

    private BigDecimal bigDecimaltotal;

    /*
     * 1  购物车列表点进来
     *
     * 2  购买单个商品
     * */
    public int flag = 1;

    private AddressVo addressVo;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_maker_activity;
    }

    @Override
    public OrderMakerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderMakerViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.empty.hide();
        mToolbar.setTitle("填写订单");
        mDynamicDetailVo = (ServiceDataBean) getIntent().getSerializableExtra("ServiceDataBean");
        if (mDynamicDetailVo != null) {
            flag = 2;
        }
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = flag;
        commonListOptions.isActParent = true;
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("falg", String.valueOf(flag));
        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(OrderMakeActivity.this.getSupportFragmentManager(), R.id.goods_frame, commonListOptions);

        mViewModel.fetchAddress();
    }

    @Override
    public void initView() {
        mBinding.tvAddress.setOnClickListener(v -> {
            if (addressVo == null) {
                ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_MANAGER).withString("type", "1").navigation(OrderMakeActivity.this, 10011);
            } else {
                ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_MANAGER).withString("type", "2").navigation(OrderMakeActivity.this, 10011);
            }
        });
    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleShoppingViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innerVm = (CircleShoppingViewModel) commonListVm;
                innerVm.flag = 1;
                mBinding.empty.hide();
                if (mDynamicDetailVo != null) {
                    ShoppingCarVoV3 shoppingCarVoV3 = new ShoppingCarVoV3();
                    shoppingCarVoV3.circleName = mDynamicDetailVo.getCircleName();
                    shoppingCarVoV3.circleid = mDynamicDetailVo.getCircleid();
                    ArrayList<ShoppingCarVoV3.CardInfo> infos = new ArrayList<>();
                    ShoppingCarVoV3.CardInfo cardInfo = shoppingCarVoV3.new CardInfo();
                    cardInfo.setNum(1);
                    cardInfo.circleid = mDynamicDetailVo.getCircleid();
                    cardInfo.goodsName = mDynamicDetailVo.getExtData().getName();

                    cardInfo.goodsid = mDynamicDetailVo.getDataid();
                    cardInfo.id = mDynamicDetailVo.getDynamicid();

                    cardInfo.price = mDynamicDetailVo.getExtData().price;
                    cardInfo.picture = BdUtils.getDynamicSingleImg(mDynamicDetailVo);
                    cardInfo.memberid = mDynamicDetailVo.getExtData().getMemberid();
                    cardInfo.transMoney = mDynamicDetailVo.getExtData().transMoney;

                    infos.add(cardInfo);
                    shoppingCarVoV3.info = infos;

                    innerVm.mItems.add(shoppingCarVoV3);
                    innerVm.processTotalMoney(innerVm.mItems);
                    innerVm.processTotalTransMoney(innerVm.mItems);
                    innerVm.mEmptycommand.set(EmptyStatus.BdHiden);

                }

                ((CircleShoppingViewModel) commonListVm).mCartAddLv.observe(nitCommonFragment, s -> {
                });
                ((CircleShoppingViewModel) commonListVm).mCartDelLv.observe(nitCommonFragment, s -> {
                });
                ((CircleShoppingViewModel) commonListVm).mTotalMoney.observe(nitCommonFragment, s -> {
                    mBinding.goodsmoney.setText("¥" + s);
                    if (TextUtils.isEmpty(((CircleShoppingViewModel) commonListVm).mTotalTransMoney.getValue())) {
                        return;
                    }
                    BigDecimal bigDecimal = new BigDecimal(((CircleShoppingViewModel) commonListVm).mTotalMoney.getValue());
                    BigDecimal bigDecimal2 = new BigDecimal(((CircleShoppingViewModel) commonListVm).mTotalTransMoney.getValue());
                    bigDecimaltotal = bigDecimal.add(bigDecimal2).setScale(2, BigDecimal.ROUND_HALF_UP);
                    mBinding.goodsrealmoney.setText("¥" + bigDecimaltotal);
                    mBinding.moneytotal.setText(String.valueOf(bigDecimaltotal));
                });
                ((CircleShoppingViewModel) commonListVm).mTotalTransMoney.observe(nitCommonFragment, s -> {
                    mBinding.goodstransmoney.setText("¥" + s);
                    BigDecimal bigDecimal = new BigDecimal(((CircleShoppingViewModel) commonListVm).mTotalMoney.getValue());
                    BigDecimal bigDecimal2 = new BigDecimal(((CircleShoppingViewModel) commonListVm).mTotalTransMoney.getValue());
                    bigDecimaltotal = bigDecimal.add(bigDecimal2).setScale(2, BigDecimal.ROUND_HALF_UP);
                    mBinding.goodsrealmoney.setText("¥" + bigDecimaltotal);
                    mBinding.moneytotal.setText(String.valueOf(bigDecimaltotal));
                });

            }
        };
        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

        mViewModel.mAddressLv.observe(this, addressVos -> {
            if (addressVos != null && addressVos.size() > 0) {
                for (int i = 0; i < addressVos.size(); i++) {
                    if ("1".equals(addressVos.get(i).getIs_moren())) {
                        addressVo = addressVos.get(i);
                    }
                }
                if (addressVo == null) {
                    addressVo = addressVos.get(0);
                }
                mBinding.tvName.setText(addressVo.getName());
                mBinding.tvAddress.setText(addressVo.getAddress());
                mBinding.tvPhone.setText(addressVo.getPhone());
            } else {
                mBinding.tvAddress.setText("请先编辑发货地址");
            }
        });


        mViewModel.orederVoMediatorLiveData.observe(this, payOrederVo -> {
            if (payOrederVo != null) {
                if (payOrederVo.goodsid != null && payOrederVo.goodsid.size() > 0) {
                    for (int i = 0; i < innerVm.mItems.size(); i++) {
                        for (int j = 0; j < ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.size(); j++) {
                            ShoppingCarVoV3.CardInfo cardInfo = ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.get(j);
                            if (payOrederVo.goodsid.contains(cardInfo.goodsid)) {
                                cardInfo.setKucunNoHave(true);
                            }
                        }
                    }
                } else {
                    ARouter.getInstance().build(AppRouter.ORDER_PAY).withSerializable("payOrederVo", payOrederVo).navigation();
                }
            }
        });

        // 提交订单
        mBinding.ordermake.setOnClickListener(v -> {

            if (addressVo == null) {
                ToastUtils.showShort("请先选择地址");
                return;
            }

            boolean isHaveGoods = false;
            for (int i = 0; i < innerVm.mItems.size(); i++) {
                for (int j = 0; j < ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.size(); j++) {
                    ShoppingCarVoV3.CardInfo cardInfo = ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.get(j);
                    if (cardInfo.getNum() > 0) {
                        isHaveGoods = true;
                    }
                }
            }

            if (!isHaveGoods) {
                ToastUtils.showShort("暂无商品购买信息");
                return;
            }

            HashMap<String, String> param = new HashMap<>();
            param.put("memberid", CacheUtils.getUser().uid);
            param.put("uuid", CacheUtils.getUser().uuid);
            param.put("addressid", addressVo.getId());

            ArrayList<SubmitOrderShopVo> submitOrderShopVos = new ArrayList<>();
            for (int i = 0; i < innerVm.mItems.size(); i++) {
                SubmitOrderShopVo submitOrderShopVo = new SubmitOrderShopVo();
                submitOrderShopVo.circleId = ((ShoppingCarVoV3) innerVm.mItems.get(i)).circleid;
//                submitOrderShopVo.circleName = ((ShoppingCarVoV3) innerVm.mItems.get(i)).circleName;
                submitOrderShopVo.remark = ((ShoppingCarVoV3) innerVm.mItems.get(i)).mark;
                for (int j = 0; j < ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.size(); j++) {
                    SubmitOrderGoodsVo submitOrderGoodsVo = new SubmitOrderGoodsVo();
                    submitOrderGoodsVo.dynamicid = ((ShoppingCarVoV3.CardInfo) ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.get(j)).id;
                    submitOrderGoodsVo.goodsid = ((ShoppingCarVoV3.CardInfo) ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.get(j)).goodsid;
                    submitOrderGoodsVo.goodsNum = String.valueOf(((ShoppingCarVoV3.CardInfo) ((ShoppingCarVoV3) innerVm.mItems.get(i)).info.get(j)).num);
                    submitOrderShopVo.goodsarr.add(submitOrderGoodsVo);
                }
                submitOrderShopVos.add(submitOrderShopVo);
            }
            param.put("orderArr", GsonUtils.toJson(submitOrderShopVos));
            param.put("payment", "2");
            mViewModel.orderMaker(param);
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10011) {
                addressVo = (AddressVo) data.getSerializableExtra("AddressVo");
                mBinding.tvName.setText(addressVo.getName());
                mBinding.tvAddress.setText(addressVo.getAddress());
                mBinding.tvPhone.setText(addressVo.getPhone());
            }
        }
    }
}
