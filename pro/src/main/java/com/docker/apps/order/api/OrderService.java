package com.docker.apps.order.api;

import android.arch.lifecycle.LiveData;

import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.AllLinkageVo;
import com.docker.apps.order.vo.OrderVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

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
}
