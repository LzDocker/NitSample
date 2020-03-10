package com.docker.order.vo;

import java.io.Serializable;

public class ISendVo implements Serializable {

    /**
     * id : 13
     * memberid : 7
     * goodsType : product
     * status : 1
     * payStatus : 1
     * goodsid : 7
     * inputtime : 1566981047
     * goods_info : {"name":"太极酒","price":"184","point":"100","img":"/var/upload/image/2019/08/2019082611290451635_172x172.png"}
     */

    private String id;
    private String memberid;
    private String goodsType;
    private int status;
    private String payStatus;
    private String goodsid;
    private String inputtime;
    private String logistic;//物流公司
    private String logisticsNo;//物流单号
    private String receiveTel;//收货人电话
    private String dynamicid;

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    private GoodsInfoBean goods_info;

    public String getLogistic() {
        return logistic;
    }

    public void setLogistic(String logistic) {
        this.logistic = logistic;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getReceiveTel() {
        return receiveTel;
    }

    public void setReceiveTel(String receiveTel) {
        this.receiveTel = receiveTel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public GoodsInfoBean getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoBean goods_info) {
        this.goods_info = goods_info;
    }

    public static class GoodsInfoBean implements Serializable {
        /**
         * name : 太极酒
         * price : 184
         * point : 100
         * img : /var/upload/image/2019/08/2019082611290451635_172x172.png
         */

        private String name;
        private String price;
        private String point;
        private String img;
        private int goods_num;

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
