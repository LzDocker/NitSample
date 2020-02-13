package com.docker.apps.order.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.BR;
import com.docker.cirlev2.util.BdUtils;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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
        return (item, view) -> ARouter.getInstance().build(AppRouter.ORDER_DETAIL).withString("orderid", ((OrderVoV2) item).id).navigation();
    }

    /*
        "id": "10",
        "memberid": "7",
        "goodsType": "product",
        "status": "1",
        "goodsid": "7",
        "inputtime": "1566979688",
        "receiveName": "郭佳伟",
        "receiveTel": "11111111111",
        "receiveAddress": "把策动墨鱼汤",
        "orderNo": "HTJ019082816080843405",
        "payment": "3",
        "is_point": "1",
        "push_memberid": "3",
        "logisticsNo": "",
        "total": "184",
        "freight": "0",
        "push_memberName": "张大伟",
        "dynamicid": "1",
"goods_info":{
"goods_num":1
}
    * */

    /*
    * "id":"935",
"inputtime":"1581413302",
"orderNo":"TYGS20021117282272381",
"memberid":"938",
"total":"1098.00",
"status":"0",
"payment":"2",
"payStatus":"0",
"paysn":"",
"paytotal":"1098.00",
"receiveName":"jxdh",
"receiveAddress":"dj",
"receiveEmail":"",
"receiveZip":"",
"receiveTel":"12452365231",
"logistics":"",
"logisticsNo":"",
"remark":"",
"freight":"0",
"period":"0",
"uuid":"d901f5754f8b35622215ca8ad0825d4a",
"addressid":"142",
"is_point":"0",
"point_total":"0.00",
"money_total":"0.00",
"moneyType":"0",
"push_memberid":"0",
"push_uuid":"",
"goodsType":"",
"goodsid":"0",
"circleid":"0",
"utid":"",
"dynamicid":"0",
"logistic":"0",
"goodsPoint":"0.00",
"orderType":"1",
"companyid":"0",
"fahuoTime":"0",
"taketime":"0",
"payTime":"0",
"is_virtual":"0",
"pointsGoodsType":"0",
"is_read":"1",
"goodsTotalPrice":"1098.00",
    *
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


    public String dynamicid;
    public String circleid;

    public String utid;
    public String push_uuid;
    public String push_memberid;

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
    public String taketime;

    public String receiveName;
    public String orderNo;
    public String payment;
    public String is_point;
    public String total;
    public String push_memberName;
    public String freight;

    public String goodsTotalPrice;
    public String money_total;

    public String addressid;
    public String remark;

    public String receiveAddress;

    public ArrayList<GoodsInfo> goods_info;

    public ArrayList<GoodsInfo> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(ArrayList<GoodsInfo> goods_info) {
        this.goods_info = goods_info;
    }

    public class GoodsInfo extends BaseSampleItem {

        public String name;
        public String price;
        public String point;
        public String img;

        public String dynamicid;

        public OrderVoV2 parent;

        public String getImg() {
            return BdUtils.getImgUrl(img);
        }

        public void setImg(String img) {

            this.img = img;
        }

        @SerializedName("goodsNum")
        public String num;

        public String goods_num;

        @Override
        public int getItemLayout() {
            return R.layout.pro_order_detail_item_goods;
        }

        @Override
        public OnItemClickListener getOnItemClickListener() {
            return null;
        }
    }

}
