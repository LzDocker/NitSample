package com.docker.cirlev2.vo.vo;

import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.vo.card.BaseCardVo;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ShoppingCarItemVo extends BaseCardVo {


    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_shopping_car;
    }

    public transient ItemBinding<ServiceDataBean> itemBinding = ItemBinding.<ServiceDataBean>of(BR.item,
            R.layout.circlev2_shopping_car_item_inner);// 单一view 有点击事件;


    private String title;
    private String id;
    private boolean isSelect;
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

    public ShoppingCarItemVo(int style, int position) {
        super(style, position);
    }


}
