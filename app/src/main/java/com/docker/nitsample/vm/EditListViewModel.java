package com.docker.nitsample.vm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.bfhd.account.api.AccountService;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.SamplEditVo;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class EditListViewModel extends NitCommonContainerViewModel {


    @Inject
    public EditListViewModel() {
    }

    @Inject
    DbCacheUtils dbCacheUtils;

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        mServerLiveData.addSource(dbCacheUtils.loadFromDb("edit"), o -> {
            if (o != null) {
                ArrayList<String> keyslist = (ArrayList<String>) o;
                ArrayList<SamplEditVo> samplEditVos = new ArrayList<>();
                for (int i = 0; i < keyslist.size(); i++) {
                    SamplEditVo samplEditVo = new SamplEditVo(keyslist.get(i));
                    samplEditVos.add(samplEditVo);
                }
                mItems.clear();
                mItems.addAll(samplEditVos);
            }
        });
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public void portOutConfig(SamplEditVo item, View view) {
        dbCacheUtils.loadFromDb("db_tab_" + ((SamplEditVo) item).title).observe((LifecycleOwner) ActivityUtils.getTopActivity(), o -> {
            String config = GsonUtils.toJson(o);
            Log.d("sss", "portOutConfig: ===============" + config);
            ToastUtils.showLong(config);
        });
    }

    public void delCard(SamplEditVo item, View view) {
        dbCacheUtils.clearCache("db_tab_" + ((SamplEditVo) item).title, null);
        dbCacheUtils.loadFromDb("edit").observe((LifecycleOwner) ActivityUtils.getTopActivity(), o -> {
            ArrayList<String> arrayList;
            if (o != null) {
                arrayList = (ArrayList<String>) o;
                if (arrayList.contains("db_tab_" + ((SamplEditVo) item).title)) {
                    arrayList.remove("db_tab_" + ((SamplEditVo) item).title);
                }
                dbCacheUtils.save("edit", arrayList, () -> ToastUtils.showShort("删除完成"));
            }
            mItems.remove(item);
        });
    }

}
