package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bfhd.account.BR;
import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.AllLinkageVo;
import com.bfhd.account.vo.AttendVo;
import com.bfhd.account.vo.CollectVo;
import com.bfhd.account.vo.CommentVo;
import com.bfhd.account.vo.FansVo;
import com.bfhd.account.vo.FavVo;
import com.bfhd.account.vo.HelpUserVo;
import com.bfhd.account.vo.MessageListVo;
import com.bfhd.account.vo.NoSeeVo;
import com.bfhd.account.vo.MsgVo;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.OrderVo;
import com.bfhd.account.vo.PointVo;
import com.bfhd.account.vo.RegistParmVo;
import com.bfhd.account.vo.RegistVo;
import com.bfhd.account.vo.SystemMessageVo;
import com.bfhd.account.vo.TxmemberVo;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.RstVo;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.CacheMemoryStaticUtils;
import com.dcbfhd.utilcode.utils.EncryptUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.ParamUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.tool.MD5Util;
import com.docker.common.common.vo.AddressVo;
import com.docker.common.common.vo.MoneyDetailVo;
import com.docker.common.common.vo.UpdateInfo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.VisitingCardVo;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.vo.WorldNumListWj;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class AccountViewModel extends HivsBaseViewModel {

    private String testcode = "940418";


    @Inject
    CommonRepository commonRepository;

    @Inject
    AccountService accountService;

    @Inject
    public AccountViewModel() {

    }

    @Override
    public void initCommand() {

    }

    public LiveData<Resource<UpdateInfo>> checkUpData() {
        return new NetworkBoundResourceAuto<UpdateInfo>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<UpdateInfo>>> createCall() {
                return accountService.systemUpdate("2", "1", AppUtils.getAppVersionCode() + "");
            }
        }.asLiveData();
    }

    //
    public void sendSms(String phone, String area_code) {

        HashMap<String, String> param = new HashMap<>();
        long aa = System.currentTimeMillis() / 1000;
        String timestamp = String.valueOf(aa);
        String sign = MD5Util.toMD5_32(phone + "_" + timestamp);
        param.put("area_code", area_code);
        param.put("mobile", phone);
        param.put("timestamp", timestamp);
        param.put("sign", sign);
        showDialogWait("发送中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.sendSmsCode(param)), new HivsNetBoundObserver<>(new NetBoundCallback<RstVo>(this) {
                    @Override
                    public void onComplete(Resource<RstVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("发送成功！");
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<RstVo> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络问题，发送失败请重试！");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    /*
     * 1 个人
     * 2 企业
     * */
    public void register(RegistParmVo registParmVo, int type, String area_code, HashMap<String, String> wechatinfo, String phone, String bindType) {

//        String uid = map.get("uid");
//        String openid = map.get("openid");//微博没有
//        String unionid = map.get("unionid");//微博没有
//        String access_token = map.get("access_token");
//        String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
//        String expires_in = map.get("expires_in");
//        String name = map.get("name");
//        String gender = map.get("gender");
//        String iconurl = map.get("iconurl");
        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", registParmVo.getPhone());
        param.put("username", registParmVo.getPhone());
        param.put("smsCode", /*registParmVo.getSmscode()*/testcode);
        param.put("password", MD5Util.toMD5_32(registParmVo.getPwd()));
        param.put("reg_type", type + "");
        param.put("area_code", area_code);
        if (wechatinfo != null) {
            if ("1".equals(bindType)) {
                param.put("nickname", wechatinfo.get("name"));
                param.put("avatar", wechatinfo.get("iconurl"));
                param.put("wxUid", wechatinfo.get("unionid"));
            }
            if ("2".equals(bindType)) {
                param.put("nickname", wechatinfo.get("name"));
                param.put("avatar", wechatinfo.get("iconurl"));
                param.put("qqUid", wechatinfo.get("uid"));
            }

            showDialogWait("绑定中...", false);
        } else {
            showDialogWait("注册中...", false);
        }
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.register(param)), new HivsNetBoundObserver<>(new NetBoundCallback<RegistVo>(this) {
                    @Override
                    public void onComplete(Resource<RegistVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (resource.data != null) {
                            if (wechatinfo != null) {
                                ToastUtils.showShort("绑定成功");
                            } else {
                                ToastUtils.showShort("注册成功");
                            }
                            UserInfoVo userInfoVo = new UserInfoVo();
                            RegistVo registVo = resource.data;
                            userInfoVo.uid = registVo.getUid();
                            userInfoVo.circleid = registVo.getCircleid();
                            userInfoVo.uuid = registVo.getUuid();
                            userInfoVo.utid = registVo.getUtid();
                            userInfoVo.reg_type = "1";
                            userInfoVo.mobile = phone;
                            CacheUtils.saveUser(userInfoVo);
                            mVmEventSouce.setValue(new ViewEventResouce(105, "", resource.data));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void AutoLogin() {
//        showDialogWait("登录中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap<>();
        param.put("t", "auto");
        param.put("uuid", userInfoVo.uuid);
        param.put("clientType", "2");
        param.put("version", AppUtils.getAppVersionCode() + "");
        param.put("nonce_str", UUID.randomUUID().toString());
        param.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        param.put("sign", MD5Util.genAppSign(param));
        param.put("udid", CacheUtils.getudid());
        param.put("source", "1");

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.login(param)), new HivsNetBoundObserver<>(new NetBoundCallback<UserInfoVo>(this) {
                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        if (resource.data != null) {
                            CacheMemoryStaticUtils.put("user", resource.data); //缓存
                            CacheUtils.saveUser(resource.data);
                            mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onBusinessError(Resource<UserInfoVo> resource) {
                        super.onBusinessError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<UserInfoVo> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", null));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public MediatorLiveData<UserInfoVo> mUserAutoLoginLv = new MediatorLiveData<>();

    public void AutoLoginV2() {
//        showDialogWait("登录中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap<>();
        param.put("t", "auto");
        param.put("uuid", userInfoVo.uuid);
        param.put("clientType", "2");
        param.put("version", AppUtils.getAppVersionCode() + "");
        param.put("nonce_str", UUID.randomUUID().toString());
        param.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        param.put("sign", MD5Util.genAppSign(param));
        param.put("udid", CacheUtils.getudid());
        param.put("source", "1");

        mUserAutoLoginLv.addSource(
                commonRepository.noneChache(
                        accountService.login(param)), new HivsNetBoundObserver<>(new NetBoundCallback<UserInfoVo>(this) {
                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        if (resource.data != null) {
                            mUserAutoLoginLv.setValue(resource.data);
                            CacheMemoryStaticUtils.put("user", resource.data); //缓存
                            CacheUtils.saveUser(resource.data);
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onBusinessError(Resource<UserInfoVo> resource) {
                        super.onBusinessError(resource);
                        mUserAutoLoginLv.setValue(null);
                    }

                    @Override
                    public void onNetworkError(Resource<UserInfoVo> resource) {
                        super.onNetworkError(resource);
                        mUserAutoLoginLv.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public void Login(String name, String pwd, String area_code) {
        showDialogWait("登录中...", false);
        HashMap<String, String> param = new HashMap<>();
        param.put("username", name);
        param.put("password", EncryptUtils.encryptMD5ToString(pwd));
        param.put("clientType", "2");
        param.put("area_code", area_code);
        param.put("version", AppUtils.getAppVersionCode() + "");
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.login(param)), new HivsNetBoundObserver<>(new NetBoundCallback<UserInfoVo>(this) {
                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                            CacheMemoryStaticUtils.put("user", resource.data); //缓存
                            CacheUtils.saveUser(resource.data);
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void ThiredLogin(HashMap<String, String> param) {
        showDialogWait("登录中...", false);
        param.put("t", "wx");
        param.put("clientType", "2");
        param.put("source", "1");
        param.put("version", AppUtils.getAppVersionCode() + "");
        param.put("udid", CacheUtils.getudid());
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.login(param)), new HivsNetBoundObserver<>(new NetBoundCallback<UserInfoVo>(this) {
                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                            CacheMemoryStaticUtils.put("user", resource.data); //缓存
                            CacheUtils.saveUser(resource.data);
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onBusinessError(Resource<UserInfoVo> resource) {
//                        super.onBusinessError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(109, "", resource.data));

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public void ThiredLoginQQ(HashMap<String, String> param) {
        showDialogWait("登录中...", false);
        param.put("t", "qq");
        param.put("clientType", "2");
        param.put("source", "1");
        param.put("version", AppUtils.getAppVersionCode() + "");
        UserInfoVo userInfoVo = CacheUtils.getUser();
        param.put("udid", CacheUtils.getudid());
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.login(param)), new HivsNetBoundObserver<>(new NetBoundCallback<UserInfoVo>(this) {
                    @Override
                    public void onComplete(Resource<UserInfoVo> resource) {
                        super.onComplete(resource);
                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                            CacheMemoryStaticUtils.put("user", resource.data); //缓存
                            CacheUtils.saveUser(resource.data);
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onBusinessError(Resource<UserInfoVo> resource) {
//                        super.onBusinessError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(110, "", resource.data));

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public void resetPwd(String name, String smscode, String pwd) {
        showDialogWait("重置中...", false);
        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", name);
        param.put("smsCode", /*smscode*/testcode);
        param.put("newpsw", MD5Util.toMD5_32(pwd));
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.resetPwd(param)), new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void EditCard(HashMap<String, String> param) {
        showDialogWait("加载中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        param.put("memberid", userInfoVo.uid);
        param.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.editInfo(param)), new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(205, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void fetchWordList(String keyworld) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.featchWorldList(keyworld)), new HivsNetBoundObserver<>(new NetBoundCallback<WorldNumList>(this) {
                    @Override
                    public void onComplete(Resource<WorldNumList> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<WorldNumList> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", null));
                    }
                }));
    }

    public void fetchWordListwj(String keyworld) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.featchWorldListwj(keyworld)), new HivsNetBoundObserver<>(new NetBoundCallback<WorldNumListWj>(this) {
                    @Override
                    public void onComplete(Resource<WorldNumListWj> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<WorldNumListWj> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", null));
                    }
                }));
    }


    public void fechAttentionList() {

        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid2", userInfoVo.uid);
        params.put("uuid2", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.featchAttentionList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<FansVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<FansVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<FansVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(105, "", null));
                    }
                }));

    }


    //名片信息
    public void cardDetail() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.cardDetail(userInfoVo.uid, userInfoVo.uuid)), new HivsNetBoundObserver<>(new NetBoundCallback<VisitingCardVo>(this) {
                    @Override
                    public void onComplete(Resource<VisitingCardVo> resource) {
//                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(108, "", resource.data));

                    }


                    @Override
                    public void onNetworkError(Resource<VisitingCardVo> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(108, "", null));
                    }


                }));
    }

    //签到列表
    public void getAttendList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.attendList(userInfoVo.uid, userInfoVo.uuid)), new HivsNetBoundObserver<>(new NetBoundCallback<List<AttendVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<AttendVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(110, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<AttendVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(110, "", null));
                    }

                }));
    }


    public void getMyInfo() {
        //featchMineData
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.featchMineData(userInfoVo.uid, userInfoVo.uuid)), new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoVo> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(111, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<MyInfoVo> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(112, "", null));
                    }
                }));
    }


    /**
     * 我的--粉丝列表
     */
    public void getFansList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid2", userInfoVo.uid);
        params.put("uuid2", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.featchFansList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<FansVo>>() {
                    @Override
                    public void onComplete(Resource<List<FansVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(112, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<FansVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(113, "", null));
                    }
                }));
    }


    /**
     * 消息列表
     */
    public void getMessageList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid2", userInfoVo.uid);
        params.put("uuid2", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.messageList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<MessageListVo>>() {
                    @Override
                    public void onComplete(Resource<List<MessageListVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(512, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<MessageListVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(513, "", null));
                    }

                }));
    }

    /**
     * 系统通知列表
     */
    public void getSystemMessageList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("page", String.valueOf(mPage));
        params.put("type", "1");
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.systemMessageList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<SystemMessageVo>>() {
                    @Override
                    public void onComplete(Resource<List<SystemMessageVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(514, "", resource.data));
                        mPage++;
                    }

                    @Override
                    public void onNetworkError(Resource<List<SystemMessageVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(515, "", null));
                        ToastUtils.showShort("网络原因，加载失败");
                    }

                }));
    }


    /**
     * 评论列表
     */
    public void getCommenList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("page", String.valueOf(mPage));
        params.put("type", "2");
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.commentList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CommentVo>>() {
                    @Override
                    public void onComplete(Resource<List<CommentVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(516, "", resource.data));
                        mPage++;
                    }

                    @Override
                    public void onNetworkError(Resource<List<CommentVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(517, "", null));
                        ToastUtils.showShort("网络原因，加载失败");
                    }

                }));
    }

    /**
     * 点赞列表
     */
    public void getFavList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("page", String.valueOf(mPage));
        params.put("type", "3");
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.favList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<FavVo>>() {
                    @Override
                    public void onComplete(Resource<List<FavVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(518, "", resource.data));
                        mPage++;

                    }

                    @Override
                    public void onNetworkError(Resource<List<FavVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(519, "", null));
                        ToastUtils.showShort("网络原因，加载失败");
                    }

                }));
    }


    /**
     * 收藏列表
     */
    public void getCollectList(String type) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("page", String.valueOf(mPage));
        params.put("type", type);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.collectList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CollectVo>>() {
                    @Override
                    public void onComplete(Resource<List<CollectVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(520, "", resource.data));
                        mPage++;
                    }

                    @Override
                    public void onNetworkError(Resource<List<CollectVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(521, "", null));
                        ToastUtils.showShort("网络原因，加载失败");
                    }

                }));
    }


    /**
     * “一键呼救设置”里面保存添加的紧急联系人
     */
    public void saveEmergencyContact(String name, String remark, String quNum, String phone) {
        showDialogWait("保存中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("concat_phone", phone);
        params.put("concat_name", name);
        params.put("concat_area_code", quNum);
        params.put("remark", remark);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.saveEmergencyContact(params)), new HivsNetBoundObserver<>(new NetBoundCallback<MsgVo>() {
                    @Override
                    public void onComplete(Resource<MsgVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(522, "", resource.data));
                        ToastUtils.showShort("保存成功");
                    }


                    @Override
                    public void onBusinessError(Resource<MsgVo> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<MsgVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(523, "", null));
                        ToastUtils.showShort("网络原因，保存失败");
                    }

                }));
    }


    /**
     * “一键呼救设置”里面紧急联系人列表
     */
    public void helpUserList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.helpUserList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<HelpUserVo>>() {
                    @Override
                    public void onComplete(Resource<List<HelpUserVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(524, "", resource.data));

                    }

                    @Override
                    public void onNetworkError(Resource<List<HelpUserVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(525, "", null));
                    }
                }));
    }


    public void deleteHelpUser(String id) {
        showDialogWait("删除中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("id", id);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.deleteHelpUser(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(526, "", resource.data));
                        ToastUtils.showShort("删除成功");

                    }

                    @Override
                    public void onBusinessError(Resource<String> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(527, "", null));
                        ToastUtils.showShort("网络问题，删除失败");
                    }
                }));

    }

    /**
     * “一键呼救设置”里面编辑添加的紧急联系人
     */
    public void editEmergencyContact(String id, String name, String remark, String quNum, String phone) {
        showDialogWait("保存中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("concat_phone", phone);
        params.put("concat_name", name);
        params.put("concat_area_code", quNum);
        params.put("remark", remark);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.editEmergencyContact(params)), new HivsNetBoundObserver<>(new NetBoundCallback<MsgVo>() {
                    @Override
                    public void onComplete(Resource<MsgVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(528, "", resource.data));
                        ToastUtils.showShort("保存成功");
                    }

                    @Override
                    public void onBusinessError(Resource<MsgVo> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<MsgVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(529, "", null));
                        ToastUtils.showShort("网络问题，保存失败");
                    }
                }));
    }

    /**
     * 我的订单列表
     */
    public void getOrderList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid", userInfoVo.uid);
        params.put("page", String.valueOf(mPage));

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.accountOrder(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<OrderVo>>() {
                    @Override
                    public void onComplete(Resource<List<OrderVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(530, "", resource.data));
                        mPage++;
                    }

                    @Override
                    public void onNetworkError(Resource<List<OrderVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(531, "", null));
                        ToastUtils.showShort("网络问题，加载失败");
                    }
                }));
    }

    /**
     * 我的--提交意见反馈
     */
    public void commitSuggestion(String content, String phone) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("content", content);
        params.put("phone", phone);

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.commitSuggestion(params)), new HivsNetBoundObserver<>(new NetBoundCallback<MsgVo>() {
                    @Override
                    public void onComplete(Resource<MsgVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(532, "", resource.data));
                        ToastUtils.showShort("反馈成功");
                    }

                    @Override
                    public void onBusinessError(Resource<MsgVo> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<MsgVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(533, "", null));
                        ToastUtils.showShort("网络问题，加载失败");
                    }
                }));
    }

    /**
     * 我的--一键呼救设置--发布求救
     */
    public void publishHelp() {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("circleid", userInfoVo.circleid);
        params.put("memberid", userInfoVo.uid);
        params.put("lat", "");
        params.put("lng", "");
        params.put("'uuid'", userInfoVo.uuid);

        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.publishHelp(params)), new HivsNetBoundObserver<>(new NetBoundCallback<MsgVo>() {
                    @Override
                    public void onComplete(Resource<MsgVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(534, "", resource.data));
                        ToastUtils.showShort("发布求救成功");
                    }

                    @Override
                    public void onNetworkError(Resource<MsgVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(535, "", null));
                        ToastUtils.showShort("网络问题，加载失败");
                    }

                    @Override
                    public void onBusinessError(Resource<MsgVo> resource) {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    /**
     * 积分记录
     */

    public void getPointDetail(boolean isSign) {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        if (isSign) {
            params.put("dotype", "1");
        }
        params.put("page", String.valueOf(mPage));

        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.pointDetail(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<PointVo>>() {
            @Override
            public void onComplete(Resource<List<PointVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(536, "", resource.data));
                mPage++;
            }

            @Override
            public void onNetworkError(Resource<List<PointVo>> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(537, "", null));
                ToastUtils.showShort("网络问题，加载失败");
            }

            @Override
            public void onBusinessError(Resource<List<PointVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }


    public final MediatorLiveData<String> msgVoMediatorLiveData = new MediatorLiveData<>();

    /**
     * 编辑名片
     */
    public void saveCardInfo(VisitingCardVo visitingCardVo) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> paramMap = ParamUtils.obj2map(visitingCardVo);
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("avatar", visitingCardVo.getAvatar());
        paramMap.put("fullName", visitingCardVo.getFullName());
        paramMap.put("sex", visitingCardVo.getSex());
        paramMap.put("mobile", visitingCardVo.getMobile());
        paramMap.put("age", visitingCardVo.getAge());
        msgVoMediatorLiveData.addSource(commonRepository.noneChache(
                accountService.editCardInfo(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                msgVoMediatorLiveData.setValue("1");
                UserInfoVo userInfoVo1 = CacheUtils.getUser();
                userInfoVo1.nickname = visitingCardVo.getFullName();
                userInfoVo1.fullName = visitingCardVo.getFullName();
                userInfoVo1.avatar = visitingCardVo.getAvatar();
                CacheUtils.saveUser(userInfoVo1);
                ToastUtils.showShort("保存成功");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                msgVoMediatorLiveData.setValue(null);
                ToastUtils.showShort("网络问题，保存失败");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                msgVoMediatorLiveData.setValue(null);
            }
        }));

    }

    /**
     * 编辑名片
     */
    public void saveUserInfo(HashMap<String, String> paramMap) {
        showDialogWait("加载中", false);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.editCardInfov2(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(538, "", resource.data));
                ToastUtils.showShort("保存成功");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(539, "", null));
                ToastUtils.showShort("网络问题，保存失败");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }


