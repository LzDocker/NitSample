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


}
