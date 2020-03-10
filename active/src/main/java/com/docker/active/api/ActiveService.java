package com.docker.active.api;

import android.arch.lifecycle.LiveData;

import com.docker.active.vo.ActivePersionVo;
import com.docker.active.vo.ActiveSelectWraper;
import com.docker.active.vo.ActiveServerDataBean;
import com.docker.active.vo.ActiveSucVo;
import com.docker.active.vo.ActiveVo;
import com.docker.active.vo.ActiveWraperVo;
import com.docker.active.vo.LinkageVo;
import com.docker.active.vo.card.ActiveManagerDetailVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ActiveService {

    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ActiveServerDataBean>>>> fechCircleInfoList(@FieldMap Map<String, String> params);


    @POST("api.php?m=publics.linkage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<LinkageVo>>>> fetchLinkType(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ActiveWraperVo>>> fetchActiveList(@FieldMap Map<String, String> params);


    @POST("api.php?m=activity.detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ActiveVo>>> fetchActivedetail(@FieldMap Map<String, String> params);


    @POST("api.php?m=activity.joinActivity")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ActiveSucVo>>> activeJoin(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.manage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ActiveManagerDetailVo>>> activitymanage(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.updateStatus")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> updateStatus(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.delete")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> delete(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.getJoinList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ActivePersionVo>>>> persionList(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.updateReviewStatus")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> updateReviewStatus(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.evoucherValidate")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> evoucherValidate(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.activitySearch")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ActiveSelectWraper>>> ActiveSelectVo(@FieldMap Map<String, String> params);


    //ActiveManagerDetailVo


}
