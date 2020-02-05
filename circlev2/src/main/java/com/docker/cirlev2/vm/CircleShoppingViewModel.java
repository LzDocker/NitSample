package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.view.View;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarItemVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVoV3;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
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


    @Inject
    CircleApiService circleApiService;


    @Inject
    public CircleShoppingViewModel() {

    }


//    @Override
//    public void loadData() {
//        mEmptycommand.set(EmptyStatus.BdHiden);
//        List<ShoppingCarVo> shoppingCarVoList = new ArrayList<>();
//
//        ShoppingCarVo shoppingCarVo = new ShoppingCarVo(0, 0);
//        shoppingCarVo.setTitle("桃源公社");
//        shoppingCarVo.setId("111");
//
//        List<ServiceDataBean> serviceDataBeanList = new ArrayList<>();
//        ServiceDataBean serviceDataBean = new ServiceDataBean();
//        serviceDataBean.setDynamicid("111");
//        serviceDataBean.setIsCollect(0);
//
//
//        ServiceDataBean.ExtDataBean extDataBean = new ServiceDataBean.ExtDataBean();
//        extDataBean.setContent("测试数据");
//        extDataBean.setName("测试数据");
//        extDataBean.setPrice("100");
//        extDataBean.setPoint("20");
//        extDataBean.setType("goods");
//        serviceDataBean.setExtData(extDataBean);
//
//        ServiceDataBean.ResourceBean resourceBean = new ServiceDataBean.ResourceBean();
//        resourceBean.setImg("/static/var/upload/img20191218/upload/image/1576670097800_720x682.png");
//        resourceBean.setParentid("0");
//        resourceBean.setSort("1");
//        resourceBean.setT(1);
//        resourceBean.setUrl("/static/var/upload/img20191218/upload/image/1576670097800_720x682.png");
//
//        List<ServiceDataBean.ResourceBean> resourceBeanList = new ArrayList<>();
//        resourceBeanList.add(resourceBean);
//        serviceDataBean.getExtData().setResource(resourceBeanList);
//
//        serviceDataBeanList.add(serviceDataBean);
//        ObservableField<List<ServiceDataBean>> observableServiceDataBeanList = new ObservableField<>();
//        observableServiceDataBeanList.set(serviceDataBeanList);
////        shoppingCarVo.setServiceDataBean(observableServiceDataBeanList);
////        shoppingCarVo.setServiceDataBean(observableServiceDataBeanList);
//        shoppingCarVoList.add(shoppingCarVo);
//
//
//        mItems.addAll(shoppingCarVoList);
//    }

    public void ItemClick(ShoppingCarVo item, View view) {
//        String id = item.getId();
//        Log.i("gjw", "ItemClick: 1111");
//        String id = "111";
//        for (int i = 0; i < mItems.size(); i++) {
//            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
//            String innerId = shoppingCarVo.getId();
//            if (id.equals(innerId)) {
//                if (shoppingCarVo.isSelect()) {
//                    shoppingCarVo.setSelect(false);
//                } else {
//                    shoppingCarVo.setSelect(true);
//                }
//                shoppingCarVo.notifyChange();
//                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
//                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
//                for (int j = 0; j < serviceDataBeans.size(); j++) {
//                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
//                    if (serviceDataBean.isSelect()) {
//                        serviceDataBean.setSelect(false);
//                    } else {
//                        serviceDataBean.setSelect(true);
//                    }
//                    serviceDataBean.notifyChange();
//                }
//            }
//        }

    }

    public void allSelectClick(ShoppingCarVo item, View view) {
//        String id = "111";
//        for (int i = 0; i < mItems.size(); i++) {
//            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
//            String innerId = shoppingCarVo.getId();
//            if (shoppingCarVo.isSelect()) {
//                shoppingCarVo.setSelect(false);
//            } else {
//                shoppingCarVo.setSelect(true);
//            }
//            shoppingCarVo.notifyChange();
//            ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
//            List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
//            for (int j = 0; j < serviceDataBeans.size(); j++) {
//                ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
//                if (serviceDataBean.isSelect()) {
//                    serviceDataBean.setSelect(false);
//                } else {
//                    serviceDataBean.setSelect(true);
//                }
//                serviceDataBean.notifyChange();
//            }
//
//        }

    }

    public void reduceNumClick(ServiceDataBean item, View view) {
        String id = "111";
//        for (int i = 0; i < mItems.size(); i++) {
//            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
//            String innerId = shoppingCarVo.getId();
//            if (id.equals(innerId)) {
//                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
//                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
//                for (int j = 0; j < serviceDataBeans.size(); j++) {
//                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
//                    String dynamicid = serviceDataBean.getDynamicid();
//                    if (dynamicid.equals(item.getDynamicid())) {
//                        if (item.getNum() > 0) {
//                            item.setNum(item.getNum() - 1);
//                            item.notifyPropertyChanged(BR.num);
//                        }
//                    }
//                }
//            }
//        }

    }

    public void addNumClick(ServiceDataBean item, View view) {
//        String id = "111";
//        for (int i = 0; i < mItems.size(); i++) {
//            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
//            String innerId = shoppingCarVo.getId();
//            if (id.equals(innerId)) {
//                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
//                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
//                for (int j = 0; j < serviceDataBeans.size(); j++) {
//                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
//                    String dynamicid = serviceDataBean.getDynamicid();
//                    if (dynamicid.equals(item.getDynamicid())) {
//                        item.setNum(item.getNum() + 1);
//                        item.notifyPropertyChanged(BR.num);
//                    }
//                }
//            }
//        }


    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return circleApiService.getGoodsCartListData(param);
    }

    public final MediatorLiveData<String> mTotalMoney = new MediatorLiveData<>();

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
        if (resource.data != null && ((List<ShoppingCarVoV3>) resource.data).size() > 0) {
            processTotalMoney((List<ShoppingCarVoV3>) resource.data);
        } else {
            mTotalMoney.setValue(String.valueOf(0));
        }
    }

    private void processTotalMoney(List<ShoppingCarVoV3> shoppingCarVoV3s) {
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < shoppingCarVoV3s.size(); i++) {
            for (int j = 0; j < ((shoppingCarVoV3s).get(i).info).size(); j++) {
                ShoppingCarVoV3.CardInfo cardInfo = ((shoppingCarVoV3s).get(i)).info.get(j);
                BigDecimal bigprice = new BigDecimal(cardInfo.price);
                BigDecimal bignum = new BigDecimal(cardInfo.num);
                total = total.add(bigprice.multiply(bignum).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        mTotalMoney.setValue(String.valueOf(total));
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
