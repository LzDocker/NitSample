package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.vo.ShoppingCarItemVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVoV3;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleShoppingViewModel extends NitCommonContainerViewModel {


    /*
     * 0 默认
     * 1 从购物车到达 填写订单界面
     * 2 单个商品购买
     * */
    public int flag = 0;

    @Inject
    CircleApiService circleApiService;


    @Inject
    public CircleShoppingViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        if (param.containsKey("falg")) {
            flag = 1;
            if ("1".equals(param.get("falg"))) {
                return circleApiService.getGoodsCartListData(param);
            } else if ("3".equals(param.get("falg"))) {
                String orderid = (String) param.get("orderid");
                param.clear();
                param.put("memberid", CacheUtils.getUser().uid);
                param.put("orderid", orderid);
                return circleApiService.orderbuyAgain(param);
            } else if ("4".equals(param.get("flag"))) {
                param.clear();
                mEmptycommand.set(EmptyStatus.BdHiden);
                return null;
            } else {
                mEmptycommand.set(EmptyStatus.BdHiden);
                return null;
            }
        } else {
            return circleApiService.getGoodsCartListData(param);
        }
    }

    public final MediatorLiveData<String> mTotalMoney = new MediatorLiveData<>();
    public final MediatorLiveData<String> mTotalTransMoney = new MediatorLiveData<>();

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
        if (resource.data != null && ((List<ShoppingCarVoV3>) resource.data).size() > 0) {
            processTotalMoney((List<ShoppingCarVoV3>) resource.data);
            processTotalTransMoney((List<ShoppingCarVoV3>) resource.data);
        } else {
            mTotalMoney.setValue(String.valueOf(0));
        }
    }

    public void processTotalMoney(List<ShoppingCarVoV3> shoppingCarVoV3s) {

        Log.d("sss", "processTotalMoney: =======flag=======" + flag);
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < shoppingCarVoV3s.size(); i++) {
            for (int j = 0; j < ((shoppingCarVoV3s).get(i).info).size(); j++) {
                ShoppingCarVoV3.CardInfo cardInfo = ((shoppingCarVoV3s).get(i)).info.get(j);
                if (flag == 0) {
                    if (((shoppingCarVoV3s).get(i)).info.get(j).getIsSelect()) {
                        BigDecimal bigprice = new BigDecimal(cardInfo.price);
                        BigDecimal bignum = new BigDecimal(cardInfo.num);
                        total = total.add(bigprice.multiply(bignum).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                } else {
                    BigDecimal bigprice = new BigDecimal(cardInfo.price);
                    BigDecimal bignum = new BigDecimal(cardInfo.num);
                    total = total.add(bigprice.multiply(bignum).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        mTotalMoney.setValue(String.valueOf(total));
    }

    public void processTotalTransMoney(List<ShoppingCarVoV3> shoppingCarVoV3s) {
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < shoppingCarVoV3s.size(); i++) {
            for (int j = 0; j < ((shoppingCarVoV3s).get(i).info).size(); j++) {
                ShoppingCarVoV3.CardInfo cardInfo = ((shoppingCarVoV3s).get(i)).info.get(j);
                BigDecimal bigtrans;
                if ("0.00".equals(cardInfo.transMoney) || TextUtils.isEmpty(cardInfo.transMoney)) {
                    bigtrans = new BigDecimal(0);
                } else {
                    bigtrans = new BigDecimal(cardInfo.transMoney);
                }
                BigDecimal bignum = new BigDecimal(cardInfo.num);
                total = total.add(bigtrans.multiply(bignum).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        mTotalTransMoney.setValue(String.valueOf(total));
    }


    public MediatorLiveData<List<ShoppingCarItemVo>> listMediatorLiveData = new MediatorLiveData<>();

    public void fetchShopCartData(HashMap<String, String> param) {
        listMediatorLiveData.addSource(RequestServer(circleApiService.getGoodsTrueData(param)), new NitNetBoundObserver<List<ShoppingCarItemVo>>(new NitBoundCallback<List<ShoppingCarItemVo>>() {
            @Override
            public void onComplete(Resource<List<ShoppingCarItemVo>> resource) {
                super.onComplete(resource);
                listMediatorLiveData.setValue(resource.data);
            }
        }));

    }

    public final MediatorLiveData<List<ShoppingCarItemVo>> mCartListDataLv = new MediatorLiveData<>();

    public void getGoodsCartListData(HashMap<String, String> param) {
        mCartListDataLv.addSource(RequestServer(circleApiService.getGoodsCartListData(param)), new NitNetBoundObserver<List<ShoppingCarItemVo>>(new NitBoundCallback<List<ShoppingCarItemVo>>() {
            @Override
            public void onComplete(Resource<List<ShoppingCarItemVo>> resource) {
                super.onComplete(resource);
                mCartListDataLv.setValue(resource.data);
            }
        }));

    }


    public final MediatorLiveData<String> mCartAddLv = new MediatorLiveData<>();

    public void requestServerCart(String option, ShoppingCarVoV3.CardInfo cardInfo, View view) {

        cardInfo.setKucunNoHave(false);
        if ("2".equals(option)) {
            if (cardInfo.num <= 0) {
                ToastUtils.showShort("已减至最低购买数量");
                return;
            }
        }
        if (flag != 0) {  // 填写订单界面不计入购物车数量
            switch (option) {
                case "1":
                    cardInfo.setNum(cardInfo.num + 1);
                    break;
                case "2":
                    cardInfo.setNum(cardInfo.num - 1);
                    break;
            }
            processTotalMoney(mItems);
            processTotalTransMoney(mItems);
            return;
        }

        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("goodsid", cardInfo.goodsid);
        param.put("operation", option);
        mCartAddLv.addSource(RequestServer(circleApiService.shoppingCarAdd(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                switch (option) {
                    case "1":
                        cardInfo.setNum(cardInfo.num + 1);
                        break;
                    case "2":
                        cardInfo.setNum(cardInfo.num - 1);
                        break;
                }
                processTotalMoney(mItems);
                processTotalTransMoney(mItems);
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onBusinessError(resource);

            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("网络问题请重试");
            }
        }));
    }

    public void selectCheck(ShoppingCarVoV3 shoppingCarVoV3, View view) {
        shoppingCarVoV3.setIsSelect(!shoppingCarVoV3.getIsSelect());
        for (int i = 0; i < shoppingCarVoV3.info.size(); i++) {
            shoppingCarVoV3.info.get(i).setIsSelect(shoppingCarVoV3.getIsSelect());
        }
        processAllCheck();
        processTotalMoney(mItems);
    }

    public void childCheck(ShoppingCarVoV3.CardInfo info, ShoppingCarVoV3 shoppingCarVoV3, View view) {
        info.setIsSelect(!info.getIsSelect());
        boolean isCheck = false;
        for (int i = 0; i < shoppingCarVoV3.info.size(); i++) {
            if (shoppingCarVoV3.info.get(i).getIsSelect()) {
                isCheck = true;
            }
        }
        shoppingCarVoV3.setIsSelect(isCheck);
        if (info.getIsSelect()) {
            boolean isallcheck = true;
            for (int i = 0; i < mItems.size(); i++) {
                for (int j = 0; j < ((ShoppingCarVoV3) mItems.get(i)).info.size(); j++) {
                    if (!((ShoppingCarVoV3) mItems.get(i)).info.get(j).getIsSelect()) {
                        isallcheck = false;
                        break;
                    }
                }
            }
            mIsAllCheckLv.setValue(isallcheck);
        } else {
            mIsAllCheckLv.setValue(false);
        }
        processTotalMoney(mItems);
    }

    public final MediatorLiveData<Boolean> mIsAllCheckLv = new MediatorLiveData<>();

    private void processAllCheck() {
        boolean isNoAllcheck = false;
        for (int i = 0; i < mItems.size(); i++) {
            if (!((ShoppingCarVoV3) mItems.get(i)).getIsSelect()) {
                isNoAllcheck = true;
            }
        }
        mIsAllCheckLv.setValue(!isNoAllcheck);
    }


    public final MediatorLiveData<String> mCartDelLv = new MediatorLiveData<>();

    public void shoppingCarDel() {
        HashMap<String, String> param = new HashMap<>();
        List<ShoppingCarVoV3.CardInfo> delcards = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < mItems.size(); i++) {
            for (int j = 0; j < ((ShoppingCarVoV3) mItems.get(i)).info.size(); j++) {
                ShoppingCarVoV3.CardInfo cardInfo = ((ShoppingCarVoV3) mItems.get(i)).info.get(j);
                if (cardInfo.getIsSelect()) {
                    delcards.add(cardInfo);
                    stringBuilder = stringBuilder.append(cardInfo.id).append(",");
                }
            }
        }
        if (delcards.size() > 0) {
            param.put("memberid", CacheUtils.getUser().uid);
            param.put("ids", stringBuilder.toString().substring(0, stringBuilder.length() - 1));
        } else {
            ToastUtils.showShort("请先选择要删除的商品");
            return;
        }
        mCartDelLv.addSource(RequestServer(circleApiService.shoppingCarDel(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mPage = 1;
                loadData();
            }
        }));
    }
}
