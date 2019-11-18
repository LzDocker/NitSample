package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;

import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.vo.CircleCreateCardVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;

import javax.inject.Inject;

public class CreateListViewModel extends NitCommonContainerViewModel {


    @Inject
    public CreateListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        CircleCreateCardVo account_sample = new CircleCreateCardVo(R.mipmap.open_up_pic_icon, "创建企业圈", "企业展示", 1);
        mItems.add(account_sample);

        CircleCreateCardVo havor_sample = new CircleCreateCardVo(R.mipmap.open_up_pic_icon, "创建兴趣圈", "志同道合", 2);
        mItems.add(havor_sample);

        CircleCreateCardVo country_sample = new CircleCreateCardVo(R.mipmap.open_up_pic_icon, "创建国别圈", "志同道合", 3);
        mItems.add(country_sample);

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
