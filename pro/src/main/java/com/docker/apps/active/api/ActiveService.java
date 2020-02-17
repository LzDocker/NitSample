package com.docker.apps.active.api;

import android.arch.lifecycle.LiveData;

import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.LinkageVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
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
    LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> fechCircleInfoList(@FieldMap Map<String, String> params);

    @POST("api.php?m=publics.linkage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<LinkageVo>>>> fetchLinkType(@FieldMap Map<String, String> params);

    @POST("api.php?m=activity.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ActiveVo>>>> fetchActiveList(@FieldMap Map<String, String> params);




}
