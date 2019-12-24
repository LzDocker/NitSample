package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.CarRvHorizontalVo;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public abstract class AppBaseRecycleHorizontalCardVo<T> extends BaseCardVo<String> {

    public AppBaseRecycleHorizontalCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {


    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_horizontal_card;
    }


    public transient ItemBinding<CarRvHorizontalVo> itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner); // 单一view 有点击事件;


    public ItemBinding<CarRvHorizontalVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
                    R.layout.app_card_horizontal_img_inner);
        }
        return itemImgBinding;
    }

    private ObservableField<List<CarRvHorizontalVo>> InnerResource = new ObservableField<>();

    public void setInnerResource(ObservableField<List<CarRvHorizontalVo>> innerResource) {
        InnerResource = innerResource;
    }


    public ObservableField<List<CarRvHorizontalVo>> getInnerResource() {
        ArrayList<CarRvHorizontalVo> arrayList = new ArrayList();
        CarRvHorizontalVo carRvHorizontalVo = new CarRvHorizontalVo("", "2019桃源公社黔酒封坛大典圆满礼成！封一坛老2019桃源公社黔酒封坛大典圆满礼成！封一坛老", "北京海淀", "活动日期：11/5", 1);
        arrayList.add(carRvHorizontalVo);
        arrayList.add(carRvHorizontalVo);
        arrayList.add(carRvHorizontalVo);
        arrayList.add(carRvHorizontalVo);
        arrayList.add(carRvHorizontalVo);
        arrayList.add(carRvHorizontalVo);

        InnerResource.set(arrayList);
        return InnerResource;
    }


}
