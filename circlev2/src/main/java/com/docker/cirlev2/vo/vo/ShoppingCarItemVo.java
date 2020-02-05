package com.docker.cirlev2.vo.vo;

import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.card.BaseCardVo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ShoppingCarItemVo extends BaseSampleItem {


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_shopping_car_item_inner;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }


    @SerializedName("name")
    private String title;

    public String price;

    public String img;

    private String id;

    private boolean isSelect;

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String totalMoney;

    @Bindable
    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Bindable
    private int num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public int getNum() {
        return num;
    }

    @Bindable
    public void setNum(int num) {
        this.num = num;
    }


}
