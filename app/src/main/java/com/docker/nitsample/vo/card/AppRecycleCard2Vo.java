package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.ui.detail.index.CircleConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.ItemVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleCard2Vo extends BaseCardVo<String> {

    public AppRecycleCard2Vo(int style, int position) {
        super(style, position);
        maxSupport = 1;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    public void onChildClick(ItemVo itemVo) {


        switch (itemVo.getName()) {
            case "桃源志":
                CircleConfig circleConfig = new CircleConfig();
                circleConfig.circleid = "255";
                circleConfig.utid = "62fe4a4647e39d823677c40fa8fff5f1";
                circleConfig.circleType = "1";
                circleConfig.Temple = 2;
                circleConfig.isNeedToobar = false;
                circleConfig.extens.put("title", "桃源志");
                ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
                        .withSerializable("circleConfig", circleConfig)
                        .navigation();
                break;
            case "分部说":
                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_PRO_MUTIPARTINDEX).navigation();

                break;
            case "沙龙·活动":
                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_PRO_ACTIVEINDEX).navigation();
                break;
            case "积分榜":
                ARouter.getInstance().build(AppRouter.POINT_SORT_INDEX).navigation();
                break;
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_card;
    }


    public transient ItemBinding<ItemVo> itemImgBinding = ItemBinding.<ItemVo>of(BR.item,
            R.layout.app_card_img_inner).bindExtra(BR.parent, this); // 单一view 有点击事件;
//


    public ItemBinding<ItemVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<ItemVo>of(BR.item,
                    R.layout.app_card_img_inner);
        }
        return itemImgBinding;
    }

    private ObservableField<List<ItemVo>> InnerResource = new ObservableField<>();

    public void setInnerResource(ObservableField<List<ItemVo>> innerResource) {
        InnerResource = innerResource;
    }


    public ObservableField<List<ItemVo>> getInnerResource() {
        ItemVo itemVo = new ItemVo(R.mipmap.dzgl_true, "桃源志");
        ItemVo itemVo1 = new ItemVo(R.mipmap.dzgl_true, "分部说");
        ItemVo itemVo2 = new ItemVo(R.mipmap.dzgl_true, "积分榜");
        ItemVo itemVo3 = new ItemVo((R.mipmap.dzgl_true), "股东汇");
        ItemVo itemVo4 = new ItemVo((R.mipmap.dzgl_true), "购酒·优选");
        ItemVo itemVo5 = new ItemVo((R.mipmap.dzgl_true), "沙龙·活动");
        ItemVo itemVo6 = new ItemVo((R.mipmap.dzgl_true), "积分·收益");
        ItemVo itemVo7 = new ItemVo((R.mipmap.dzgl_true), "邀人·推广");
        ArrayList<ItemVo> arrayList = new ArrayList();
        arrayList.add(itemVo);
        arrayList.add(itemVo1);
        arrayList.add(itemVo2);
        arrayList.add(itemVo3);
        arrayList.add(itemVo4);
        arrayList.add(itemVo5);
        arrayList.add(itemVo6);
        arrayList.add(itemVo7);
        InnerResource.set(arrayList);
        return InnerResource;
    }
}
