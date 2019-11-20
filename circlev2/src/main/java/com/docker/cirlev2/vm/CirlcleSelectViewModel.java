package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.cirlev2.vo.entity.CirclePubSelectVo;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.entity.NetImgVo;
import com.docker.cirlev2.vo.entity.NetImgWapperVo;
import com.docker.cirlev2.vo.entity.PublishImgSpeicalVo;
import com.docker.cirlev2.vo.entity.PublishRstVo;
import com.docker.cirlev2.vo.entity.RstVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.model.BaseItemModel;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CirlcleSelectViewModel extends NitCommonContainerViewModel {


    public final MediatorLiveData<CirclePubSelectVo> selectVoMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public CirlcleSelectViewModel() {

    }

    @Inject
    CircleApiService circleApiService;


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return circleApiService.fechJoinSelectCircle(param);
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {

        if (mCommonListReq.externs.get("default") != null) {
            for (int i = 0; i < data.size(); i++) {
                CirclePubSelectVo circlePubSelectVo = ((List<CirclePubSelectVo>) data).get(i);
                if (mCommonListReq.externs.get("default").equals(circlePubSelectVo)) {
                    circlePubSelectVo.setSelectsource(R.mipmap.class_check_true);
                }
                break;
            }
            return data;
        } else {
            return super.formatListData(data);
        }
    }

    public void onItemClick(CirclePubSelectVo item, View view, CirlcleSelectViewModel cirlcleSelectViewModel) {
        for (int i = 0; i < cirlcleSelectViewModel.mItems.size(); i++) {
            ((CirclePubSelectVo) cirlcleSelectViewModel.mItems.get(i)).setSelectsource(R.mipmap.class_check_false);
        }
        item.setSelectsource(R.mipmap.class_check_true);
        selectVoMediatorLiveData.setValue(item);
    }


}
