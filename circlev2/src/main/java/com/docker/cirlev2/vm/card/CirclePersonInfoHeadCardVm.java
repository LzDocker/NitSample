package com.docker.cirlev2.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.CircleDynamicDetailCardVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class CirclePersonInfoHeadCardVm extends NitcommonCardViewModel {

    public CirclePersonInfoHeadCardVm circlePersonInfoHeadCardVm;

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CirclePersonInfoHeadCardVm() {

    }



    public void process() {
        Log.d("sss", "process: ===============================");
    }


    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {

    }

}
