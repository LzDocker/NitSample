package com.bfhd.evaluate.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.evaluate.api.EnStudyService;
import com.bfhd.evaluate.vo.AfterClassListVo;
import com.bfhd.evaluate.vo.BookListVo;
import com.bfhd.evaluate.vo.CataLogListVo;
import com.bfhd.evaluate.vo.EnStudyOutVo;
import com.bfhd.evaluate.vo.LessonDetailVo;
import com.bfhd.evaluate.vo.LessonListVo;
import com.bfhd.evaluate.vo.RadioLessonVo;
import com.bfhd.evaluate.vo.RadioMenuVo;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class EnStudyViewModel extends NitCommonContainerViewModel {


    @Inject
    EnStudyService commonService;

    @Inject
    public EnStudyViewModel() {

    }

//    public final MediatorLiveData<List<RadioMenuVo>> radioMenus = new MediatorLiveData<>();
//    public final MediatorLiveData<LessonDetailVo> lessonDetailVoMediatorLiveData = new MediatorLiveData<>();


    /**
     * 返回 {@link com.bfhd.evaluate.vo.RadioLessonVo}
     *
     * @param apiurl
     * @param param
     * @return
     */
    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> apiResponseLiveData = commonService.lessionRadioLessonList(param);
        return apiResponseLiveData;
    }

    public void getBookList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, Object> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mContainerLiveData.addSource(
                RequestServer(
                        commonService.bookList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<BookListVo>>() {
                    @Override
                    public void onComplete(Resource<List<BookListVo>> resource) {
                        super.onComplete(resource);
                        //       radioLessonVo.setValue(new ViewEventResouce(512, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<BookListVo>> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onBusinessError(Resource<List<BookListVo>> resource) {
                        super.onBusinessError(resource);

                    }
                }));
    }

    //新概念 第一二三四册
    public void getCataLogList(String book_id, String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        paramMap.put("book_id", book_id);
        mContainerLiveData.addSource(RequestServer(
                commonService.catalogList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CataLogListVo>>() {
            @Override
            public void onComplete(Resource<List<CataLogListVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                //  mVmEventSouce.setValue(new ViewEventResouce(513, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<List<CataLogListVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }

    public void getLessonList(String catalog_id, String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        paramMap.put("catalog_id", catalog_id);
        mContainerLiveData.addSource(RequestServer(
                commonService.lessionList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<LessonListVo>>() {
            @Override
            public void onComplete(Resource<List<LessonListVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                // mVmEventSouce.setValue(new ViewEventResouce(514, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<List<LessonListVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }

    public void getLessonDetail(String memberid, String uuid, String id) {
        showDialogWait("正在加载课程数据", true);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        paramMap.put("id", id);
        mContainerLiveData.addSource(RequestServer(
                commonService.lessionDetail(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<LessonDetailVo>() {
            @Override
            public void onComplete(Resource<LessonDetailVo> resource) {
                super.onComplete(resource);
                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(515, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<LessonDetailVo> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }


    public void getRadioLessonList(String id, String memberid, String uuid) {
        showDialogWait("正在加载课程数据", true);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        paramMap.put("lession_dubbing_id", id);
        mContainerLiveData.addSource(RequestServer(
                commonService.lessionRadioLessonList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<RadioLessonVo>>() {
            @Override
            public void onComplete(Resource<List<RadioLessonVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(517, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<List<RadioLessonVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    public void getAudioLessonDetail(String memberid, String uuid, String id) {
        showDialogWait("正在加载课程数据", true);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        paramMap.put("detail_id", id);
        mContainerLiveData.addSource(RequestServer(
                commonService.radioLessonDetail(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<LessonDetailVo>() {
            @Override
            public void onComplete(Resource<LessonDetailVo> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mContainerLiveData.setValue(resource.data);
            }

            @Override
            public void onBusinessError(Resource<LessonDetailVo> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    public void studyList(int id) {
        showDialogWait("正在加载课程数据", true);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("type", id);
        mContainerLiveData.addSource(RequestServer(
                commonService.studyList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<AfterClassListVo>>() {
            @Override
            public void onComplete(Resource<List<AfterClassListVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(518, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<List<AfterClassListVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    public void afterDetail(String type, String id) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("type", type);
        paramMap.put("id", id);
        mContainerLiveData.addSource(RequestServer(
                commonService.studyDetail(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<AfterClassListVo>() {
            @Override
            public void onComplete(Resource<AfterClassListVo> resource) {
                super.onComplete(resource);
                hideDialogWait();
                //  mVmEventSouce.setValue(new ViewEventResouce(533, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<AfterClassListVo> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    public void outStudyList() {
//        showDialogWait("正在加载课程数据", true);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        mContainerLiveData.addSource(RequestServer(
                commonService.outStudyList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<EnStudyOutVo>>() {
            @Override
            public void onComplete(Resource<List<EnStudyOutVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
//                mVmEventSouce.setValue(new ViewEventResouce(519, "", resource.data));
            }

            @Override
            public void onBusinessError(Resource<List<EnStudyOutVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }

        }));
    }

    //lession.book_list顶部书列表
    public void getLessonBookList(String memberid, String uuid) {
        showDialogWait("正在加载课程数据", true);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        mContainerLiveData.addSource(RequestServer(
                commonService.lessionTopList(paramMap)), new HivsNetBoundObserver<>(new NetBoundCallback<List<RadioMenuVo>>() {
            @Override
            public void onComplete(Resource<List<RadioMenuVo>> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mContainerLiveData.setValue(resource.data);
            }

            @Override
            public void onBusinessError(Resource<List<RadioMenuVo>> resource) {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }
    public void testRxCon(String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        commonService.tttt1(paramMap).doOnNext(new Consumer<BaseResponse<List<RadioMenuVo>>>() {
            @Override
            public void accept(BaseResponse<List<RadioMenuVo>> listBaseResponse) throws Exception {
                LogUtils.e(JSON.toJSONString(listBaseResponse.getRst().get(0)));
            }
        }).concatMap(new Function<BaseResponse<List<RadioMenuVo>>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(BaseResponse<List<RadioMenuVo>> listBaseResponse) throws Exception {
                RadioMenuVo vo = listBaseResponse.getRst().get(0);
                paramMap.put("lession_dubbing_id", vo.getId());
                return commonService.tttt2(paramMap);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).cast(BaseResponse.class).subscribe(
                new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        List<RadioLessonVo> radioLessonVos = (List<RadioLessonVo>) baseResponse.getRst();
                        LogUtils.e(radioLessonVos.get(0).getName());
                    }
                });

    }

    public void testRxCon2(String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        Observable.merge(commonService.tttt1(paramMap), commonService.ttttt3(paramMap)).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).subscribe(new Consumer<BaseResponse<? extends List<? extends Object>>>() {
            @Override
            public void accept(BaseResponse<? extends List<? extends Object>> baseResponse) throws Exception {
                List d = baseResponse.getRst();
                if (d.get(0) instanceof RadioMenuVo) {
                    List<RadioMenuVo> radioMenuVos = d;
                    LogUtils.e(JSON.toJSONString(radioMenuVos));

                } else {
                    List<BookListVo> radioMenuVos = d;
                    LogUtils.e(JSON.toJSONString(radioMenuVos));

                }
            }
        });

    }


}
