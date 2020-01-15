package com.bfhd.account.api;

import android.arch.lifecycle.LiveData;

import com.bfhd.account.vo.AllLinkageVo;
import com.bfhd.account.vo.AttendVo;
import com.bfhd.account.vo.CollectVo;
import com.bfhd.account.vo.CommentVo;
import com.bfhd.account.vo.FansVo;
import com.bfhd.account.vo.FavVo;
import com.bfhd.account.vo.HelpUserVo;
import com.bfhd.account.vo.MessageListVo;
import com.bfhd.account.vo.MoneyBoxVov2;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.NoSeeVo;
import com.bfhd.account.vo.MsgVo;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.OrderVo;
import com.bfhd.account.vo.PointVo;
import com.bfhd.account.vo.RegistVo;
import com.bfhd.account.vo.SystemMessageVo;
import com.bfhd.account.vo.tygs.BranchVoV2;
import com.bfhd.account.vo.tygs.MoneyItemVo;
import com.bfhd.account.vo.tygs.PointItemVo;
import com.bfhd.circle.vo.RstVo;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.common.common.config.CommonApiConfig;
import com.docker.common.common.vo.AddressVo;
import com.docker.common.common.vo.MoneyDetailVo;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.VisitingCardVo;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.vo.WorldNumListWj;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AccountService {


    /*
     * 注册
     * */
    @POST("api.php?m=login.register")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RegistVo>>> register(@FieldMap HashMap<String, String> paramMap);


    /*
     * 登录
     * */
    @POST("api.php?m=login")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<UserInfoVo>>> login(@FieldMap HashMap<String, String> paramMap);    /*

     /* 重置密码
     * */

    @POST("api.php?m=user.resetPassword")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> resetPwd(@FieldMap HashMap<String, String> paramMap);

    /* 重置密码
     * */
    @POST("api.php?m=visitingcard.editInfo")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> editInfo(@FieldMap HashMap<String, String> paramMap);

    /*
     * 发送验证码
     * */
    @POST("api.php?m=login.send_pcode")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<RstVo>>> sendSmsCode(@FieldMap HashMap<String, String> paramMap);

    /*
     * 获取国家列表
     * */
    @POST("api.php?m=get.getWorldList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<WorldNumList>>> featchWorldList(@Field("type") String type);


    /*
     * 获取国家列表--wj
     * */
    @POST("api.php?m=get.getWorldList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<WorldNumListWj>>> featchWorldListwj(@Field("type") String type);

    /*
     * 获取名片信息
     * */
    @POST("api.php?m=user.member_data_info")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<VisitingCardVo>>> cardDetail(@Field("memberid") String memberid, @Field("uuid") String uuid);

    /*
     * 获取名片信息
     * */
    @POST("api.php?m=user.attendList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<AttendVo>>>> attendList(@Field("memberid") String memberid, @Field("uuid") String uuid);

    /*
     * 获取国家列表
     * */
    @POST("api.php?m=user.focusList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<FansVo>>>> featchAttentionList(@FieldMap HashMap<String, String> paramMap);

    /*
     *
     * */
    @POST("api.php?m=my")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MyInfoVo>>> featchMineData(@Field("memberid") String memberid, @Field("uuid") String uuid);

    /*
     *
     * */
    @POST("api.php?m=member")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MyInfoDispatcherVo>>> featchMineData(@FieldMap HashMap<String, String> paramMap);


    /*
     * 粉丝列表
     * */
    @POST("api.php?m=user.fansList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<FansVo>>>> featchFansList(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--消息列表
     * */
    @POST("api.php?m=get.msgList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MessageListVo>>>> messageList(@FieldMap HashMap<String, String> paramMap);


    /*
     * 我的--系统通知列表
     * */
    @POST("api.php?m=get.msgTypeList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<SystemMessageVo>>>> systemMessageList(@FieldMap HashMap<String, String> paramMap);


    @POST("api.php?m=h5")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> getUseContantWebUrl(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--评论列表
     * */
    @POST("api.php?m=get.msgTypeList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CommentVo>>>> commentList(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--点赞列表
     * */
    @POST("api.php?m=get.msgTypeList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<FavVo>>>> favList(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--列表
     * */
    @POST("api.php?m=get.msgTypeList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CollectVo>>>> collectList(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--一键呼救设置紧急联系人
     * */
    @POST("api.php?m=callhelp.addUser")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MsgVo>>> saveEmergencyContact(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--一键呼救编辑紧急联系人
     * */
    @POST("api.php?m=callhelp.editUser")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MsgVo>>> editEmergencyContact(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--一键呼救设置联系人列表
     * */
    @POST("api.php?m=callhelp.userList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<HelpUserVo>>>> helpUserList(@FieldMap HashMap<String, String> paramMap);


    /*
     * 我的--一键呼救刪除联系人
     * */
    @POST("api.php?m=callhelp.delUser")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> deleteHelpUser(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--我的订单
     * */
    @POST("api.php?m=order.order_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<OrderVo>>>> accountOrder(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--反馈意见
     * */
    @POST("api.php?m=user.feedback")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MsgVo>>> commitSuggestion(@FieldMap HashMap<String, String> paramMap);


    /*
     * 我的--一键呼救设置--发布求救
     * */
    @POST("api.php?m=callhelp.onekey")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MsgVo>>> publishHelp(@FieldMap HashMap<String, String> paramMap);


    /*
     * 我的--积分记录
     * */
    @POST("api.php?m=user.getPointDetail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<PointVo>>>> pointDetail(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--点击头像编辑个人信息
     * */
    @POST("api.php?m=user.member_data_update")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MsgVo>>> editCardInfo(@FieldMap HashMap<String, String> paramMap);

    /*
     * 我的--关注列表--关注/取消关注
     * */
    @POST("api.php?m=user.focus")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> focusMe(@FieldMap HashMap<String, String> paramMap);


    @POST("api.php?m=user.readAllMsg")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> readAllMsg(@Field("memberid") String memberid);


    //版本更新
    @POST("api.php?m=publics.checkVersion")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<UpdateInfo>>> systemUpdate(@Field("clientType") String clientType, @Field("appType") String appType, @Field("version") String version);

    //获取地址列表
    @POST("api.php?m=user.member_address")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<AddressVo>>>> getAdressList(@FieldMap HashMap<String, String> paramMap);

    //添加、编辑地址
    @POST("api.php?m=user.member_addressSave")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> addAdress(@FieldMap HashMap<String, String> paramMap);

    //删除地址
    @POST("api.php?m=user.member_address_del")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> deleteAdress(@FieldMap HashMap<String, String> paramMap);

    //我的钱包
    @POST("api.php?m=my.my_money")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> moneyBox(@FieldMap HashMap<String, String> paramMap);//我的钱包

    @POST("api.php?m=my.my_money")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<MoneyBoxVov2>>> moneyBoxv2(@FieldMap HashMap<String, String> paramMap);

    //提现
    @POST("api.php?m=my.member_withdraw")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> txMoney(@FieldMap HashMap<String, String> paramMap);


    //我关注的列表
    @POST("api.php?m=user.focusList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<FansVo>>>> focusList(@FieldMap HashMap<String, String> paramMap);


    //明细
    @POST("api.php?m=user.getBalanceDetail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MoneyDetailVo>>>> moneyDetail(@FieldMap HashMap<String, String> paramMap);

    //获取城市三级联动数据
    @POST("api.php?m=publics.linkageAll")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<AllLinkageVo>>> cityChoose(@FieldMap HashMap<String, String> paramMap);

    // company列表
    @POST("api.php?m=app.integral_service")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<Map<String, String>>>> pointPay(@FieldMap HashMap<String, String> paramMap);

    // 拉黑列表列表
    @POST("api.php?m=user.blackList")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<NoSeeVo>>>> pullBlackList(@FieldMap HashMap<String, String> paramMap);

    // 解除拉黑
    @POST("api.php?m=user.pullBlack")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> pullBlack(@FieldMap HashMap<String, String> paramMap);

    // 积分
    @POST("api.php?m=my.getPointDetail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<PointItemVo>>>> fetchPointList(@FieldMap HashMap<String, String> paramMap);

    // 收益
    @POST("api.php?m=my.getPointDetail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<MoneyItemVo>>>> fetchMoneyList(@FieldMap HashMap<String, String> paramMap);


    /*
     * 已加入的圈子
     * */
    @POST("api.php?m=circle.getMyJoin")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<BranchVoV2>>>> fechJoinCircle(@FieldMap Map<String, String> params);


}
