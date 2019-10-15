package com.bfhd.circle.vm;


import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.PublishImgSpeicalVo;
import com.bfhd.circle.vo.PublishRstVo;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CirclePublishViewModel extends HivsBaseViewModel {

    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    public CirclePublishViewModel() {

    }

    @Override
    public void initCommand() {
    }


    public void getData() {

    }


    public void publishImgToserver(File file) {
        showDialogWait("上传中...", false);
        String fileNameByTimeStamp = System.currentTimeMillis() + ".jpg";
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", fileNameByTimeStamp, requestFile);
        mResourceLiveData.addSource(
                commonRepository.SpecialFeatch(
                        circleService.publishImgToServer(body)),
                new HivsNetBoundObserver<>(new NetBoundCallback<PublishImgSpeicalVo>(this) {
                    @Override
                    public void onComplete(Resource<PublishImgSpeicalVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(103, null, resource.data));
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
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.publishNews(paramMap)),
                new HivsNetBoundObserver<>(new NetBoundCallback<PublishRstVo>(this) {
                    @Override
                    public void onComplete(Resource<PublishRstVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(104, null, resource.data));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


}

