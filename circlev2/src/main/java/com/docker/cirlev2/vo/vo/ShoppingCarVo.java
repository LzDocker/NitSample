package com.docker.cirlev2.vo.vo;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.util.AppExecutors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ShoppingCarVo extends BaseCardVo {
    private String title;
    private String id;

    private ObservableList<ShoppingCarItemVo> shopCardListOb = new ObservableArrayList<>();
    private boolean isSelect;

    private ArrayList<ShoppingCartDbVo> LocalShopCart;

    public void setLocalShopCart(ArrayList<ShoppingCartDbVo> localShopCart) {
        LocalShopCart = localShopCart;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_shopping_car;
    }

    public transient ItemBinding<ShoppingCarItemVo> itemBinding = ItemBinding.<ShoppingCarItemVo>of(BR.item,
            R.layout.circlev2_shopping_car_item_inner);// 单一view 有点击事件;


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

    public ObservableList<ShoppingCarItemVo> getShopCardListOb() {
        return shopCardListOb;
    }



    public void setShopCardListOb(List<ShoppingCarItemVo> shopCardListOb) {

        if (LocalShopCart != null) {
            for (int i = 0; i < LocalShopCart.size(); i++) {
                for (int j = 0; j < shopCardListOb.size(); j++) {
                    if (shopCardListOb.get(j).getId().equals(LocalShopCart.get(i).id)) {
                        shopCardListOb.get(j).setNum(LocalShopCart.get(i).num);
                        BigDecimal bigPrice = new BigDecimal(shopCardListOb.get(j).price);
                        BigDecimal bigNum = new BigDecimal(shopCardListOb.get(j).getNum());
                        shopCardListOb.get(j).setTotalMoney(String.valueOf(bigPrice.multiply(bigNum)));
                    }
                }
            }
        }
        this.shopCardListOb.addAll(shopCardListOb);
    }

    public ShoppingCarVo(int style, int position) {
        super(style, position);
        mVmPath = "com.docker.cirlev2.vm.card.ShoppingCartViewModel";
    }


}