//    /**
//     * 点击关注
//     *
//     * @param fansVo
//     */
//    public void focusMeClick(FansVo fansVo) {
//        showDialogWait("加载中", false);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        HashMap<String, String> paramMap = new HashMap<>();
//        paramMap.put("memberid", userInfoVo.uid);
//        paramMap.put("uuid", userInfoVo.uuid);
//        paramMap.put("nickname", userInfoVo.nickname);
//        paramMap.put("memberid2", fansVo.getMemberid());
//        paramMap.put("uuid2", fansVo.getUuid());
//        paramMap.put("status", "1");
//        mResourceLiveData.addSource(commonRepository.noneChache(
//                accountService.focusMe(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
//            @Override
//            public void onComplete(Resource<String> resource) {
//                super.onComplete(resource);
//                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(540, "", resource.data));
//                fansVo.setIsFocus(1);
//                fansVo.notifyPropertyChanged(BR.isFocus);
//                ToastUtils.showShort("关注成功");
//            }
//
//            @Override
//            public void onNetworkError(Resource<String> resource) {
//                super.onNetworkError(resource);
//                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(541, "", null));
//                ToastUtils.showShort("网络问题，保存失败");
//            }
//
//            @Override
//            public void onBusinessError(Resource<String> resource) {
//                super.onComplete();
//                hideDialogWait();
//            }
//        }));
//
//    }

    /**
     * 点击互相关注 将会取消关注
     *
     * @param fansVo
     */
    public void focusClick(FansVo fansVo) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            paramMap.put("nickname", userInfoVo.nickname);
        } else {
            paramMap.put("nickname", "匿名");
        }
        paramMap.put("memberid2", fansVo.getMemberid());
        paramMap.put("uuid2", fansVo.getUuid());
        if (fansVo.getIsFocus() == 1) {
            paramMap.put("status", "0");//1关注0取消关注
        } else {
            paramMap.put("status", "1");//1关注0取消关注
        }

        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.focusMe(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                if (fansVo.getIsFocus() == 1) {
                    fansVo.setIsFocus(0);
                    fansVo.setIsFocusMe(1);
                } else {
                    fansVo.setIsFocus(1);
                    fansVo.setIsFocusMe(0);
                }
                fansVo.notifyPropertyChanged(BR.isFocus);
                fansVo.notifyPropertyChanged(BR.isFocusMe);
                RxBus.getDefault().post(new RxEvent<>("refresh_card", ""));
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，保存失败");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }


    //readAllMsg
    public void readAllMsg() {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.readAllMsg(userInfoVo.uid)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(518, "", ""));
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    /**
     * 获取地址列表
     */
    public void getAdressList() {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.getAdressList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<AddressVo>>() {
            @Override
            public void onComplete(Resource<List<AddressVo>> resource) {
                super.onComplete(resource);

                mVmEventSouce.setValue(new ViewEventResouce(112, "", resource.data));
            }

            @Override
            public void onNetworkError(Resource<List<AddressVo>> resource) {
                super.onNetworkError(resource);

                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<List<AddressVo>> resource) {
                super.onComplete();

            }
        }));
    }

    /**
     * 添加、编辑地址
     */
    public void addAdress(AddressVo addressVo) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        String id = addressVo.getId();
        if (TextUtils.isEmpty(id)) {
            id = "";
        }
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("id", id);
        params.put("name", addressVo.getName());
        params.put("phone", addressVo.getPhone());
        params.put("location", addressVo.getLocation());
        params.put("address", addressVo.getAddress());
        params.put("is_moren", addressVo.getIs_moren());
        params.put("region1", addressVo.getRegion1());
        params.put("region2", addressVo.getRegion2());
        params.put("region3", addressVo.getRegion3());
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.addAdress(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(112, "", resource.data));
                RxBus.getDefault().post(new RxEvent<>("refresh_address_list", ""));
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("保存失败，请重试");
            }
        }));
    }

    /**
     * 添加地址
     */
    public void deleteAdress(AddressVo addressVo) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        String id = addressVo.getId();
        params.put("addressid", id);

        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.deleteAdress(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
                RxBus.getDefault().post(new RxEvent<>("refresh_address_list", ""));

            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("删除失败，请重试");
            }
        }));
    }

    /**
     * 我的钱包
     */
    public void moneyBox() {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.moneyBox(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("获取金额失败");
            }
        }));
    }

    /**
     * 提现
     */
    public void txMoney(String money) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("money", money);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.txMoney(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
                RxBus.getDefault().post(new RxEvent<>("refresh_tx", ""));
                ToastUtils.showShort("提现成功");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("提现失败");
            }
        }));
    }


    /**
     * 我的关注列表
     */
    public void getFocusList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid2", userInfoVo.uid);
        params.put("uuid2", userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.focusList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<FansVo>>() {
            @Override
            public void onComplete(Resource<List<FansVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
            }

            @Override
            public void onNetworkError(Resource<List<FansVo>> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(114, "", resource.data));
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<List<FansVo>> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }
        }));
    }


    public void getMoneyDetailList(String timeStamp) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
