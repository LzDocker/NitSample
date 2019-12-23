package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.CarRvHorizontalVo;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleHorizontalCardVo2 extends BaseCardVo<String> {

    public int managerStyle;
    public LayoutManagerVo managerStyleVo;
    public RecycleTopLayout recycleTopLayout;

    public AppRecycleHorizontalCardVo2(int style, int position, LayoutManagerVo managerStyleVo, RecycleTopLayout recycleTopLayout) {
        super(style, position);
        this.managerStyleVo = managerStyleVo;
        maxSupport = 1;
        this.recycleTopLayout = recycleTopLayout;
    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_horizontal_card2;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {


    }

    public transient ItemBinding<CarRvHorizontalVo> itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner2);// 单一view 有点击事件;


    public ItemBinding<CarRvHorizontalVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
                    R.layout.app_card_horizontal_img_inner2);

        }
        return itemImgBinding;
    }

    private ObservableField<List<CarRvHorizontalVo>> InnerResource = new ObservableField<>();


    public void setInnerResource(ObservableField<List<CarRvHorizontalVo>> innerResource) {
        InnerResource = innerResource;
    }


    public ObservableField<List<CarRvHorizontalVo>> getInnerResource() {
        ArrayList<CarRvHorizontalVo> arrayList = new ArrayList();
        CarRvHorizontalVo carRvHorizontalVo = new CarRvHorizontalVo("", "七律分舵", "舵主：石慧杰", "", 1);
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
