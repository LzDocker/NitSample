package com.docker.cirlev2.vo.card;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.ItemVo;
import com.docker.common.common.vo.card.BaseCardVo;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ProRecycleCardVo extends BaseCardVo<String> {

    public ProRecycleCardVo(int style, int position) {
        super(style, position);
        maxSupport = 2;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.tv_use) { // 开通
            //
            Log.d("sss", "onItemClick: ===========开通===============");
            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_PRO_INFO).navigation();
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_pro_recycle_card;
    }


    public transient ItemBinding<ItemVo> itemImgBinding;

    public ItemBinding<ItemVo> getItemImgBinding() {
        if (style == 0) {
            itemImgBinding = ItemBinding.<ItemVo>of(BR.item,
                    R.layout.circlev2_pro_item_inner).bindExtra(BR.parent, this);
        } else {
            itemImgBinding = ItemBinding.<ItemVo>of(BR.item,
                    R.layout.circlev2_pro_item_use_inner).bindExtra(BR.parent, this);
        }

        Log.d("sss", "getItemImgBinding: ======style==========" + style);
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
