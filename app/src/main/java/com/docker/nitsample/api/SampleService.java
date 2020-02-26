package com.docker.nitsample.api;

import android.arch.lifecycle.LiveData;
import android.telephony.SignalStrength;

import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.cirlev2.vo.entity.ServerGoodsDataBean;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.config.CommonApiConfig;
import com.docker.common.common.vo.RstServerVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.BannerEntityVo;
import com.docker.nitsample.vo.CircleListJoinVo;
import com.docker.nitsample.vo.CircleListNomalJoinVo;
import com.docker.nitsample.vo.GoodsBannerVo;
import com.docker.nitsample.vo.MenuEntityVo;
import com.docker.nitsample.vo.SampleServiceDataVo;
import com.docker.nitsample.vo.Tabvo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by zhangxindang on 2018/10/22.
 */

public interface SampleService {

    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<SampleServiceDataVo>>>> fechCircleInfoList(@FieldMap Map<String, String> params);


    @POST("api.php?m=get.hotwords")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<RstServerVo>>>> hotwords(@Field("type") String type);


    @POST("api.php?m=data.ad")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<BannerEntityVo>>>> fetchBannerAd(@FieldMap Map<String, String> params);

    @POST("api.php?m=publics.linkage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MenuEntityVo>>>> fetchIndexMenu(@FieldMap Map<String, String> params);

    @POST("api.php?m=publics.getBottomColumn")
    LiveData<ApiResponse<BaseResponse<List<Tabvo>>>> fetchIndexTabvo();

    @POST("api.php?m=dynamic.getGoodsBanner")
    LiveData<ApiResponse<BaseResponse<HashMap<String, List<BannerEntityVo>>>>> fetchGoodsBannervo();

    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ServerGoodsDataBean>>>> fechCircleGoodsList(@FieldMap Map<String, String> params);

    /*
     * 圈子列表
     * */
    @POST(CommonApiConfig.apiBaseUrlZHA + "api.php?m=circle.getlist")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleListJoinVo>>>> fechCircleList(@FieldMap Map<String, String> params);

    /*
     * 圈子列表
     * */
    @POST(CommonApiConfig.apiBaseUrlZHA + "api.php?m=circle.getlist")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleListNomalJoinVo>>>> fechCircleListNomal(@FieldMap Map<String, String> params);

    /*
     * 圈子列表
     * */
    @POST(CommonApiConfig.apiBaseUrlZHA + "api.php?m=circle.setCheckMsg")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> sendCheckMsg(@FieldMap Map<String, String> params);



    //api.php?m=circle.setCheckMsg
}
