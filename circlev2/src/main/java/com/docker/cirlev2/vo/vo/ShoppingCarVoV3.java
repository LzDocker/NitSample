package com.docker.cirlev2.vo.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.card.BaseCardVo;

import java.io.Serializable;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class ShoppingCarVoV3 extends BaseSampleItem {

    public String circleName;

    public String circleid;

    public String mark;


    @Bindable
    public List<CardInfo> info;

    @Bindable
    public List<CardInfo> getInfo() {
        return info;
    }

    @Bindable
    public void setInfo(List<CardInfo> info) {
        this.info = info;
    }

    public ObservableList<CardInfo> cardInfoObservableList = new ObservableArrayList<>();

    public ObservableList<CardInfo> getObList() {
        cardInfoObservableList.addAll(getInfo());
        return cardInfoObservableList;
    }

    public class CardInfo extends BaseObservable implements Serializable {
        public String id;
        public String inputtime;
        public String goodsid;
        public String skuId;
        public String goodsName;
        public String picture;
        public String price;
        public String memberid;
        public String circleid;
        public String transMoney;
        public String point;

        @Bindable
        public boolean kucunNoHave = false;

        @Bindable
        public boolean getKucunNoHave() {
            return kucunNoHave;
        }

        @Bindable
        public void setKucunNoHave(boolean kucunNoHave) {
            this.kucunNoHave = kucunNoHave;
            notifyPropertyChanged(BR.kucunNoHave);
        }

        @Bindable
        public int num;

        @Bindable
        public int getNum() {
            return num;
        }

        @Bindable
        public void setNum(int num) {
            this.num = num;
            notifyPropertyChanged(BR.num);
        }

        private boolean isSelect = false;

        @Bindable
        public boolean getIsSelect() {
            return isSelect;
        }

        @Bindable
        public void setIsSelect(boolean select) {
            isSelect = select;
            notifyPropertyChanged(BR.isSelect);
        }

    }


    @Override
    public int getItemLayout() {
        return R.layout.circlev2_item_shopping_car;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }


    private boolean isSelect = false;

    @Bindable
    public boolean getIsSelect() {
        return isSelect;
    }

    @Bindable
    public void setIsSelect(boolean select) {
        isSelect = select;
        notifyPropertyChanged(BR.isSelect);
    }

    public transient ItemBinding<CardInfo> itemBinding = ItemBinding.<CardInfo>of(BR.item,
            R.layout.circlev2_shopping_car_item_innerv3);// 单一view 有点击事件;


    public ItemBinding<CardInfo> getItemBinding(CircleShoppingViewModel circleShoppingViewModel) {
        ItemBinding<CardInfo> itemBinding;
        Log.d("sss", "getItemBinding: =============" + circleShoppingViewModel.flag);
        if (circleShoppingViewModel.flag == 1) {
            itemBinding = ItemBinding.<CardInfo>of(BR.item,
                    R.layout.circlev2_shopping_car_item_innerv3_style_2)
                    .bindExtra(BR.parent, this)
                    .bindExtra(BR.viewmodel, circleShoppingViewModel);// 单一view 有点击事件;
        } else {
            itemBinding = ItemBinding.<CardInfo>of(BR.item,
                    R.layout.circlev2_shopping_car_item_innerv3)
                    .bindExtra(BR.parent, this)
                    .bindExtra(BR.viewmodel, circleShoppingViewModel);// 单一view 有点击事件;
        }


        return itemBinding;
    }
}
