package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;

import com.bfhd.circle.BR;
import com.bfhd.circle.vo.CommentVo;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.OptimizationVo;
import com.docker.nitsample.vo.SampleItemVo;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OptimizationModel extends NitCommonContainerViewModel {


    @Inject
    public OptimizationModel() {
    }

    public ObservableField<List<OptimizationVo.BannerVo>> bannerVos = new ObservableField<>();

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        List<OptimizationVo> optimizationVoList = new ArrayList<>();

        OptimizationVo optimizationVo = new OptimizationVo(0, 0);

        OptimizationVo.BannerVo bannerVo = new OptimizationVo.BannerVo();

        ObservableField<List<OptimizationVo.BannerVo>> observablebannerVoList = new ObservableField<>();
        List<OptimizationVo.BannerVo> bannerVoList = new ArrayList<>();
        bannerVoList.add(bannerVo);
        bannerVoList.add(bannerVo);
        bannerVoList.add(bannerVo);
        observablebannerVoList.set(bannerVoList);
        optimizationVo.setBannerVo(observablebannerVoList);

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

        List<ServiceDataBean.ResourceBean> resourceBeanList  = new ArrayList<>();
        resourceBeanList.add(resourceBean);
        serviceDataBean.getExtData().setResource(resourceBeanList);


        serviceDataBeanList.add(serviceDataBean);
        serviceDataBeanList.add(serviceDataBean);
        serviceDataBeanList.add(serviceDataBean);
        serviceDataBeanList.add(serviceDataBean);
        ObservableField<List<ServiceDataBean>> observableServiceDataBeanList = new ObservableField<>();
        observableServiceDataBeanList.set(serviceDataBeanList);
        optimizationVo.setServiceDataBean(observableServiceDataBeanList);
        optimizationVoList.add(optimizationVo);
        optimizationVoList.add(optimizationVo);


        mItems.addAll(optimizationVoList);
    }

    // 公社优选 列表样式
    public final ItemBinding<CommentVo> commentBinding = ItemBinding.<CommentVo>of(BR.item, com.bfhd.circle.R.layout.circle_item_comment)
            .bindExtra(BR.viewmodel, this);

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }
}
