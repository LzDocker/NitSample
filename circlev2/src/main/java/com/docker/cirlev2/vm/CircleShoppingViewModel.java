package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.ui.comment.CommentDialogFragment;
import com.docker.cirlev2.util.AudioPlayerUtils;
import com.docker.cirlev2.vo.entity.CommentRstVo;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaPersionDetail;
import com.docker.cirlev2.vo.vo.ShoppingCarItemVo;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.common.BR;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.wx.goodview.GoodView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CircleShoppingViewModel extends NitCommonContainerViewModel {


    @Inject
    CircleApiService circleApiService;


    @Inject
    public CircleShoppingViewModel() {

    }


    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        List<ShoppingCarVo> shoppingCarVoList = new ArrayList<>();

        ShoppingCarVo shoppingCarVo = new ShoppingCarVo(0, 0);
        shoppingCarVo.setTitle("桃源公社");
        shoppingCarVo.setId("111");

        List<ServiceDataBean> serviceDataBeanList = new ArrayList<>();
        ServiceDataBean serviceDataBean = new ServiceDataBean();
        serviceDataBean.setDynamicid("111");
        serviceDataBean.setIsCollect(0);


        ServiceDataBean.ExtDataBean extDataBean = new ServiceDataBean.ExtDataBean();
        extDataBean.setContent("测试数据");
        extDataBean.setName("测试数据");
        extDataBean.setPrice("100");
        extDataBean.setPoint("20");
        extDataBean.setType("goods");
        serviceDataBean.setExtData(extDataBean);

        ServiceDataBean.ResourceBean resourceBean = new ServiceDataBean.ResourceBean();
        resourceBean.setImg("/static/var/upload/img20191218/upload/image/1576670097800_720x682.png");
        resourceBean.setParentid("0");
        resourceBean.setSort("1");
        resourceBean.setT(1);
        resourceBean.setUrl("/static/var/upload/img20191218/upload/image/1576670097800_720x682.png");

        List<ServiceDataBean.ResourceBean> resourceBeanList = new ArrayList<>();
        resourceBeanList.add(resourceBean);
        serviceDataBean.getExtData().setResource(resourceBeanList);

        serviceDataBeanList.add(serviceDataBean);
        ObservableField<List<ServiceDataBean>> observableServiceDataBeanList = new ObservableField<>();
        observableServiceDataBeanList.set(serviceDataBeanList);
        shoppingCarVo.setServiceDataBean(observableServiceDataBeanList);
        shoppingCarVo.setServiceDataBean(observableServiceDataBeanList);
        shoppingCarVoList.add(shoppingCarVo);


        mItems.addAll(shoppingCarVoList);
    }

    public void ItemClick(ShoppingCarVo item, View view) {
//        String id = item.getId();
        Log.i("gjw", "ItemClick: 1111");
        String id = "111";
        for (int i = 0; i < mItems.size(); i++) {
            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
            String innerId = shoppingCarVo.getId();
            if (id.equals(innerId)) {
                if (shoppingCarVo.isSelect()) {
                    shoppingCarVo.setSelect(false);
                } else {
                    shoppingCarVo.setSelect(true);
                }
                shoppingCarVo.notifyChange();
                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
                for (int j = 0; j < serviceDataBeans.size(); j++) {
                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
                    if (serviceDataBean.isSelect()) {
                        serviceDataBean.setSelect(false);
                    } else {
                        serviceDataBean.setSelect(true);
                    }
                    serviceDataBean.notifyChange();
                }
            }
        }

    }

    public void allSelectClick(ShoppingCarVo item, View view) {
        String id = "111";
        for (int i = 0; i < mItems.size(); i++) {
            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
            String innerId = shoppingCarVo.getId();
            if (shoppingCarVo.isSelect()) {
                shoppingCarVo.setSelect(false);
            } else {
                shoppingCarVo.setSelect(true);
            }
            shoppingCarVo.notifyChange();
            ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
            List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
            for (int j = 0; j < serviceDataBeans.size(); j++) {
                ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
                if (serviceDataBean.isSelect()) {
                    serviceDataBean.setSelect(false);
                } else {
                    serviceDataBean.setSelect(true);
                }
                serviceDataBean.notifyChange();
            }

        }

    }

    public void reduceNumClick(ServiceDataBean item, View view) {
        String id = "111";
        for (int i = 0; i < mItems.size(); i++) {
            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
            String innerId = shoppingCarVo.getId();
            if (id.equals(innerId)) {
                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
                for (int j = 0; j < serviceDataBeans.size(); j++) {
                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
                    String dynamicid = serviceDataBean.getDynamicid();
                    if (dynamicid.equals(item.getDynamicid())) {
                        if (item.getNum() > 0) {
                            item.setNum(item.getNum() - 1);
                            item.notifyPropertyChanged(BR.num);
                        }
                    }
                }
            }
        }

    }

    public void addNumClick(ServiceDataBean item, View view) {
        String id = "111";
        for (int i = 0; i < mItems.size(); i++) {
            ShoppingCarVo shoppingCarVo = (ShoppingCarVo) mItems.get(i);
            String innerId = shoppingCarVo.getId();
            if (id.equals(innerId)) {
                ObservableField<List<ServiceDataBean>> serviceDataBeanList = shoppingCarVo.getServiceDataBean();
                List<ServiceDataBean> serviceDataBeans = serviceDataBeanList.get();
                for (int j = 0; j < serviceDataBeans.size(); j++) {
                    ServiceDataBean serviceDataBean = serviceDataBeans.get(j);
                    String dynamicid = serviceDataBean.getDynamicid();
                    if (dynamicid.equals(item.getDynamicid())) {
                        item.setNum(item.getNum() + 1);
                        item.notifyPropertyChanged(BR.num);
                    }
                }
            }
        }


    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        return null;
    }


}
