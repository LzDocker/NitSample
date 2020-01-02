package com.docker.nitsample.api;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.vo.RstServerVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.SampleServiceDataVo;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
