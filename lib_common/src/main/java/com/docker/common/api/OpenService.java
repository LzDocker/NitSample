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

import io.reactivex.Observable;
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


    /*
     * 路由
     * */
    @POST
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<Object>>> fetchCommon(@Url String url, @FieldMap HashMap<String, String> paramMap);

    @POST("http://www.hanfengyishu.cn/api.php?m=lession.book_list")
    @FormUrlEncoded
    Observable<BaseResponse<List>> tttt1(@FieldMap HashMap<String, Object> map);

    @POST("http://www.hanfengyishu.cn/api.php?m=lession.lession_list")
    @FormUrlEncoded
    Observable<BaseResponse<List>> tttt2(@FieldMap HashMap<String, Object> map);

    @POST("http://www.hanfengyishu.cn/api.php?m=book.book_list")
    @FormUrlEncoded
    Observable<BaseResponse<List>> ttttt3(@FieldMap HashMap<String, Object> map);

}
