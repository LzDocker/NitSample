package com.docker.nitsample.vo.card;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.CarRvHorizontalVo;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleHorizontalCardVo2 extends BaseCardVo<String> {

    public int managerStyle;
    public LayoutManagerVo managerStyleVo;

    @Bindable
    public RecycleTopLayout recycleTopLayout;

    public AppRecycleHorizontalCardVo2(int style, int position, LayoutManagerVo managerStyleVo, RecycleTopLayout recycleTopLayout) {
        super(style, position);
        this.managerStyleVo = managerStyleVo;
        maxSupport = 1;
        this.recycleTopLayout = recycleTopLayout;
        setInnerResource();
    }

    @Bindable
    public RecycleTopLayout getRecycleTopLayout() {
        return recycleTopLayout;
    }

    public void setRecycleTopLayout(RecycleTopLayout recycleTopLayout) {
        this.recycleTopLayout = recycleTopLayout;
        notifyPropertyChanged(BR.recycleTopLayout);
    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_horizontal_card2;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {


    }

    public void onChildItemCilck(CarRvHorizontalVo horizontalVo, View view) {
        if (view.getId() == R.id.tv_join) { // 加入
            //
            Log.d("sss", "onChildItemCilck: ======加入分舵========");
        }

        if (view.getId() == R.id.iv_close) { // 关闭
            if (InnerResource != null && InnerResource.contains(horizontalVo)) {
                InnerResource.remove(horizontalVo);
                if (InnerResource.size() == 0) {
                    this.setRecycleTopLayout(null);
                }
            }
        }
    }

    public transient ItemBinding<CarRvHorizontalVo> itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner2).bindExtra(BR.parent, this);// 单一view 有点击事件;


    public ItemBinding<CarRvHorizontalVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
                    R.layout.app_card_horizontal_img_inner2).bindExtra(BR.parent, this);

        }
        return itemImgBinding;
    }

    public ObservableList<CarRvHorizontalVo> InnerResource = new ObservableArrayList<>();


    public void setInnerResource() {
        CarRvHorizontalVo carRvHorizontalVo = new CarRvHorizontalVo("", "七律分部", "CEO：石慧杰", "", 1);
        InnerResource.add(carRvHorizontalVo);
        InnerResource.add(carRvHorizontalVo);
        InnerResource.add(carRvHorizontalVo);
        InnerResource.add(carRvHorizontalVo);
        InnerResource.add(carRvHorizontalVo);
        InnerResource.add(carRvHorizontalVo);
    }


    public ObservableList<CarRvHorizontalVo> getInnerResource() {

        return InnerResource;
    }


}
