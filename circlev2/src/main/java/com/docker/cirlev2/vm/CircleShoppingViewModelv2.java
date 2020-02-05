package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarItemVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleShoppingViewModelv2 extends NitCommonContainerViewModel {


    @Inject
    CircleApiService circleApiService;


    @Inject
    public CircleShoppingViewModelv2() {

    }


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
        return circleApiService.shoppingCarList(param);
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
}
