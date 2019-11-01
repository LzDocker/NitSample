package com.docker.videobasic.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.view.View;

import com.docker.common.BR;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.video.assist.AssistPlayer;
import com.docker.video.entity.DataSource;
import com.docker.videobasic.R;
import com.docker.videobasic.api.VideoService;
import com.docker.videobasic.util.ImgUrlUtil;
import com.docker.videobasic.vo.VideoEntityVo;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class VideoListViewModel extends NitCommonContainerViewModel {

    @Inject
    VideoService videoService;


    public final MediatorLiveData<Integer> playPosLv = new MediatorLiveData();
    public final MediatorLiveData<Boolean> onrefresh = new MediatorLiveData();

    public View view;

    @Inject
    public VideoListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return videoService.fechCircleInfoList(apiurl, param);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onrefresh.setValue(true);
    }

    /*
     * 条目点击事件
     * */
    public void ItemVideoClick(BaseItemModel item, View view) {
        this.view = view;
        Timber.e("=========OnItemClickListene============");
        ((VideoEntityVo) item).setPlayer(true);
        ((VideoEntityVo) item).notifyPropertyChanged(BR.player);
        DataSource dataSource = new DataSource(ImgUrlUtil.getCompleteVideoUrl((VideoEntityVo) item));
        dataSource.setTitle(((VideoEntityVo) item).getNickname());
        AssistPlayer.get().play(view.findViewById(R.id.layoutContainer), dataSource);
        playPosLv.setValue(mItems.indexOf(item));
    }

}
