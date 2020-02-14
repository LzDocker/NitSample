package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.entity.NetImgVo;
import com.docker.cirlev2.vo.entity.NetImgWapperVo;
import com.docker.cirlev2.vo.entity.PublishImgSpeicalVo;
import com.docker.cirlev2.vo.entity.PublishRstVo;
import com.docker.cirlev2.vo.entity.RstVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PublishViewModel extends NitCommonContainerViewModel {


    @Inject
    public PublishViewModel() {

    }

    /*
     * 圈子栏目
     * */
    public final MediatorLiveData<List<CircleTitlesVo>> mCircleClassLv = new MediatorLiveData();

    @Inject
    CircleApiService circleApiService;


    public final MediatorLiveData<String> mImageUploadLV = new MediatorLiveData<>();
    public final MediatorLiveData<String> mDynamicPublishLV = new MediatorLiveData<>();
    public final MediatorLiveData<NetImgWapperVo> mImgListLV = new MediatorLiveData<>();
    public final MediatorLiveData<List<MemberGroupingVo>> mMemberGroupLV = new MediatorLiveData<>();
    public final MediatorLiveData<List<CircleListVo>> mCircleJoinLV = new MediatorLiveData<>();


    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void publishImgToserver(File file) {
        showDialogWait("上传中...", false);
        String fileNameByTimeStamp = System.currentTimeMillis() + ".jpg";
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", fileNameByTimeStamp, requestFile);
        mImageUploadLV.addSource(RequestSpeicalServer(circleApiService.publishImgToServer(body)),
                new NitNetBoundObserver(new NitBoundCallback() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null) {
                            mImageUploadLV.setValue(((PublishImgSpeicalVo) resource.data).url);
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


    public void publishNews(HashMap<String, String> paramMap) {
        showDialogWait("发布中...", false);
        mDynamicPublishLV.addSource(RequestServer(circleApiService.publishNews(paramMap)),
                new NitNetBoundObserver(new NitBoundCallback() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null && resource.data != null) {
                            mDynamicPublishLV.setValue(((PublishRstVo) resource.data).dynamicId + "-" + "1");
                        } else {
                            mDynamicPublishLV.setValue(resource.message);
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

    public void publishActive(HashMap<String, String> paramMap) {
        showDialogWait("发布中...", false);
        mDynamicPublishLV.addSource(RequestServer(circleApiService.publishactive(paramMap)),
                new NitNetBoundObserver(new NitBoundCallback() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null && resource.data != null) {
                            mDynamicPublishLV.setValue(((PublishRstVo) resource.data).activityid + "-" + "1");
                        } else {
                            mDynamicPublishLV.setValue(resource.message);
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

        mImgListLV.addSource(RequestSpeicalServer(circleApiService.fechBaiduImgList(params)),
                new NitNetBoundObserver(new NitBoundCallback() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null) {
                            mImgListLV.setValue(((NetImgWapperVo) resource.data));
                            mBaiduImgPage += 30;
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


    public void getDefaultImgList() {

        mImgListLV.addSource(RequestSpeicalServer(circleApiService.fechDefaltImgList()),
                new NitNetBoundObserver<>(new NitBoundCallback<List<RstVo>>() {
                    @Override
                    public void onComplete(Resource<List<RstVo>> resource) {
                        super.onComplete(resource);
                        if (resource.data != null && resource.data instanceof List && resource.data.size() > 0) {
                            NetImgWapperVo netImgWapperVo = new NetImgWapperVo();
                            List<NetImgVo> imgs = new ArrayList<>();
                            for (int i = 0; i < resource.data.size(); i++) {
                                NetImgVo netImgVo = new NetImgVo();
                                netImgVo.setThumbURL(CommonBdUtils.getCompleteImageUrl(resource.data.get(i).img));
                                imgs.add(netImgVo);
                            }
                            netImgWapperVo.setData(imgs);
                            mImgListLV.setValue(netImgWapperVo);
                        } else {
                            mImgListLV.setValue(null);
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


    /*
     * 成员分组
     * */
    public void getMemberGroup(StaCirParam mStartParam) {
        showDialogWait("发布中...", false);
        mMemberGroupLV.addSource(RequestServer(circleApiService.fechMemberGroup(mStartParam.getCircleid(),
                mStartParam.getUtid())),
                new NitNetBoundObserver(new NitBoundCallback<List<MemberGroupingVo>>() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null && resource.data != null) {
                            mMemberGroupLV.setValue((List<MemberGroupingVo>) resource.data);
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

    public void getCircleJoinList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mCircleJoinLV.addSource(RequestServer(circleApiService.fechJoinCircle(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleListVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleListVo>> resource) {
                        super.onComplete(resource);
                        this.onComplete();
                        mCircleJoinLV.setValue(resource.data);
                    }

                    @Override
                    public void onComplete() {
//                        super.onComplete();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }
                }));
    }


    public void FetchCircleClass(String circleid, String utid) {
        mCircleClassLv.addSource(RequestServer(circleApiService.fechCircleClass(circleid, utid)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }

                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mCircleClassLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }

}
