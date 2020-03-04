package com.docker.nitsample.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.ui.detail.index.CircleConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.MenuEntityVo;

import java.util.ArrayList;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleCard2Vo extends BaseCardVo {

    public AppRecycleCard2Vo(int style, int position) {
        super(style, position);
        maxSupport = 1;
        mVmPath = "com.docker.nitsample.vm.card.AppIndexMenuViewModel";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    public void onChildClick(MenuEntityVo itemVo) {

//        switch (itemVo.linkageid) {
//            case "3544":
//                CircleConfig circleConfig = new CircleConfig();
//                circleConfig.circleid = "64";
//                circleConfig.utid = "be49f2cb2627965610be332a4476286c";
////                circleConfig.circleType = "1";
//                circleConfig.circleType = "tyz";
////                circleConfig.Temple = 2;
//                circleConfig.isNeedToobar = true;
//                circleConfig.isNeedIntroduce = false;
//                circleConfig.extens.put("title", "桃源志");
//                ARouter.getInstance()
//                        .build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
//                        .withSerializable("circleConfig", circleConfig)
//                        .navigation();
//                break;
//            case "3476":
//                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_PRO_MUTIPARTINDEX).navigation();
//                break;
//            case "3477":
//                ARouter.getInstance().build(AppRouter.ACTIVE_INDEX).navigation();
//                break;
//            case "3472":
//                ARouter.getInstance().build(AppRouter.POINT_SORT_INDEX).navigation();
//                break;
//            case "3475":
//                ARouter.getInstance().build(AppRouter.INVITE_INDEX).navigation();
//                break;
//            case "3474":
//                RxBus.getDefault().post(new RxEvent<>("change", 1));
//                break;
//        }
        switch (itemVo.linkageid) {
            case "3544":
                CircleConfig circleConfig = new CircleConfig();
                circleConfig.circleid = "64";
                circleConfig.utid = "be49f2cb2627965610be332a4476286c";
//                circleConfig.circleType = "1";
                circleConfig.circleType = "0";
                circleConfig.Temple = 2;
                circleConfig.isNeedToobar = true;
                circleConfig.isNeedIntroduce = false;
                circleConfig.extens.put("title", "桃源志");
                ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
                        .withSerializable("circleConfig", circleConfig)
                        .navigation();
                break;
            case "3472":
                if (CacheUtils.getUser() == null) {
                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                    return;
                }
                if (CacheUtils.getUser() != null && "2".equals(CacheUtils.getUser().reg_type)) {
                    CircleConfig circleConfig1 = new CircleConfig();
                    circleConfig1.circleid = "61";
                    circleConfig1.utid = "fcecfffb5fb3c5927997c716b8947fe3";
                    circleConfig1.circleType = "3";
                    circleConfig1.Temple = 1;
                    circleConfig1.isNeedToobar = true;
                    circleConfig1.isNeedIntroduce = false;
                    ARouter.getInstance()
                            .build(AppRouter.CIRCLE_DETAIL_v2_INDEX_NEW_default)
                            .withSerializable("circleConfig", circleConfig1)
                            .navigation();
                } else {
                    ToastUtils.showShort("您还不是股东");
                }

                break;
            case "3476":
                ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL_v2_PRO_MUTIPARTINDEX).navigation();

                break;
            case "3474":
                ARouter.getInstance().build(AppRouter.ACTIVE_INDEX).navigation();
                break;
            case "3477":
                if (CacheUtils.getUser() == null) {
                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                    return;
                }
                ARouter.getInstance().build(AppRouter.POINT_SORT_INDEX).navigation();
                break;
            case "3478":
                if (CacheUtils.getUser() == null) {
                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                    return;
                }
                ARouter.getInstance().build(AppRouter.INVITE_INDEX).navigation();
                break;
            case "3475":
                RxBus.getDefault().post(new RxEvent<>("change", 1));
                break;
            case "3473":
                if (CacheUtils.getUser() == null) {
                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
                    return;
                }
                ARouter.getInstance().build(AppRouter.ACCOUNT_point).withString("type", "point").navigation();
                break;
        }
    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_card;
    }


    public transient ItemBinding<MenuEntityVo> itemImgBinding = ItemBinding.<MenuEntityVo>of(BR.item,
            R.layout.app_card_img_inner).bindExtra(BR.parent, this); // 单一view 有点击事件;
//


    public ItemBinding<MenuEntityVo> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<MenuEntityVo>of(BR.item,
                    R.layout.app_card_img_inner);
        }
        return itemImgBinding;
    }

    private ObservableList<MenuEntityVo> InnerResource = new ObservableArrayList<>();

    public void setInnerResource(ArrayList<MenuEntityVo> innerResource) {
        if (InnerResource.size() == 0) {
            InnerResource.addAll(innerResource);
        }
//        InnerResource.clear();

    }


//    public ObservableField<List<ItemVo>> getInnerResource() {
//        ItemVo itemVo = new ItemVo(R.mipmap.dzgl_true, "桃源志");
//        ItemVo itemVo1 = new ItemVo(R.mipmap.dzgl_true, "分部说");
//        ItemVo itemVo2 = new ItemVo(R.mipmap.dzgl_true, "积分榜");
//        ItemVo itemVo3 = new ItemVo((R.mipmap.dzgl_true), "股东汇");
//        ItemVo itemVo4 = new ItemVo((R.mipmap.dzgl_true), "购酒·优选");
//        ItemVo itemVo5 = new ItemVo((R.mipmap.dzgl_true), "沙龙·活动");
//        ItemVo itemVo6 = new ItemVo((R.mipmap.dzgl_true), "认知·课堂");
//        ItemVo itemVo7 = new ItemVo((R.mipmap.dzgl_true), "邀人·推广");
//        ArrayList<ItemVo> arrayList = new ArrayList();
//        arrayList.add(itemVo);
//        arrayList.add(itemVo1);
//        arrayList.add(itemVo2);
//        arrayList.add(itemVo3);
//        arrayList.add(itemVo4);
//        arrayList.add(itemVo5);
//        arrayList.add(itemVo6);
//        arrayList.add(itemVo7);
//        InnerResource.set(arrayList);
//        return InnerResource;
//    }

    public ObservableList<MenuEntityVo> getInnerResource() {
        return InnerResource;
    }
}
