package com.bfhd.circle.vm;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.CircleCountpageVo;
import com.bfhd.circle.vo.CircleCreateRst;
import com.bfhd.circle.vo.CircleCreateVo;
import com.bfhd.circle.vo.CircleDetailVo;
import com.bfhd.circle.vo.CircleGroupPerssionVo;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.MemberSettingsVo;
import com.bfhd.circle.vo.NetImgVo;
import com.bfhd.circle.vo.NetImgWapperVo;
import com.bfhd.circle.vo.RstVo;
import com.bfhd.circle.vo.ShareVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.base.BaseVm;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CircleViewModel extends HivsBaseViewModel {

    private StaCirParam mStartParam;

    public boolean datainit = false;
    /*
     * 详情数据
     * */
    public final ObservableField<CircleDetailVo> detailVo = new ObservableField<>();

    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    public CircleViewModel() {

    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            getData();
        });
        mCommand.OnLoadMore(() -> {
            getData();
        });
        mCommand.OnRetryLoad(() -> {
            getData();
        });
    }

    public void initCircleParam(StaCirParam mStartParam) {
        this.mStartParam = mStartParam;
    }

    public void getData() {
        if (!datainit) {
            getCircleDetail();
//            getCircleClass();
        }
    }

    /*
     * 创建圈子
     * */
    public void createCircle(CircleCreateVo circleCreateVo, String type) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("创建中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        circleCreateVo.memberid = userInfoVo.uid;
        circleCreateVo.uuid = userInfoVo.uuid;
        circleCreateVo.fullName = circleCreateVo.circleName;
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("type", type);//官方圈0|兴趣圈1|企业圈2
        params.put("circleName", circleCreateVo.circleName);
        params.put("surfaceImg", circleCreateVo.surfaceImg);
        params.put("intro", circleCreateVo.intro);
        params.put("job", circleCreateVo.job);
        params.put("card", circleCreateVo.card);
        params.put("goodsname", circleCreateVo.goodsname);

        if (!TextUtils.isEmpty(userInfoVo.lng) && !TextUtils.isEmpty(userInfoVo.lat)) {
            params.put("lng", userInfoVo.lng);
            params.put("lat", userInfoVo.lat);
        }
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        if (!TextUtils.isEmpty(circleCreateVo.logoUrl)) {
            params.put("logoUrl", circleCreateVo.logoUrl);
        }
        if (!TextUtils.isEmpty(circleCreateVo.companyName))
            params.put("companyName", circleCreateVo.companyName);

        if (!TextUtils.isEmpty(circleCreateVo.address)) {
            params.put("address", circleCreateVo.address);
        }

        if (!TextUtils.isEmpty(circleCreateVo.linkman)) {
            params.put("linkman", circleCreateVo.linkman);
        }


        if (!TextUtils.isEmpty(circleCreateVo.contact))
            params.put("contact", circleCreateVo.contact);
//        if (TextUtils.equals("0", MyApplication.getInstance().getBaseSharePreference().readTeamid())) {
//            params.put("issetdef", "1");
//        } else {
//            params.put("issetdef", "0");
//        }
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.createCircle(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleCreateRst>() {
                    @Override
                    public void onComplete(Resource<CircleCreateRst> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("恭喜成功加入和太极家!");
                        mVmEventSouce.setValue(new ViewEventResouce(105, "", resource.data));

                    }

                    @Override
                    public void onNetworkError(Resource<CircleCreateRst> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络原因请重试！");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }



    /*
     * 编辑圈子
     * */

    public void editCircle(CircleCreateVo circleCreateVo, String type, String circleid) {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        showDialogWait("保存中...", false);
        circleCreateVo.memberid = userInfoVo.uid;
        circleCreateVo.uuid = userInfoVo.uuid;
        circleCreateVo.fullName = circleCreateVo.circleName;
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("type", type);//官方圈0|兴趣圈1|企业圈2
        params.put("circleName", circleCreateVo.circleName);
//        params.put("fullName", circleCreateVo.circleName);
        params.put("surfaceImg", circleCreateVo.surfaceImg);
        params.put("intro", circleCreateVo.intro);
        params.put("job", circleCreateVo.job);
        params.put("card", circleCreateVo.card);
        params.put("goodsname", circleCreateVo.goodsname);
        params.put("utid", circleCreateVo.utid);
        params.put("circleid", circleid);
//        if (TextUtils.isEmpty(userInfoVo.nickname)) {
//            params.put("fullName", "匿名");
//        } else {
//            params.put("fullName", userInfoVo.nickname);
//        }

        if (!TextUtils.isEmpty(userInfoVo.lng) && !TextUtils.isEmpty(userInfoVo.lat)) {
            params.put("lng", userInfoVo.lng);
            params.put("lat", userInfoVo.lat);
        }
        if (!TextUtils.isEmpty(circleCreateVo.logoUrl)) {
            params.put("logoUrl", circleCreateVo.logoUrl);
        }
        if (!TextUtils.isEmpty(circleCreateVo.companyName))
            params.put("companyName", circleCreateVo.companyName);
        if (!TextUtils.isEmpty(circleCreateVo.address))
            params.put("address", circleCreateVo.address);
        if (!TextUtils.isEmpty(circleCreateVo.linkman)) {
            params.put("linkman", circleCreateVo.linkman);
        }
        if (!TextUtils.isEmpty(circleCreateVo.contact))
            params.put("contact", circleCreateVo.contact);
//        if (TextUtils.equals("0", MyApplication.getInstance().getBaseSharePreference().readTeamid())) {
//            params.put("issetdef", "1");
//        } else {
//            params.put("issetdef", "0");
//        }
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.updateCircle(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleCreateRst>() {
                    @Override
                    public void onComplete(Resource<CircleCreateRst> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("保存成功");
                        mVmEventSouce.setValue(new ViewEventResouce(105, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<CircleCreateRst> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络原因请重试！");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }


    public void getCircleDetailVo(String utid, String circleID) {
        showDialogWait("加载中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleDetailVo(utid, circleID)),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleCreateVo>(this) {
                    @Override
                    public void onComplete(Resource<CircleCreateVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(108, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }

    /*
     * 圈子详情 主页
     * */
    public void getCircleDetail() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        if (!"-1".equals(userInfoVo.memberid)) {
            param.put("memberid", userInfoVo.uid);
            param.put("uuid", userInfoVo.uuid);
        }
        param.put("utid", mStartParam.getUtid());
        param.put("circleid", mStartParam.getCircleid());
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleDetail(param)),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleDetailVo>(this) {
                    @Override
                    public void onComplete(Resource<CircleDetailVo> resource) {
                        super.onComplete(resource);
                        detailVo.set(resource.data);
                        detailVo.notifyChange();
                        datainit = true;
                        mVmEventSouce.setValue(new ViewEventResouce(113, "", ""));
                    }
                }));
    }

    /*
     * 圈子分类
     * */
    public void getCircleClass() {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleClass(mStartParam.getCircleid(), mStartParam.getUtid())), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }
                }));
    }

    /*
     * 圈子分类
     * */
    public void getCircleClass(StaCirParam mStartParam) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleClass(mStartParam.getCircleid(), mStartParam.getUtid())), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }
                }));
    }


    /*
     * 圈子分类
     * */
    public void getCircleClass(String circleid, String utid) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleClass(circleid, utid)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }
                }));
    }


    /*
     * 成员分组
     * */
    public void getMemberGroup(StaCirParam mStartParam) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechMemberGroup(mStartParam.getCircleid(), mStartParam.getUtid())), new HivsNetBoundObserver<>(new NetBoundCallback<List<MemberGroupingVo>>() {
                    @Override
                    public void onComplete(Resource<List<MemberGroupingVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(112, "", resource.data));
                    }
                }));
    }


    /*
     * 成员分组
     * */
    public void updateMemberGroup(HashMap<String, String> params) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("保存中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.updateMemberGroup(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<MemberGroupingVo>>() {
                    @Override
                    public void onComplete(Resource<List<MemberGroupingVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(113, "", resource.data));
                        ToastUtils.showShort("保存成功!");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    /*
     * 圈子分组列表
     * */
    public void getCircleGrouop(StaCirParam mStartParam) {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleGroup(mStartParam.getUtid(), mStartParam.getCircleid())),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleGroupPerssionVo>(this) {
                    @Override
                    public void onComplete(Resource<CircleGroupPerssionVo> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(109, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<CircleGroupPerssionVo> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(109, "", null));
                    }
                }));
    }

    /*
     * 分组权限变更
     * */

    public void updateCircleGrouopPerssion(StaCirParam mStartParam, String key, String val, String groupid) {

        if (checkLoginState()) {
            return;
        }
        showDialogWait("修改中", false);
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("key", key);
        parms.put("val", val);
        parms.put("groupid", groupid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.updateCircleGroupPerssion(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("修改成功！");
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }
                }));
    }


    /*
     *
     * 设置成员
     * */
    public void getSettingMember(StaCirParam mStartParam, String employeeid, String employeeuuid) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", userInfoVo.uid);
        parms.put("uuid", userInfoVo.uuid);
        parms.put("employeeid", employeeid);
        parms.put("employeeuuid", employeeuuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechSettingMember(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<MemberSettingsVo>(this) {
                    @Override
                    public void onComplete(Resource<MemberSettingsVo> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(110, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<MemberSettingsVo> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(110, "", null));
                    }
                }));
    }


    public void updateSettingMember(StaCirParam mStartParam, String employeeid, String employeeuuid, String fullName, HashMap<String, String> parms) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("修改中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("employeeid", employeeid);
        parms.put("employeeuuid", employeeuuid);
        parms.put("fullName", fullName);
        parms.put("memberid", userInfoVo.uid);
        parms.put("utid", mStartParam.getUtid());
        parms.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.updateSettingMember(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("修改成功！");
                        mVmEventSouce.setValue(new ViewEventResouce(111, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }
                }));
    }

    public void quitCircle(StaCirParam mStartParam, String employeeid, String employeeuuid) {
        showDialogWait("删除中..", false);
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", employeeid);
        parms.put("uuid", employeeuuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.quitCircle(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("删除成功！");
                        mVmEventSouce.setValue(new ViewEventResouce(111, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }
                }));
    }


    public void quitCircle(StaCirParam mStartParam) {
        showDialogWait("退出中..", false);
        Map<String, String> parms = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", userInfoVo.uid);
        parms.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.quitCircle(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("退出成功！");
                        mVmEventSouce.setValue(new ViewEventResouce(111, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，退出失败！请重试");
                    }
                }));
    }


    /*
     * 保存圈子分类
     * */
    public void saveCircleClass(HashMap<String, String> param) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("保存中...", true);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.saveCircleClass(param)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleTitlesVo>> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                    }
                }));
    }


    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";


    public int mBaiduImgPage = 0;

    public void getBaiduImgList(String keyword) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cg", "star");
        params.put("cl", "2");
        params.put("ct", "201326592");
        params.put("face", "0");
        params.put("fp", "result");
        params.put("gsm", "1");
        params.put("ic", "0");
        params.put("ie", "utf-8");
        params.put("ipn", "rj");
        params.put("istype", "2");
        params.put("lm", "-1");
        params.put("nc", "1");
        params.put("oe", "utf-8");
        params.put("st", "-1");
        params.put("tn", "resultjson_com");
        params.put("pn", mBaiduImgPage + "");//为第几页
        if (TextUtils.isEmpty(keyword)) {
            params.put("word", "null");//关键词
            params.put("queryWord", "null");//查询关键词
        } else {
            params.put("word", keyword);//关键词
            params.put("queryWord", keyword);//查询关键词
        }
        params.put("rn", "30");//为一页返回的图片数量
        mResourceLiveData.addSource(
                commonRepository.SpecialFeatch(
                        circleService.fechBaiduImgList(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<NetImgWapperVo>(this) {
                    @Override
                    public void onComplete(Resource<NetImgWapperVo> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(105, null, resource.data));
                        mBaiduImgPage += 30;
                    }
                }));
    }

    public void getDefaultImgList() {
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechDefaltImgList()),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<RstVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<RstVo>> resource) {
                        super.onComplete(resource);
                        if (resource.data != null && resource.data.size() > 0) {
                            NetImgWapperVo netImgWapperVo = new NetImgWapperVo();
                            List<NetImgVo> imgs = new ArrayList<>();
                            for (int i = 0; i < resource.data.size(); i++) {
                                NetImgVo netImgVo = new NetImgVo();
                                netImgVo.setThumbURL(Constant.getCompleteImageUrl(resource.data.get(i).img));
                                imgs.add(netImgVo);
                            }
                            netImgWapperVo.setData(imgs);
                            mVmEventSouce.setValue(new ViewEventResouce(106, null, netImgWapperVo));
                        } else {
                            mVmEventSouce.setValue(new ViewEventResouce(106, null, null));
                        }
                    }
                }));
    }

    public void getCircleJoinList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.fechJoinCircle(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleListVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<CircleListVo>> resource) {
                        super.onComplete(resource);
                        this.onComplete();
                        mVmEventSouce.setValue(new ViewEventResouce(107, "", (List<CircleListVo>) resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleListVo>> resource) {
//                        super.onNetworkError(resource);

                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleListVo>> resource) {
//                        super.onBusinessError(resource);
                    }

                    @Override
                    public void onComplete() {
//                        super.onComplete();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onLoading() {
//                        super.onLoading();
                    }
                }));
    }


    // 圈子详情列表数据 调用固定接口的方式
    public void getCircleTabInfoListData(HashMap<String, String> param) {
        mResourceLiveData.addSource(
                commonRepository.SpecialFeatch(
                        circleService.fechCircleInfoListSpec(param)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        try {
                            JSONObject jsonObject = new JSONObject(resource.data);
                            if ("0".equals(jsonObject.get("errno"))) {
                                String datastr = jsonObject.getString("rst");

//                               List<ServiceDataBean>  serviceDataList = FastJsonUtils.getObjectsList(datastr, ServiceDataBean.class);
//                                if(serviceDataList!=null && serviceDataList.size()>0){
//                                    Log.d("sss", "onComplete: " + serviceDataList.get(0).getExtData().getResource());
//                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mVmEventSouce.setValue(new ViewEventResouce(201, "", resource.data));
                    }

                    @Override
                    public void onBusinessError(Resource<String> resource) {
                        super.onBusinessError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(202, "", resource.message));
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(203, "", null));
                    }
                }));
    }


    //api.php?m=dynamic.detail fechDynamicDetail


    // 加入圈子
    public void joinCircle(View view) {
        if (checkLoginState()) {
            return;
        }
        if (detailVo.get().getRole() > 0) {
            ToastUtils.showShort("圈主不能退出圈子");
            return;
        }
        if ("1".equals(detailVo.get().getIsJoin())) {
            quitCirlce();
        } else {
            joinCircle();
        }
    }

    public void quitCirlce() {

        showDialogWait("退出中..", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", userInfoVo.uid);
        parms.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.quitCircle(parms)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("退出成功！");
                        detailVo.get().setIsJoin("0");
                        detailVo.notifyPropertyChanged(BR.isJoin);
                        detailVo.get().setEmployeeNum(String.valueOf(Integer.parseInt(detailVo.get().getEmployeeNum()) - 1));
                        detailVo.notifyPropertyChanged(BR.employeeNum);
                        RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", "111")); // 刷新我的圈子
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }
                }));
    }

    public void joinCircle() {
        if (checkLoginState()) {
            return;
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("utid", mStartParam.getUtid());
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        params.put("circleid", mStartParam.getCircleid());
        showDialogWait("加入中...", false);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.joinCircle(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                ToastUtils.showShort("加入成功！");
                detailVo.get().setIsJoin("1");
                detailVo.notifyPropertyChanged(BR.isJoin);
                detailVo.get().setEmployeeNum(String.valueOf(Integer.parseInt(detailVo.get().getEmployeeNum()) + 1));
                detailVo.notifyPropertyChanged(BR.employeeNum);
                RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", "111")); // 刷新我的圈子
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("网络问题，加入失败请重试！");
            }
        }));
    }

    public void circlePersionDetail(String memberid2, String uuid2) {
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        if (!"-1".equals(userInfoVo.memberid)) {
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
        }
        params.put("memberid2", memberid2);
        params.put("uuid2", uuid2);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.circlePersionDetail(params)), new HivsNetBoundObserver<>(new NetBoundCallback<CircleCountpageVo>() {
                    @Override
                    public void onComplete(Resource<CircleCountpageVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(213, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public void getCircleJoinList(String uid, String uuid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", uid);
        params.put("uuid", uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.fechJoinCircle(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleListVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<CircleListVo>> resource) {
                        super.onComplete(resource);
                        this.onComplete();
                        mVmEventSouce.setValue(new ViewEventResouce(107, "", (List<CircleListVo>) resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleListVo>> resource) {
//                        super.onNetworkError(resource);

                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleListVo>> resource) {
//                        super.onBusinessError(resource);
                    }

                    @Override
                    public void onComplete() {
//                        super.onComplete();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onLoading() {
//                        super.onLoading();
                    }
                }));
    }


    public void focus(String uid, String uuid, CircleCountpageVo circleCountpageVo) {
        if (checkLoginState()) {
            return;
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid2", uid);
        params.put("uuid2", uuid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid", userInfoVo.uid);
        params.put("nickname", userInfoVo.nickname);
        if (circleCountpageVo.isFocus == 1) {
            params.put("status", "0");
        } else {
            params.put("status", "1");
        }
        showDialogWait("请稍后...", false);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.focus(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>(this) {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (circleCountpageVo.isFocus == 1) {
                            circleCountpageVo.setIsFocus(0);
                        } else {
                            circleCountpageVo.setIsFocus(1);
                        }
                        circleCountpageVo.notifyPropertyChanged(BR.isFocus);
                        RxBus.getDefault().post(new RxEvent<>("refresh_focus", circleCountpageVo.getIsFocus())); // 刷新我关注
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络错误，请重试");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public void invite(HashMap<String, String> param) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("请稍后...", false);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.invite(param)),
                new HivsNetBoundObserver<>(new NetBoundCallback<ShareVo>(this) {
                    @Override
                    public void onComplete(Resource<ShareVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(500, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<ShareVo> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络错误，请重试");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    public void getShareData(HashMap<String, String> params) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("加载中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.share(params)), new HivsNetBoundObserver<>(new NetBoundCallback<ShareBean>() {
                    @Override
                    public void onComplete(Resource<ShareBean> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(211, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }
    
    // 举报个人
    public void circlePersionReport(String memberid, String content) {
        showDialogWait("举报中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.CircleReport(memberid, content)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("举报成功");
//                        mVmEventSouce.setValue(new ViewEventResouce(211, "", resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("举报失败请重试...");
                    }
                }));

    }


    public void circleBlackList(String memberid) {
        if (checkLoginState()) {
            return;
        }
        showDialogWait("拉黑中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.pullBlack(userInfoVo.uid, memberid)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("拉黑成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("拉黑失败请重试...");
                    }
                }));
    }


    public boolean checkLoginState() {
        if (("-1".equals(CacheUtils.getUser().memberid))) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return true;
        } else {
            return false;
        }
    }


}
