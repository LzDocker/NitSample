package com.docker.common.api;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.router.RoutersServerVo;
import com.docker.common.common.vo.ServiceDataBean;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface OpenService {

    /*
     * 路由
     * */
    @POST("http://test.cyn6.com/api.php?m=publics.getRoute")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RoutersServerVo>>> fetchRouter(@FieldMap HashMap<String, String> paramMap);


    //版本更新
    @POST("api.php?m=publics.checkVersion")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<UpdateInfo>>> systemUpdate(@Field("clientType") String clientType, @Field("appType") String appType, @Field("version") String version);

}