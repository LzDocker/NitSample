package com.docker.videobasic.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.view.View;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.video.assist.AssistPlayer;
import com.docker.video.entity.DataSource;
import com.docker.videobasic.R;
import com.docker.videobasic.api.VideoService;
import com.docker.videobasic.util.ImgUrlUtil;
import com.docker.videobasic.vo.VideoDyEntityVo;
import com.docker.videobasic.vo.VideoEntityVo;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class VideoListViewModel extends NitCommonContainerViewModel {

    @Inject
    VideoService videoService;


    public final MediatorLiveData<Integer> playPosLv = new MediatorLiveData();
    public final MediatorLiveData<Boolean> onrefresh = new MediatorLiveData();

    public int scope = 0;

    public View view;

    @Inject
    public VideoListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        if (scope == 0) {
            return videoService.fechCircleInfoList(apiurl, param);
        }
        return videoService.fechCircleDyList(apiurl, param);
    }

    @Override
    public void refresh() {
        onrefresh.setValue(true);

    }

    /*
     * 条目点击事件
     * */
    public void ItemVideoClick(BaseItemModel item, View view) {
        this.view = view;
        Timber.e("=========OnItemClickListene============");
//        ((VideoEntityVo) item).setPlayer(true);
//        ((VideoEntityVo) item).notifyPropertyChanged(BR.player);
        String videourl;
        String title;
        if (item instanceof VideoEntityVo) {
            videourl = ImgUrlUtil.getCompleteVideoUrl((VideoEntityVo) item);
            title = ((VideoEntityVo) item).getNickname();
        } else {
            videourl = ImgUrlUtil.getCompleteVideoUrl((VideoDyEntityVo) item);
            title = ((VideoDyEntityVo) item).getNickname();
        }
        DataSource dataSource = new DataSource(videourl);
        dataSource.setTitle(title);
        AssistPlayer.get().play(view.findViewById(R.id.layoutContainer), dataSource);
        playPosLv.setValue(mItems.indexOf(item));
    }

  /*  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.d("sss", "create: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.d("sss", "start: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        Log.d("sss", "resume: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        Log.d("sss", "pause: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.d("sss", "stop: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        Log.d("sss", "destory: ================");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void any() {
        Log.d("sss", "any: =============");
    }*/


}
