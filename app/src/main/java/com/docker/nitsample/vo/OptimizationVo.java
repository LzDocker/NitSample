package com.docker.nitsample.vo;

import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OptimizationVo extends BaseCardVo {
    private ObservableField<List<BannerVo>> bannerVo;
    private ObservableField<List<ServiceDataBean>> serviceDataBean;

    public OptimizationVo(int style, int position) {
        super(style, position);

    }

    @Override
    public int getItemLayout() {
        return R.layout.item_optimization;
    }

    public transient ItemBinding<ServiceDataBean> itemBinding = ItemBinding.<ServiceDataBean>of(BR.item,
            com.docker.cirlev2.R.layout.circlev2_item_dynamic_goods);// 单一view 有点击事件;


    public ItemBinding<ServiceDataBean> getItemImgBinding() {
        if (itemBinding == null) {
            itemBinding = ItemBinding.<ServiceDataBean>of(BR.item,
                   com.docker.cirlev2.R.layout.circlev2_item_dynamic_goods);

        }
        return itemBinding;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }


    public ObservableField<List<BannerVo>> getBannerVo() {
        return bannerVo;
    }

    public void setBannerVo(ObservableField<List<BannerVo>> bannerVo) {
        this.bannerVo = bannerVo;
    }

    public ObservableField<List<ServiceDataBean>> getServiceDataBean() {
        return serviceDataBean;
    }

    public void setServiceDataBean(ObservableField<List<ServiceDataBean>> serviceDataBean) {
        this.serviceDataBean = serviceDataBean;
    }


    public static class BannerVo  implements  Serializable{
        public String img = "http://taijistar.oss-cn-beijing.aliyuncs.com/static/var/upload/img20191029/upload/image/1572354265340_536x451.png";
        public String url;
        public String type;
    }
}
