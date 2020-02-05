package com.docker.nitsample.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.docker.cirlev2.vo.entity.ServerGoodsDataBean;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.BR;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.api.SampleService;
import com.docker.nitsample.vo.BannerEntityVo;
import com.docker.nitsample.vo.GoodsBannerVo;

import java.util.ArrayList;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class AppGoodsOptimizationCardVo extends BaseCardVo {


    // 轮播图
    public ObservableList<BannerEntityVo> goodsBannerOb = new ObservableArrayList<>();

    public ObservableList<ServerGoodsDataBean> goodsListOb = new ObservableArrayList<>();

    public String tabClass;

    /*
     * banner 点击事件
     * */
    public ReplyCommandParam replyCommandParam = (ReplyCommandParam) o -> {
        if ("".equals(((BannerEntityVo) o).dynamicid) || "0".equals(((BannerEntityVo) o).dynamicid) && !TextUtils.isEmpty(((BannerEntityVo) o).http)) {
            ARouter.getInstance().build(AppRouter.COMMONH5)
                    .withString("title", ((BannerEntityVo) o).title)
                    .withString("weburl", ((BannerEntityVo) o).http).navigation();
        } else {
            StaDetailParam staDetailParam = new StaDetailParam();
            staDetailParam.dynamicId = (((BannerEntityVo) o)).dynamicid;
            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
        }
    };

    public AppGoodsOptimizationCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
        mVmPath = "com.docker.nitsample.vm.card.OptimizationCardViewModel";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.app_goods_optimization_card;
    }

    public void setBannerList(ArrayList<BannerEntityVo> bannerEntityVos) {
        goodsBannerOb.clear();
        goodsBannerOb.addAll(bannerEntityVos);
    }

    public void setRecycleGoodsList(ArrayList<com.docker.cirlev2.vo.entity.ServerGoodsDataBean> serviceDataBeans) {
        goodsListOb.clear();
        goodsListOb.addAll(serviceDataBeans);
    }


    // 多类型条目适配
    public OnItemBind<ServerGoodsDataBean> mutipartItemsBinding = (ItemBinding itemBinding, int position, ServerGoodsDataBean item) -> {
        if (item instanceof BaseSampleItem) {
            ((ServerGoodsDataBean) item).index = position;
        }
        itemBinding.set(BR.item, item.getItemLayout());
    };
    // itembinding
    public ItemBinding<ServerGoodsDataBean> itemBinding = ItemBinding.of(mutipartItemsBinding);

}
