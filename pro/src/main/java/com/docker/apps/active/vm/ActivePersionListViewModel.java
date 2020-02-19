package com.docker.apps.active.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.active.api.ActiveService;
import com.docker.apps.active.vo.ActivePersionVo;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

public class ActivePersionListViewModel extends NitCommonContainerViewModel {


    @Inject
    ActiveService activeService;

    public String acctivityid;

    @Inject
    public ActivePersionListViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return activeService.persionList(param);
    }


    //api.php?m=activity.updateReviewStatus

    public final MediatorLiveData<String> mPersionStatusLv = new MediatorLiveData<>();

    public void updateReviewStatus(ActivePersionVo item, char flag, View view) {
        HashMap<String, String> parm = new HashMap<>();
        parm.put("joinid", item.joinid);
        if ("1".equals(flag)) {
            parm.put("status", "-1");
        } else {
            parm.put("status", "1");
        }
        mPersionStatusLv.addSource(RequestServer(activeService.updateReviewStatus(parm)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mPersionStatusLv.setValue(parm.get("status"));
                ToastUtils.showShort("操作成功");
                if ("1".equals(flag)) {
                    item.status = 1;
                } else {
                    item.status = 2;
                }
            }
        }));
    }

    public void enterVerfity(ActivePersionVo item, View view) {
        ARouter.getInstance()
                .build(AppRouter.ACTIVE_MANAGE_VERFIC)
                .withString("activityid", acctivityid).navigation();

    }
}
