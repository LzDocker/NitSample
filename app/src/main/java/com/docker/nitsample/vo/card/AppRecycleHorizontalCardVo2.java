package com.docker.nitsample.vo.card;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.bfhd.account.vm.card.AccountHeadCardViewModel;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vm.card.CircleRecomendListCardVm;
import com.docker.nitsample.vo.CarRvHorizontalVo;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleHorizontalCardVo2 extends BaseCardVo {

    public int managerStyle;
    public LayoutManagerVo managerStyleVo;

    @Bindable
    public RecycleTopLayout recycleTopLayout;

    public AppRecycleHorizontalCardVo2(int style, int position, LayoutManagerVo managerStyleVo, RecycleTopLayout recycleTopLayout) {
        super(style, position);
        this.managerStyleVo = managerStyleVo;
        maxSupport = 1;
        this.recycleTopLayout = recycleTopLayout;
        mVmPath = "com.docker.nitsample.vm.card.CircleRecomendListCardVm";
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

    public void onChildItemCilck(CircleListNomalVo horizontalVo, View view) {
        if (view.getId() == R.id.tv_join) { // 加入
            //
            ((CircleRecomendListCardVm) mNitcommonCardViewModel).joinCircle(horizontalVo);
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

    public transient ItemBinding<CircleListNomalVo> itemImgBinding = ItemBinding.<CircleListNomalVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner2).bindExtra(BR.parent, this);// 单一view 有点击事件;


    public ItemBinding<CircleListNomalVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<CircleListNomalVo>of(BR.item,
                    R.layout.app_card_horizontal_img_inner2).bindExtra(BR.parent, this);

        }
        return itemImgBinding;
    }

    //    public ObservableList<CarRvHorizontalVo> InnerResource = new ObservableArrayList<>();
    public ObservableList<CircleListNomalVo> InnerResource = new ObservableArrayList<>();


    public void setDatasource(List<CircleListNomalVo> datasource) {
        InnerResource.clear();
        InnerResource.addAll(datasource);
        if (InnerResource.size() == 0) {
            this.setRecycleTopLayout(null);
        }
    }


    public ObservableList<CircleListNomalVo> getInnerResource() {
        return InnerResource;
    }


}
