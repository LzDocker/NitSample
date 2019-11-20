package com.docker.message.api;

import android.arch.lifecycle.LiveData;

import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.message.vo.MessageDetailListVo;
import com.docker.message.vo.MessageListVo;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MessageService {

    /*
     * 消息列表
     * */
    @POST(MessageApiConfig.apiBaseUrl + "api.php?m=get.msgList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MessageListVo>>>> FsetchmessageList(@FieldMap HashMap<String, String> paramMap);

    /*
     * 消息二级列表
     * */
    @POST(MessageApiConfig.apiBaseUrl + "api.php?m=get.msgTypeList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MessageDetailListVo>>>> FetchMessageList(@FieldMap HashMap<String, String> paramMap);


    @POST("api.php?m=user.readAllMsg")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> readAllMsg(@Field("memberid") String memberid);
}
