package com.docker.apps.order.utils;

import com.docker.apps.order.vo.OrderVoV2;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderBdUtils {


//    public static String getGoodsImg() {
//
//    }


    public static boolean isShowName(ArrayList<OrderVoV2.GoodsInfo> goodsInfos) {
        if (goodsInfos == null || goodsInfos.size() == 0) {
            return false;
        }
        if (goodsInfos.size() == 1) {
            return true;
        } else {
            return false;
        }
    }


    public static String getGoodsName(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2) && chechGoods(orderVoV2)) {
            if (orderVoV2.goods_info.size() == 1) {
                return orderVoV2.goods_info.get(0).name;
            } else {
                return "";
            }
        }
        return "";
    }

    public static boolean chechOrder(OrderVoV2 orderVoV2) {
        return orderVoV2 != null;
    }

    public static boolean chechGoods(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2)) {
            if (orderVoV2.goods_info != null && orderVoV2.goods_info.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getPriceTotal(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2)) {
            if (orderVoV2.goods_info != null && orderVoV2.goods_info.size() > 0) {
                BigDecimal bigDecimal = new BigDecimal(0);
                for (int i = 0; i < orderVoV2.goods_info.size(); i++) {
                    OrderVoV2.GoodsInfo goodsInfo = orderVoV2.goods_info.get(i);
                    BigDecimal bigprice = new BigDecimal(goodsInfo.price);
                    BigDecimal bignum = new BigDecimal(goodsInfo.num);
                    bigDecimal = bigDecimal.add(bigprice.multiply(bignum).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                return String.valueOf(bigDecimal);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getNumTotal(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2)) {
            if (orderVoV2.goods_info != null && orderVoV2.goods_info.size() > 0) {
                int num = 0;
                for (int i = 0; i < orderVoV2.goods_info.size(); i++) {
                    OrderVoV2.GoodsInfo goodsInfo = orderVoV2.goods_info.get(i);
                    num += Integer.parseInt(goodsInfo.num);
                }
                return String.valueOf(num);
            } else {
                return "1";
            }
        } else {
            return "1";
        }
    }


    //    0代付款 ， 1待发货（已付款）， 2待收货（已发货）， 3已取消 ，4已完成/待评价 ,5已评价，6退货中, 7已退货
    public static String getOrderStatus(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2)) {
            String status = "待付款";
            switch (orderVoV2.status) {
                case 0:
                    status = "待付款";
                    break;
                case 1:
                    status = "待发货";
                    break;
                case 2:
                    status = "待收货";
                    break;
                case 3:
                    status = "已取消";
                    break;
                case 4:
                    status = "已完成";
                    break;
                case 5:
                    status = "已评价";
                    break;
                case 6:
                    status = "退货中";
                    break;
                case 7:
                    status = "已退货";
                    break;
            }
            return status;
        } else {
            return "";
        }
    }

    //    0代付款 ， 1待发货（已付款）， 2待收货（已发货）， 3已取消 ，4已完成/待评价 ,5已评价，6退货中, 7已退货
    public static boolean isShowDel(OrderVoV2 orderVoV2) {
        if (chechOrder(orderVoV2)) {
            boolean status = false;
            switch (orderVoV2.status) {
                case 0:
                    status = false;
                    break;
                case 1:
                    status = false;
                    break;
                case 2:
                    status = false;
                    break;
                case 3:
                    status = true;
                    break;
                case 4:
                    status = true;
                    break;
                case 5:
                    status = true;
                    break;
                case 6:
                    status = false;
                    break;
                case 7:
                    status = true;
                    break;
            }
            return status;
        } else {
            return false;
        }
    }


}
