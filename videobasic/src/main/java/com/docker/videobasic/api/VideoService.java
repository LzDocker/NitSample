package com.docker.videobasic.api;

import android.arch.lifecycle.LiveData;

import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.videobasic.vo.VideoDyEntityVo;
import com.docker.videobasic.vo.VideoEntityVo;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface VideoService {

    /*
     * 圈子详情tab列表数据
     * */
    @POST()
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<VideoEntityVo>>>> fechCircleInfoList(@Url String url, @FieldMap Map<String, String> params);



    /*
     * 圈子详情tab列表数据
     * */
    @POST()
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<VideoDyEntityVo>>>> fechCircleDyList(@Url String url, @FieldMap Map<String, String> params);




}
