package com.docker.cirlev2.vm.card;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.PersonInfoHeadCardVo;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.CircleCountpageVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.BR;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.module_im.session.SessionHelper;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class CirclePersonInfoHeadCardVm extends NitcommonCardViewModel {

    public PersonInfoHeadCardVo personInfoHeadVo;

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
        if (personInfoHeadVo != null) {
            loadCardData(personInfoHeadVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        this.personInfoHeadVo = (PersonInfoHeadCardVo) accountHeadCardVo;
        LiveData<Resource<PersonInfoHeadVo>> responseLiveData = RequestServer(circleApiService.circlePersionCount(personInfoHeadVo.mRepParamMap));
        personInfoHeadVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<PersonInfoHeadVo>() {
                    @Override
                    public void onComplete(Resource<PersonInfoHeadVo> resource) {
                        super.onComplete(resource);
                        personInfoHeadVo.mCardVoLiveData.setValue(resource.data);
                        personInfoHeadVo.mCardVoLiveData.removeSource(responseLiveData);
                        personInfoHeadVo.setDatasource(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<PersonInfoHeadVo> resource) {
                        super.onNetworkError(resource);
                        personInfoHeadVo.mCardVoLiveData.setValue(null);
                    }
                }));

    }


    public final MediatorLiveData<String> mAttenLv = new MediatorLiveData<>();

    // 关注
    public void attention(PersonInfoHeadVo personInfoHeadVo, View view) {
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("memberid2", personInfoHeadVo.getMemberid());
        params.put("uuid2", personInfoHeadVo.getUuid());
        params.put("uuid", userInfoVo.uuid);
        if (!TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("nickname", userInfoVo.nickname);
        } else {
            params.put("nickname", "匿名");
        }
        if (personInfoHeadVo.getIsFocus() == 1) {
            params.put("status", "0");
        } else {
            params.put("status", "1");
        }
        mAttenLv.addSource(
                RequestServer(
                        circleApiService.attention(params)), new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mAttenLv.setValue(resource.data);
                        if (personInfoHeadVo.getIsFocus() == 1) {
                            personInfoHeadVo.setIsFocus(0);
                        } else {
                            personInfoHeadVo.setIsFocus(1);
                        }
                        personInfoHeadVo.notifyPropertyChanged(BR.isFocus);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 聊一聊
    public void toIm(PersonInfoHeadVo personInfoHeadVo, View view) {
        if (personInfoHeadVo == null) {
            return;
        }
        SessionHelper.startP2PSession(ActivityUtils.getTopActivity(), personInfoHeadVo.getUuid());
    }


}
