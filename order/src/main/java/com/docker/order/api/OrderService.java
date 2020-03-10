package com.docker.order.api;

import android.arch.lifecycle.LiveData;
import com.docker.cirlev2.vo.vo.ShoppingCarVoV3;
import com.docker.common.common.vo.PayOrederVo;
import com.docker.common.common.vo.WxOrderVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.order.vo.AddressVo;
import com.docker.order.vo.AllLinkageVo;
import com.docker.order.vo.GoodsVo;
import com.docker.order.vo.LogisticeVo;
import com.docker.order.vo.OrderVo;
import com.docker.order.vo.OrderVoV2;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {

    /*
     * 我的--我的订单
     * */
    @POST("api.php?m=order.order_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<OrderVo>>>> fetchOrder(@FieldMap HashMap<String, String> paramMap);


    //获取地址列表
    @POST("api.php?m=user.member_address")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<AddressVo>>>> fetchAdressList(@FieldMap HashMap<String, String> paramMap);


    //添加、编辑地址
    @POST("api.php?m=user.member_addressSave")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> addAdress(@FieldMap HashMap<String, String> paramMap);

    //删除地址
    @POST("api.php?m=user.member_address_del")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> deleteAdress(@FieldMap HashMap<String, String> paramMap);

    //获取城市三级联动数据
    @POST("api.php?m=publics.linkageAll")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<AllLinkageVo>>> cityChoose(@FieldMap HashMap<String, String> paramMap);


    // 订阅相关 -- 生成微信预支付订单
    @POST("api.php?m=pay.wechatPrepay")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<WxOrderVo>>> fechWxPreOrder(@FieldMap HashMap<String, String> paramMap);


    //获取物流详情
    @POST("api.php?m=user.wuliu")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<LogisticeVo>>> wuliu(@FieldMap HashMap<String, String> paramMap);//获取物流详情

    // 生成订单
    @POST("api.php?m=order.make")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<PayOrederVo>>> orderMaker(@FieldMap HashMap<String, String> paramMap);

    //订单列表
    @POST("api.php?m=order.order_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<OrderVoV2>>>> orderlist(@FieldMap HashMap<String, String> paramMap);

    //订单列表
    @POST("api.php?m=order.takeGoods")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> takeGoods(@FieldMap HashMap<String, String> paramMap);

    //订单列表--支付
    @POST("api.php?m=order.orderPay")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<PayOrederVo>>> orderPay(@FieldMap HashMap<String, String> paramMap);

    //订单列表--取消
    @POST("api.php?m=order.order_del")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> orderCancel(@FieldMap HashMap<String, String> paramMap);

    //订单列表--删除
    @POST("api.php?m=order.prePaymentOrder_del")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> prePaymentOrder_del(@FieldMap HashMap<String, String> paramMap);

    //订单列表--再次购买
    @POST("api.php?m=order.buyAgain")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ShoppingCarVoV3>>>> orderbuyAgain(@FieldMap HashMap<String, String> paramMap);

    //单个订单包含的商品列表
    @POST("api.php?m=order.getOrderGoods")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<GoodsVo>>>> orderGoodsList(@FieldMap HashMap<String, String> paramMap);

    //单个订单包含的商品列表
    @POST("api.php?m=order.order_info")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<OrderVoV2>>> order_info(@FieldMap HashMap<String, String> paramMap);

}
