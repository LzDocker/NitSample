package com.docker.nitsample.api;

import android.arch.lifecycle.LiveData;

import com.bfhd.circle.vo.CircleCountpageVo;
import com.bfhd.circle.vo.CircleCreateRst;
import com.bfhd.circle.vo.CircleCreateVo;
import com.bfhd.circle.vo.CircleDetailVo;
import com.bfhd.circle.vo.CircleGroupPerssionVo;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.CircleUserVo;
import com.bfhd.circle.vo.CityCodeVo;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.MemberSettingsVo;
import com.bfhd.circle.vo.NetImgWapperVo;
import com.bfhd.circle.vo.PerssionVo;
import com.bfhd.circle.vo.PublishImgSpeicalVo;
import com.bfhd.circle.vo.PublishRstVo;
import com.bfhd.circle.vo.RstVo;
import com.bfhd.circle.vo.ScreenVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.ShareVo;
import com.bfhd.circle.vo.TradingCommonVo;
import com.bfhd.circle.vo.TypeVo;
import com.docker.common.common.vo.RstServerVo;
import com.docker.common.common.vo.ShareBean;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;
import com.docker.nitsample.vo.SampleServiceDataVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
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


}
