package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ArrayUtils;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleShoppingCarListBinding;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarVoV3;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 *  购物车列表
 * */
@Route(path = AppRouter.CIRCLE_shopping_car)
public class CircleShoppingCarListActivity extends NitCommonActivity<CircleShoppingViewModel, Circlev2ActivityCircleShoppingCarListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    DbCacheUtils dbCacheUtils;

    boolean isAllCheck = false;

    CommentVo commentVo;
    //    NitAbsSampleAdapter adapter;
    private String replay;
    private ServiceDataBean serviceDataBean;
    private RelativeLayout relativeLayout;
    private NitCommonListVm innerVm;

    public static void startMe(Context context, CommentVo commentVo, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleShoppingCarListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentVo", commentVo);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_shopping_car_list;
    }

    @Override
    public CircleShoppingViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleShoppingViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("购物车");
        mBinding.setViewmodel(mViewModel);
        mBinding.setIsAllCheck(isAllCheck);

        TextView tvRight = mToolbar.getTvRight();
        mToolbar.setTvRight("编辑", view -> {
            String rightContent = tvRight.getText().toString();
            if (rightContent.contains("编辑")) {//点击编辑
                tvRight.setText("完成");
                mBinding.tvToOrder.setVisibility(View.GONE);
                mBinding.tvDelete.setVisibility(View.VISIBLE);
                mBinding.linCount.setVisibility(View.GONE);
            } else if (rightContent.contains("完成")) {//点击完成
                tvRight.setText("编辑");
                mBinding.linCount.setVisibility(View.VISIBLE);
                mBinding.tvToOrder.setVisibility(View.VISIBLE);
                mBinding.tvDelete.setVisibility(View.GONE);
            }
        });

//        mBinding.recycle.setAdapter(adapter);
        mBinding.tvToOrder.setOnClickListener(v -> {

            if (innerVm.mItems.size() == 0) {
                ToastUtils.showShort("请先添加商品");
                return;
            }

            if (mBinding.getIsAllCheck()) { // 全选
                ARouter.getInstance().build(AppRouter.ORDER_MAKER).withString("is_ShoppingCar", "1").navigation();
                finish();
                return;
            } else {
                ArrayList<ShoppingCarVoV3> CartSaleList = new ArrayList<>();
                for (int i = 0; i < innerVm.mItems.size(); i++) {
                    if (((ShoppingCarVoV3) innerVm.mItems.get(i)).getIsSelect()) {
                        CartSaleList.add((ShoppingCarVoV3) innerVm.mItems.get(i));
                    }
                }
                if (CartSaleList.size() > 0) {
                    ARouter.getInstance().build(AppRouter.ORDER_MAKER).withString("is_ShoppingCar", "1").withSerializable("CartList", CartSaleList).navigation();
                    finish();
                } else {
                    ToastUtils.showShort("请先选择商品");
                }
            }
        });
        mBinding.tvDelete.setOnClickListener(view -> {
            ((CircleShoppingViewModel) innerVm).shoppingCarDel();
        });
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 1;
        commonListOptions.isActParent = true;
//        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        NitBaseProviderCard.providerCoutainerForFrame(CircleShoppingCarListActivity.this.getSupportFragmentManager(), R.id.frame, commonListOptions);

        mBinding.llAllCheck.setOnClickListener(v -> {
            mBinding.setIsAllCheck(!mBinding.getIsAllCheck());
            isAllCheck = mBinding.getIsAllCheck();
            List<ShoppingCarVoV3> carVoV3s = ((CircleShoppingViewModel) innerVm).mItems;
            for (int i = 0; i < carVoV3s.size(); i++) {
                carVoV3s.get(i).setIsSelect(!isAllCheck);
                selectCheck(carVoV3s.get(i), null);
            }
            ((CircleShoppingViewModel) innerVm).processTotalMoney(innerVm.mItems);
        });
    }

    public void selectCheck(ShoppingCarVoV3 shoppingCarVoV3, View view) {
        shoppingCarVoV3.setIsSelect(!shoppingCarVoV3.getIsSelect());
        for (int i = 0; i < shoppingCarVoV3.info.size(); i++) {
            shoppingCarVoV3.info.get(i).setIsSelect(shoppingCarVoV3.getIsSelect());
        }
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
                mBinding.empty.hide();
                innerVm = commonListVm;
                ((CircleShoppingViewModel) commonListVm).mCartAddLv.observe(nitCommonFragment, s -> {
                });
                ((CircleShoppingViewModel) commonListVm).mIsAllCheckLv.observe(nitCommonFragment, s -> {
                    mBinding.setIsAllCheck(s);
                });
                ((CircleShoppingViewModel) commonListVm).mTotalMoney.observe(nitCommonFragment, s -> {
                    mBinding.tvMoney.setText(s);
                });
                ((CircleShoppingViewModel) commonListVm).mCartDelLv.observe(nitCommonFragment, s -> {
                });


//                mViewModel.mCartListDataLv.observe(CircleShoppingCarListActivity.this, new Observer<List<ShoppingCarItemVo>>() {
//                    @Override
//                    public void onChanged(@Nullable List<ShoppingCarItemVo> shoppingCarItemVos) {
//                        if (shoppingCarItemVos.size() > 0) {
//                            mBinding.empty.hide();
//                            for (int i = 0; i < shoppingCarItemVos.size(); i++) {
//                                ShoppingCarVo shoppingCarItemVo = new ShoppingCarVo(0, 0);
//                                NitBaseProviderCard.providerCard(commonListVm, shoppingCarItemVo, nitCommonFragment);
//                            }
//                        }else {
//                            mBinding.empty.showNodata();
//                        }
//                    }
//                });
//
//                HashMap<String, String> param = new HashMap<>();
//                param.put("memberid", CacheUtils.getUser().uid);
//                mViewModel.getGoodsCartListData(param);


//                dbCacheUtils.loadFromDb("shopcart").observe(CircleShoppingCarListActivity.this, o -> {
//                    if (o != null && ((ArrayList<ShoppingCartDbVo>) o).size() > 0) {
//                        mBinding.empty.hide();
//
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (int i = 0; i < ((ArrayList<ShoppingCartDbVo>) o).size(); i++) {
//                            stringBuilder.append(((ArrayList<ShoppingCartDbVo>) o).get(i).id).append(",");
//                        }
//                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//                        ShoppingCarVo shoppingCarItemVo = new ShoppingCarVo(0, 0);
//                        shoppingCarItemVo.setLocalShopCart((ArrayList<ShoppingCartDbVo>) o);
//                        shoppingCarItemVo.mRepParamMap.put("goodidStr", stringBuilder.toString());
//                        NitBaseProviderCard.providerCard(commonListVm, shoppingCarItemVo, nitCommonFragment);
//                    } else {
//                        mBinding.empty.showNodata();
//                    }
//                });
            }
        };
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


}
