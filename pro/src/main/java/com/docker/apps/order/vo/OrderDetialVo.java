package com.docker.apps.order.vo;

public class OrderDetialVo {


    /**
     * id : 10
     * memberid : 7
     * goodsType : product
     * status : 1
     * goodsid : 7
     * inputtime : 1566979688
     * receiveName : 郭佳伟
     * receiveTel : 11111111111
     * receiveAddress : 把策动墨鱼汤
     * orderNo : HTJ019082816080843405
     * payment : 3
     * push_memberid : 3
     * logisticsNo :
     * total : 184
     * freight : 0
     * push_memberName : 张大伟
     * goods_info : {"name":"太极酒","price":"184","point":"100","img":"/var/upload/image/2019/08/2019082611290451635_172x172.png"}
     */

    public String uuid;

    private String id;
    private String memberid;
    private String goodsType;
    private String status;
    private String goodsid;
    private String inputtime;
    private String receiveName;
    private String receiveTel;
    private String receiveAddress;
    private String orderNo;
    private String payment;
    private String is_point;
    private String push_memberid;
    private String push_uuid;
    private String logisticsNo;
    private String total;
    private String freight;
    private String push_memberName;
    private String dynamicid;
    private GoodsInfoBean goods_info;
    private String payTime;
    private String paytotal;
    private String is_refunds;
    private String remark;

    public String getIs_refunds() {
        return is_refunds;
    }

    public void setIs_refunds(String is_refunds) {
        this.is_refunds = is_refunds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaytotal() {
        return paytotal;
    }

    public void setPaytotal(String paytotal) {
        this.paytotal = paytotal;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getIs_point() {
        return is_point;
    }

    public void setIs_point(String is_point) {
        this.is_point = is_point;
    }

    public String getPush_uuid() {
        return push_uuid;
    }

    public void setPush_uuid(String push_uuid) {
        this.push_uuid = push_uuid;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveTel() {
        return receiveTel;
    }

    public void setReceiveTel(String receiveTel) {
        this.receiveTel = receiveTel;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPush_memberid() {
        return push_memberid;
    }

    public void setPush_memberid(String push_memberid) {
        this.push_memberid = push_memberid;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getPush_memberName() {
        return push_memberName;
    }

    public void setPush_memberName(String push_memberName) {
        this.push_memberName = push_memberName;
    }

    public GoodsInfoBean getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoBean goods_info) {
        this.goods_info = goods_info;
    }

    public static class GoodsInfoBean {
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

        private String goods_num;

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
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