//        params.put("dotype", "1");
        params.put("month", timeStamp);
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.moneyDetail(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<MoneyDetailVo>>() {
            @Override
            public void onComplete(Resource<List<MoneyDetailVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
            }

            @Override
            public void onNetworkError(Resource<List<MoneyDetailVo>> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(114, "", resource.data));
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<List<MoneyDetailVo>> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }
        }));
    }


    public void getCityList() {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("keyid", "1");
        params.put("parentid", "0");
        mResourceLiveData.addSource(commonRepository.noneChache(
                accountService.cityChoose(params)), new HivsNetBoundObserver<>(new NetBoundCallback<AllLinkageVo>() {
            @Override
            public void onComplete(Resource<AllLinkageVo> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(114, "", resource.data));
            }

            @Override
            public void onNetworkError(Resource<AllLinkageVo> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(115, "", resource.data));
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<AllLinkageVo> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }
        }));
    }

    public void pointPay(HashMap<String, String> param) {
        showDialogWait("兑换中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.pointPay(param)), new HivsNetBoundObserver<>(new NetBoundCallback<Map<String, String>>(this) {
                    @Override
                    public void onComplete(Resource<Map<String, String>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(430, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));


    }

    /**
     * 获取拉黑列表
     */
    public void getPullBlackList() {
        HashMap<String, String> param = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        param.put("memberid", userInfoVo.uid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.pullBlackList(param)), new HivsNetBoundObserver<>(new NetBoundCallback<List<NoSeeVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<NoSeeVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(430, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<NoSeeVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(431, "", resource.data));
                        ToastUtils.showShort("网络问题，请重试");
                    }

                    @Override
                    public void onBusinessError(Resource<List<NoSeeVo>> resource) {
                        super.onComplete();
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(431, "", resource.data));
                        ToastUtils.showShort("请求数据失败，请重试");
                    }
                }));


    }

    /**
     * 解除拉黑
     */
    public void pullBlack(String id) {
        showDialogWait("加载中...", false);
        HashMap<String, String> param = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        param.put("id", id);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        accountService.pullBlack(param)), new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(432, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(433, "", resource.data));
                        ToastUtils.showShort("解除拉黑失败，请重试");
                    }

                    @Override
                    public void onBusinessError(Resource<String> resource) {
                        super.onComplete();
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(433, "", resource.data));
                        ToastUtils.showShort("解除拉黑失败，请重试");
                    }
                }));

    }


}

