package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.BR;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.CarRvHorizontalVo;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleHorizontalCardVo extends BaseCardVo<String> {

    public int managerStyle;
    public LayoutManagerVo managerStyleVo;
    public RecycleTopLayout recycleTopLayout;

    public AppRecycleHorizontalCardVo(int style, int position, LayoutManagerVo managerStyleVo, RecycleTopLayout recycleTopLayout) {
        super(style, position);
        this.managerStyleVo = managerStyleVo;
        maxSupport = 1;
        this.recycleTopLayout = recycleTopLayout;
    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_horizontal_card;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.ll_title) {
            ARouter.getInstance().build(AppRouter.ACTIVE_INDEX).navigation();
            return;
        }
    }

    public void onChildItemClick(CarRvHorizontalVo carRvHorizontalVo, View view) {
        // 进入活动详情
        Log.d("sss", "onChildItemClick: =======进入活动详情===");
    }

    public transient ItemBinding<CarRvHorizontalVo> itemImgBinding = ItemBinding.<CarRvHorizontalVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner).bindExtra(BR.parent, this);// 单一view 有点击事件;


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
