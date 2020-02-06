package com.docker.apps.order.vo;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.docker.apps.R;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.util.BdUtils;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.ItemVo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class OrderVoV2 extends BaseSampleItem {

    @Override
    public int getItemLayout() {
        return R.layout.pro_order_item_order;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }

    /*
    * "id":"893",
"memberid":"938",
"goodsType":"",
"status":0,
"payStatus":"0",
"goodsid":"0",
"inputtime":"1580968620",
"logistic":"0",
"logisticsNo":"",
"receiveTel":"12452365231",
"payTime":"0",
"orderType":"1",
"push_memberid":"66",
"push_uuid":"8f08e38dfae8afc5913cd4713eabb0e2",
"taketime":"0",
"dynamicid":"0",
"goods_info":{
"goods_num":1
}
    * */


//


    public ItemBinding<GoodsInfo> getItemImgBinding() {
        ItemBinding<GoodsInfo> itemImgBinding = ItemBinding.<GoodsInfo>of(BR.item,
                R.layout.pro_order_img_inner);
        return itemImgBinding;
    }

    ObservableList<GoodsInfo> innerResource = new ObservableArrayList<>();

    public ObservableList<GoodsInfo> getInnerResource() {
        if (innerResource.size() == 0) {
            innerResource.addAll(goods_info);
        }
        return innerResource;
    }

    public String circleName;
    public String id;
    public String memberid;
    public String goodsType;
    public int status;
    public String payStatus;
    public String goodsid;
    public String inputtime;
    public String logistic;
    public String logisticsNo;
    public String receiveTel;
    public String payTime;
    public String orderType;
    public String push_memberid;
    public String push_uuid;
    public String taketime;
    public String dynamicid;
    public ArrayList<GoodsInfo> goods_info;

    public ArrayList<GoodsInfo> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(ArrayList<GoodsInfo> goods_info) {
        this.goods_info = goods_info;
    }

    public class GoodsInfo extends BaseObservable {

        public String name;
        public String price;
        public String point;
        public String img;

        public String getImg() {
            return BdUtils.getImgUrl(img);
        }

        public void setImg(String img) {

            this.img = img;
        }

        @SerializedName("goodsNum")
        public String num;

    }

}
