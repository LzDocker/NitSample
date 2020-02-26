package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.ActiveWraperVo;
import com.docker.cirlev2.BR;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleHorizontalCardVo extends BaseCardVo<ActiveWraperVo> {

    public int managerStyle;
    public LayoutManagerVo managerStyleVo;
    public RecycleTopLayout recycleTopLayout;

    public AppRecycleHorizontalCardVo(int style, int position, LayoutManagerVo managerStyleVo, RecycleTopLayout recycleTopLayout) {
        super(style, position);
        this.managerStyleVo = managerStyleVo;
        maxSupport = 1;
        this.recycleTopLayout = recycleTopLayout;
        mVmPath = "com.docker.nitsample.vm.card.AppRecycleHorizontalVm";
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

    public void onChildItemClick(ActiveVo item, View view) {
        // 进入活动详情
        Log.d("sss", "onChildItemClick: =======进入活动详情===");

        if (item == null || TextUtils.isEmpty(((ActiveVo) item).uuid)) {
            ToastUtils.showShort("数据不存在");
            return;
        }
        if (((ActiveVo) item).uuid.equals(CacheUtils.getUser().uuid)) {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_DETAIL).withSerializable("activeVo", ((ActiveVo) item)).navigation();
        } else {
            ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL_ACTIVITY)
                    .withString("activityid", ((ActiveVo) item).dataid)
                    .withString("activitytitle", ((ActiveVo) item).title)
                    .navigation();
        }
    }

    public transient ItemBinding<ActiveVo> itemImgBinding = ItemBinding.<ActiveVo>of(BR.item,
            R.layout.app_card_horizontal_img_inner).bindExtra(BR.parent, this);// 单一view 有点击事件;


    public ItemBinding<ActiveVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<ActiveVo>of(BR.item,
                    R.layout.app_card_horizontal_img_inner);

        }
        return itemImgBinding;
    }

    //ArrayList<ActiveVo> indexList;

    private ObservableField<List<ActiveVo>> activeVos = new ObservableField<>();


    public ObservableField<List<ActiveVo>> getActiveVos() {
        return activeVos;
    }

    public void setActiveVos(List<ActiveVo> activeVos) {
        this.activeVos.set(activeVos);
        if (activeVos == null || activeVos.size() == 0) {
            isnonedata.set(false);
        } else {
            isnonedata.set(true);
        }
    }


    public ObservableField<Boolean> isnonedata = new ObservableField<Boolean>();
}
