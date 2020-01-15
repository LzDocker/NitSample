package com.docker.apps.intvite.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.docker.apps.intvite.api.InviteService;
import com.docker.apps.intvite.vo.InviteDataVo;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

public class ProInviteVm extends NitCommonVm {


    @Inject
    InviteService inviteService;

    @Inject
    public ProInviteVm() {

    }

    public MediatorLiveData<InviteDataVo> inviteDataVoMediatorLiveData = new MediatorLiveData<>();


    public void fechInviteData(HashMap<String, String> param) {
        inviteDataVoMediatorLiveData.addSource(RequestServer(inviteService.fechInviteData(param)), new NitNetBoundObserver<InviteDataVo>(new NitBoundCallback<InviteDataVo>() {
            @Override
            public void onComplete(Resource<InviteDataVo> resource) {
                super.onComplete(resource);
                inviteDataVoMediatorLiveData.setValue(resource.data);
            }

            @Override
            public void onNetworkError(Resource<InviteDataVo> resource) {
                super.onNetworkError(resource);
                inviteDataVoMediatorLiveData.setValue(null);
            }

            @Override
            public void onBusinessError(Resource<InviteDataVo> resource) {
                super.onBusinessError(resource);
                inviteDataVoMediatorLiveData.setValue(null);
            }
        }));
    }


}
