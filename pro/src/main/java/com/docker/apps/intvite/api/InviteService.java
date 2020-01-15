package com.docker.apps.intvite.api;

import android.arch.lifecycle.LiveData;

import com.docker.apps.intvite.vo.InviteDataVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface InviteService {

    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> fechCircleInfoList(@FieldMap Map<String, String> params);


    /*
     * 邀请数据
     * */
    @GET("api.php?m=member.inviteFriends")
    LiveData<ApiResponse<BaseResponse<InviteDataVo>>> fechInviteData(@QueryMap Map<String, String> params);

}
