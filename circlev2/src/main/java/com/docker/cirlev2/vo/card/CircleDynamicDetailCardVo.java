package com.docker.cirlev2.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.vo.card.BaseCardVo;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CircleDynamicDetailCardVo extends BaseCardVo {


    public ServiceDataBean serviceDataBean;

    // 普通 动态 问答

    public CircleDynamicDetailCardVo(int style, int position) {
        super(style, position);
        maxSupport = 2;
        mVmPath = "com.docker.cirlev2.vm.card.CircleDynamicDetailCardVm";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        int lay = R.layout.circlev2_item_dynamic_detail_card;
        switch (serviceDataBean.getType()) {

            case "dynamic": //dynamic

                break;

            case "answer":

                break;
        }
        return lay;
    }


    public void setServerData(ServiceDataBean serviceDataBean) {
        this.serviceDataBean = serviceDataBean;
    }


    public transient ItemBinding<ServiceDataBean.ResourceBean> itemImgBinding = ItemBinding.<ServiceDataBean.ResourceBean>of(BR.item,
            R.layout.circlev2_card_img_inner); // 单一view 有点击事件;
//


    public ItemBinding<ServiceDataBean.ResourceBean> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<ServiceDataBean.ResourceBean>of(BR.item,
                    R.layout.circlev2_card_img_inner);
        }
        return itemImgBinding;
    }

    private ObservableList<ServiceDataBean.ResourceBean> InnerResource = new ObservableArrayList<>();

    public ObservableList<ServiceDataBean.ResourceBean> getInnerResource() {
        if (serviceDataBean != null && serviceDataBean.getExtData().getResource() != null && serviceDataBean.getExtData().getResource().size() > 0) {
            for (int i = 0; i < serviceDataBean.getExtData().getResource().size(); i++) {
                serviceDataBean.getExtData().getResource().get(i).setParentid(serviceDataBean.getDynamicid());
            }
            InnerResource.addAll(serviceDataBean.getExtData().getResource());
        }
        return InnerResource;
    }
}
