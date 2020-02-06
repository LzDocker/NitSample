package com.docker.apps.order.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderMakerActivityBinding;
import com.docker.apps.order.vm.OrderMakerViewModel;
import com.docker.apps.order.vo.AddressVo;
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
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.empty.EmptyStatus;

import java.math.BigDecimal;
import java.util.ArrayList;

/*
 * 订单详情
 **/
@Route(path = AppRouter.ORDER_DETAIL)
public class OrderDetailActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderMakerActivityBinding> {

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
        return R.layout.pro_order_detail_activity;
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
        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(OrderDetailActivity.this.getSupportFragmentManager(), R.id.goods_frame, commonListOptions);

        mViewModel.fetchAddress();
    }

    @Override
    public void initView() {
        mBinding.tvAddress.setOnClickListener(v -> {
            if (addressVo == null) {
                ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_MANAGER).withString("type", "1").navigation(OrderDetailActivity.this, 10011);
            } else {
                ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_MANAGER).withString("type", "2").navigation(OrderDetailActivity.this, 10011);
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


        // 提交订单
        mBinding.ordermake.setOnClickListener(v -> {

            ARouter.getInstance().build(AppRouter.ORDER_PAY).navigation();

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
