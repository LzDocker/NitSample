package com.bfhd.circle.api;

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

public interface CircleService {


    /*
     * 创建圈子
     * */
    @POST("api.php?m=circle.create")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleCreateRst>>> createCircle(@FieldMap Map<String, String> params);

    /*
     * 创建圈子
     * */
    @POST("api.php?m=circle.update")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleCreateRst>>> updateCircle(@FieldMap Map<String, String> params);


    /*
     * 加入圈子
     * */
    @POST("api.php?m=circle.userJoin")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> joinCircle(@FieldMap Map<String, String> params);


    /*
     * 圈子详情
     * */
    @POST("api.php?m=circle.detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleCreateVo>>> fechCircleDetailVo(@Field("utid") String utid, @Field("circleid") String circleid);


    /*
     * 圈子列表
     * */
    @POST("api.php?m=circle.getlist")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleListVo>>>> fechCircleList(@FieldMap Map<String, String> params);


    /*
     * 已加入的圈子
     * */
    @POST("api.php?m=circle.getMyJoin")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleListVo>>>> fechJoinCircle(@FieldMap Map<String, String> params);


    /*
     * 圈子详情 圈子主页
     * */
    @POST("api.php?m=circle.index")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleDetailVo>>> fechCircleDetail(@Field("memberid") String memberid,
                                                                         @Field("uuid") String uuid,
                                                                         @Field("utid") String utid,
                                                                         @Field("circleid") String circleid);

    /*
     * 圈子详情 圈子主页
     * */
    @POST("api.php?m=circle.index")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleDetailVo>>> fechCircleDetail(@FieldMap Map<String, String> param);


    /*
     * 圈子管理权限接口
     * */
    @POST("api.php?m=circle.authorization")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleGroupPerssionVo>>> fechCircleGroup(@Field("utid") String utid, @Field("circleid") String circleid);

    /*
     * 修改分组权限
     * */

    @POST("api.php?m=circle.updateAuthorization")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> updateCircleGroupPerssion(@FieldMap Map<String, String> param);

    /*
     * 设置成员
     * */

    @POST("api.php?m=circle.employeeSetting")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MemberSettingsVo>>> fechSettingMember(@FieldMap Map<String, String> param);

    /*
     * 设置成员 -- 保存信息
     * */

    @POST("api.php?m=circle.updateEmployeeSetting")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> updateSettingMember(@FieldMap Map<String, String> param);


    /*
     * 设置成员 -- 退出后圈子
     * */
    @POST("api.php?m=circle.quit")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> quitCircle(@FieldMap Map<String, String> param);

    /*
     * 成员分组l列表
     * */
    @POST("api.php?m=circle.getEmployeeGroup")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MemberGroupingVo>>>> fechMemberGroup(@Field("circleid") String circleid, @Field("utid") String utid);


    /*
     * 更新成员分组l列表
     * */
    @POST("api.php?m=circle.saveEmployeeGroup")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MemberGroupingVo>>>> updateMemberGroup(@FieldMap Map<String, String> param);


    /*
     * 圈子分类
     * */
    @POST("api.php?m=circle.getClass")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleTitlesVo>>>> fechCircleClass(@Field("circleid") String circleid, @Field("utid") String utid);


    /*
     * 保存圈子分类
     * */
    @FormUrlEncoded
    @POST("api.php?m=circle.saveClass")
    LiveData<ApiResponse<BaseResponse<List<CircleTitlesVo>>>> saveCircleClass(@FieldMap Map<String, String> params);

    /*
     * 内部成员
     * */
    @FormUrlEncoded
    @POST("api.php?m=circle.insideEmployees")
    LiveData<ApiResponse<BaseResponse<List<TradingCommonVo>>>> fechInnerPersonList(@FieldMap Map<String, String> params);

    /*
     * 外部成员
     * */
    @FormUrlEncoded
    @POST("api.php?m=circle.outsideEmployees")
    LiveData<ApiResponse<BaseResponse<List<TradingCommonVo>>>> fechOuterPersonList(@FieldMap Map<String, String> params);


    /*
     * 百度图库
     * */
    @GET("https://image.baidu.com/search/acjson")
    LiveData<ApiResponse<NetImgWapperVo>> fechBaiduImgList(@QueryMap HashMap<String, String> params);


    /*
     * 精选图库
     * */
    @GET("api.php?m=get.thumbs")
    LiveData<ApiResponse<BaseResponse<List<RstVo>>>> fechDefaltImgList();


    /*
     * 上传图片到服务器
     * */
    @Multipart
    @POST("kindeditor/php/uploadApi.php?mode=3")
    LiveData<ApiResponse<PublishImgSpeicalVo>> publishImgToServer(@Part MultipartBody.Part file);


    /*
     * 上传图片到oss
     * */
    @Multipart
    @POST("kindeditor/php/uploadApi.php?mode=3")
    LiveData<ApiResponse<BaseResponse<List<TradingCommonVo>>>> publishImgToOss(@FieldMap Map<String, String> params);



    /*
     * 发布动态 news
     * */

    @FormUrlEncoded
    @POST("api.php?m=publish")
    LiveData<ApiResponse<BaseResponse<PublishRstVo>>> publishNews(@FieldMap Map<String, String> params);


    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> fechCircleInfoList(@FieldMap Map<String, String> params);


    /*
     * 圈子详情tab列表数据
     * */
    @POST("api.php?m=dynamic.getList")
    @FormUrlEncoded
    LiveData<ApiResponse<String>> fechCircleInfoListSpec(@FieldMap Map<String, String> params);


    /*
     * 圈子详情tab列表数据 urlapi 形式
     * */
    @POST()
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> fechCircleInfoList(@Url String url, @FieldMap Map<String, String> params);


    /*
     * 圈子详情数据
     * */
    @POST("api.php?m=dynamic.detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ServiceDataBean>>> fechDynamicDetail(@FieldMap Map<String, String> params);


    /*
     * 点赞/取消点赞
     * */
    @POST("api.php?m=dynamic.favour")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RstVo>>> dynamicParise(@FieldMap Map<String, String> params);


    /*
     * 评论列表
     * */
    @POST("api.php?m=dynamic.commentList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CommentVo>>>> fechCommentList(@FieldMap Map<String, String> params);

    /*
     * 评论列表
     * */
    @POST("api.php?m=dynamic.comment")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CommentRstVo>>> commentDynamic(@FieldMap Map<String, String> params);

    /*
     * 权限
     * */
    @POST("api.php?m=circle.getAuthorization")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<PerssionVo>>> getAuthorization(@FieldMap Map<String, String> params);

    /*
     * 删除评论
     * */
    @POST("api.php?m=dynamic.delComment")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> delComment(@FieldMap Map<String, String> params);

    /*
     * 评论点赞
     * */
    @POST("api.php?m=dynamic.commentFavour")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> pariseComment(@FieldMap Map<String, String> params);


    /*
     * 收藏
     * */
    @POST("api.php?m=dynamic.collect")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> collect(@FieldMap Map<String, String> params);


    /*
     * 分享
     * */
    @POST("api.php?m=dynamic.share")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ShareBean>>> share(@FieldMap Map<String, String> params);


    /*
     * 关注
     * */
    @POST("api.php?m=user.focus")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> attention(@FieldMap Map<String, String> params);


    //

    @POST("api.php?m=circle.dynamicTop")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> dynamicTop(@FieldMap Map<String, String> params);


    //

    @POST("api.php?m=dynamic.del")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> dynamicDel(@FieldMap Map<String, String> params);


    @POST("api.php?m=user.countpage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<CircleCountpageVo>>> circlePersionDetail(@FieldMap Map<String, String> params);


    @POST("api.php?m=user.focus")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> focus(@FieldMap Map<String, String> params);

    @POST("api.php?m=circle.invitation")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<ShareVo>>> invite(@FieldMap Map<String, String> params);

    @POST("api.php?m=dynamic.replyList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CommentVo>>>> replyList(@FieldMap Map<String, String> params);

    @POST("api.php?m=get.hotwords")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<RstServerVo>>>> hotwords(@Field("type") String type);

    // 商品分类
    @POST("api.php?m=publics.linkage")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<TypeVo>>>> linkage(@Field("keyid") String keyid, @Field("parentid") String parentid);

    // 筛选条件
    @POST("api.php?m=data.screen")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<ScreenVo>>>> screenData(@FieldMap Map<String, String> params);// 筛选条件

    @POST("api.php?m=user.user_recommend")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CircleUserVo>>>> fetchUserVo(@Field("memberid") String memberid, @Field("uuid") String uuid);

    @POST("api.php?m=publics.feedBack")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> CircleReport(@Field("memberid") String memberid, @Field("content") String content);


    @POST("api.php?m=user.pullBlack")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> pullBlack(@Field("memberid") String memberid, @Field("black_memberid") String black_memberid);

    @POST("http://api.map.baidu.com/reverse_geocoding/v3/")
    @FormUrlEncoded
    LiveData<ApiResponse<CityCodeVo>> fechCityCode(@FieldMap Map<String, String> params);

}
