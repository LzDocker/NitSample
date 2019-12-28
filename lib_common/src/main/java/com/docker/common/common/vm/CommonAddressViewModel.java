package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;
import android.view.View;

import com.docker.common.api.OpenService;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.AddressVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.di.netmodule.CommonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CommonAddressViewModel extends NitCommonContainerViewModel {


    @Inject
    OpenService openService;


    @Inject
    public CommonAddressViewModel() {

    }


    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);

        AddressVo addressVo = new AddressVo(0, 0);
        addressVo.setAddress("111111111");
        addressVo.setId("111111111");
        addressVo.setIs_moren("111111111");
        addressVo.setLocation("111111111");
        addressVo.setName("111111111");
        addressVo.setPhone("111111111");
        addressVo.setRegion1("111111111");
        addressVo.setRegion2("111111111");
        addressVo.setRegion3("111111111");

        AddressVo addressVo1 = new AddressVo(0, 0);
        addressVo1.setAddress("222222");
        addressVo1.setId("222222222");
        addressVo1.setIs_moren("2222222");
        addressVo1.setLocation("22222222");
        addressVo1.setName("2222222222");
        addressVo1.setPhone("2222222");
        addressVo1.setRegion1("222222222");
        addressVo1.setRegion2("22222222");
        addressVo1.setRegion3("222222222");

        List<AddressVo> addressVoList = new ArrayList<>();
        addressVoList.add(addressVo);
        addressVoList.add(addressVo1);
        mItems.addAll(addressVoList);
    }

    public void ItemClick(AddressVo item, View view) {
        String itemId = item.getId();
        for (int i = 0; i < mItems.size(); i++) {
            AddressVo addressVo = (AddressVo) mItems.get(i);
            String id = addressVo.getId();
            if (itemId.equals(id)) {
                if (addressVo.isCheck()) {
                    addressVo.setCheck(false);
                } else {
                    addressVo.setCheck(true);
                }
                addressVo.notifyChange();
            }

        }

    }

    /**
     * 编辑地址
     * @param item
     * @param view
     */
    public void editItemClick(AddressVo item, View view) {

    }

    public void allSelectClick() {

    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        return null;
    }


}
