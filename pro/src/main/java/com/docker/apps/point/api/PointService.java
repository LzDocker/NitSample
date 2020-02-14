package com.docker.apps.point.api;

import android.arch.lifecycle.LiveData;

import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.apps.point.vo.PointSortVo;
import com.docker.apps.point.vo.PointTabVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PointService {

    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> fechCircleInfoList(@FieldMap Map<String, String> params);

    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=publics.getRankList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<PointSortItemVo>>>> fechPointSortList(@FieldMap Map<String, String> params);


    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=publics.getMyRank")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<PointSortItemVo>>> getMyRank(@FieldMap Map<String, String> params);




    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=publics.getRankClass")
    LiveData<ApiResponse<BaseResponse<List<PointTabVo>>>> fechPointTabdata();


}
