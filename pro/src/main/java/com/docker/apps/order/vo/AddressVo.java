package com.docker.apps.order.vo;

import android.databinding.Bindable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

public class AddressVo extends BaseSampleItem {

    public AddressVo() {

    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_order_item_address;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
//            if (view.getId() == R.id.iv_choose_address) {
//                ((AddressVo) item).setIsCheck(!((AddressVo) item).isCheck);
//            }
            if (view.getId() == R.id.tv_edit_address) {
                ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_EDIT).withString("type", "1").withSerializable("AddressVo", (AddressVo) item).navigation();
            }
        };
    }

    private String id;
    private String name;
    private String phone;
    private String address;//详细地址
    private String is_moren;//是否默认 1 默认 0 非默认
    private String location;//所在地区
    private boolean isCheck;
    private String region1;
    private String region2;
    private String region3;

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getRegion3() {
        return region3;
    }

    public void setRegion3(String region3) {
        this.region3 = region3;
    }

    ;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Bindable
    public boolean getIsCheck() {
        return isCheck;
    }


    public void setIsCheck(boolean check) {
        isCheck = check;
        notifyPropertyChanged(BR.isCheck);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_moren() {
        return is_moren;
    }

    public void setIs_moren(String is_moren) {
        this.is_moren = is_moren;
    }


}
