package com.docker.videobasic.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
import com.docker.videobasic.vo.VideoFullEntityVo;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class VideoListViewModel extends NitCommonContainerViewModel {

    @Inject
    VideoService videoService;


    public final MediatorLiveData<Integer> playPosLv = new MediatorLiveData();
    public final MediatorLiveData<Boolean> onrefresh = new MediatorLiveData();
    public final MediatorLiveData<HashMap<String, Object>> fullData = new MediatorLiveData<>();

    public int scope = 0;

    public View view;

    public VideoFullEntityVo mVideofull;

    public LinearLayout llplay;

    @Inject
    public VideoListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        if (scope == 0) {
            return videoService.fechCircleInfoList(apiurl, param);
        } else if (scope == 1) {
            return videoService.fechCircleDyList(apiurl, param);
        } else {
            return videoService.fechCircleFullList(apiurl, param);
        }
    }

    @Override
    public void refresh() {
        onrefresh.setValue(true);
    }

    /*
     * 条目点击事件
     * */
    public void ItemVideoClick(BaseItemModel item, View view) {
        Timber.e("=========OnItemClickListene============");
        Log.d("sss", "ItemVideoClick: ======================");
        this.view = view;
        if (view == null) {
            return;
        }
        onrefresh.setValue(true);
        String videourl = null;
        String title = null;
        String targ = null;
        if (item instanceof VideoEntityVo) {
            if (view.findViewById(R.id.layoutContainer) == null) {
                return;
            }
            videourl = ImgUrlUtil.getCompleteVideoUrl((VideoEntityVo) item);
            title = ((VideoEntityVo) item).getNickname();
            targ = ((VideoEntityVo) item).getDynamicid();
        } else if (item instanceof VideoDyEntityVo) {
            if (view.findViewById(R.id.layoutContainer) == null) {
                return;
            }
            videourl = ImgUrlUtil.getCompleteVideoUrl((VideoDyEntityVo) item);
            title = ((VideoDyEntityVo) item).getNickname();
            targ = ((VideoDyEntityVo) item).getDynamicid();
        } else if (item instanceof VideoFullEntityVo) {
            if (mVideofull == item) {
                return;
            }
            mVideofull = (VideoFullEntityVo) item;
            llplay = view.findViewById(R.id.ll_play);
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("view", view.findViewById(R.id.ll_play));
            hashMap.put("item", item);
            fullData.setValue(hashMap);
            return;
        }
        playPosLv.setValue(mItems.indexOf(item));
        DataSource dataSource = new DataSource(videourl);
        dataSource.setTitle(title);
        dataSource.setTag(targ);
        AssistPlayer.get().play(view.findViewById(R.id.layoutContainer), dataSource);

    }
}
