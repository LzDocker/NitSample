package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.common.BR;
import com.docker.common.common.command.ReponseCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import retrofit2.Call;
import timber.log.Timber;

public abstract class NitCommonListVm<T> extends NitCommonVm {

    public int mPage = 1;
    public int mPageSize = 20;


    public ArrayList<BaseCardVo> mProvideredCardVos = new ArrayList<>();
    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mServerLiveData = new MediatorLiveData<Object>();

    public CommonListOptions mCommonListReq;
    public ObservableBoolean bdenable = new ObservableBoolean(false);
    public ObservableBoolean bdenablemore = new ObservableBoolean(false);
    public ObservableBoolean bdenablenodata = new ObservableBoolean(false);
    public ObservableBoolean bdenablerefresh = new ObservableBoolean(true);
    public ObservableBoolean loadingOV = new ObservableBoolean(false);
    public LiveData<ApiResponse<BaseResponse<T>>> servicefun = null;

    public void initParam(CommonListOptions commonListReq) {
        this.mCommonListReq = commonListReq;
    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            mPage = 1;
            loadData();
            refresh();
        });
        mCommand.OnLoadMore(() -> {
            loadData();
        });
        mCommand.OnRetryLoad(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
            loadData();
        });
    }

    // 列表数据源
    public ObservableList<BaseItemModel> mItems = new ObservableArrayList<>();
    // 多类型条目适配
    public OnItemBind<BaseItemModel> mutipartItemsBinding = (ItemBinding itemBinding, int position, BaseItemModel item) -> {
        if (item instanceof BaseSampleItem) {
            ((BaseSampleItem) item).index = position;
        }
        itemBinding.set(BR.item, item.getItemLayout());
    };
    // itembinding
    public ItemBinding<BaseItemModel> itemBinding = ItemBinding.of(mutipartItemsBinding).bindExtra(BR.viewmodel, this);

    public void setLoadControl(boolean enable) {
        switch (mCommonListReq.refreshState) {
            case Constant.KEY_REFRESH_OWNER:
                bdenable.set(enable);
                bdenablemore.set(enable);
                bdenable.notifyChange();
                bdenablemore.notifyChange();
                break;
            case Constant.KEY_REFRESH_ONLY_LOADMORE:
                bdenablemore.set(enable);
                bdenablemore.notifyChange();
                bdenablerefresh.set(false);
                bdenablerefresh.notifyChange();
                break;
            case Constant.KEY_REFRESH_NOUSE:
                bdenablemore.set(false);
                bdenablemore.notifyChange();
                bdenablerefresh.set(false);
                bdenablerefresh.notifyChange();
                break;
        }
    }

    public void loadData() {
        mCommonListReq.ReqParam.put("page", String.valueOf(mPage));
        servicefun = getServicefun(mCommonListReq.ApiUrl, mCommonListReq.ReqParam);
        if (servicefun == null) {
            Timber.e("必须提供请求server");
            return;
        }
        mServerLiveData.addSource(
                commonRepository.noneChache(
                        servicefun), new NitNetBoundObserver<>(new NitBoundCallback<T>() {
                    @Override
                    public void onLoading(Call call) {
                        super.onLoading(call);
                    }

                    @Override
                    public void onLoading() {
                        super.onLoading();
                        loadingState = true;
                        loadingOV.set(true);
                        loadingOV.notifyChange();
                        if (mPage == 1) {
                            if (mIsfirstLoad) {
                                mEmptycommand.set(EmptyStatus.BdLoading);
                                setLoadControl(false);
                            } else {
                                mEmptycommand.set(EmptyStatus.BdHiden);
                                setLoadControl(true);
                            }
                        }
                    }

                    @Override
                    public void onComplete(Resource<T> resource) {
                        super.onComplete(resource);
                        loadingOV.set(false);
                        loadingOV.notifyChange();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                        loadingState = false;
                        mIsfirstLoad = false;
                        if (mPage == 1) {
                            mItems.clear();
                        }
                        setLoadControl(true);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                        formartData(resource);
                        if (resource.data instanceof List) {
                            if (((List) resource.data).size() == 0 || ((List) resource.data).size() < mPageSize) {
                                bdenablenodata.set(true);
                            } else {
                                bdenablenodata.set(false);
                            }
                            bdenablenodata.notifyChange();
                            if (!CollectionUtils.isEmpty((Collection) resource.data)) {
                                mItems.addAll(formatListData((Collection<? extends BaseItemModel>) resource.data));
                            }
                        } else {
                            if (resource.data != null) {
                                mItems.add(formatData((BaseItemModel) resource.data));
                            }
                        }
                        // 添加card
                        if (mCardTreeMap.size() > 0) {
                            ArrayList<Integer> keysAddList = new ArrayList();
                            Iterator<Integer> treemap = mCardTreeMap.keySet().iterator();
                            while (treemap.hasNext()) {
                                int position = treemap.next();
                                BaseSampleItem sampleItem = mCardTreeMap.get(position);
                                if (mItems.size() > 0 && mItems.size() >= position) {
                                    mItems.add(position, sampleItem);
                                    keysAddList.add(position);
                                }
                            }
                            for (int i = 0; i < keysAddList.size(); i++) {
                                mCardTreeMap.remove(keysAddList.get(i));
                            }
                        }
                        //


                        if (resource.data != null) {
                            mPage++;
                        }
                        if (mItems.size() == 0) { // 暂无数据
                            mEmptycommand.set(EmptyStatus.BdEmpty);
                            setLoadControl(false);
                        } else {
                            setLoadControl(true);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        loadingState = false;
                        loadingOV.set(false);
                        loadingOV.notifyChange();
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onBusinessError(Resource<T> resource) {
                        super.onBusinessError(resource);
                        setLoadControl(false);
                    }

                    @Override
                    public void onNetworkError(Resource<T> resource) {
                        super.onNetworkError(resource);
                        setLoadControl(false);
                        if (mPage == 1) {
                            mEmptycommand.set(EmptyStatus.BdError);
                        }
                    }
                }));

    }

    public void formartData(Resource<T> resource) {

    }
    public void addData(BaseItemModel baseItemModel) {
        mItems.add(baseItemModel);
    }

    public void removeData(BaseItemModel baseItemModel) {
        mItems.remove(baseItemModel);
    }

    public void updateData(int position, BaseItemModel baseItemModel) {
        mItems.set(position, baseItemModel);
    }

    public List<BaseItemModel> searchData(ReponseCommand<List<BaseItemModel>> reponseCommand) {
        return reponseCommand.exectue();
    }


    /*
     * 服务端接口
     * */
    public LiveData<ApiResponse<BaseResponse<T>>> getServicefun(String apiurl, HashMap<String, String> param) {
        return null;
    }

    /*
     * 条目点击事件
     * */
    public void ItemClick(BaseItemModel item, View view) {

    }


    /*
     * 刷新事件
     * */
    public void refresh() {
        if (mProvideredCardVos.size() > 0) {
            onOuterVmRefresh();
        }
    }

    /*
     *
     *格式化list类型数据
     * */
    public abstract Collection<? extends BaseItemModel> formatListData(Collection<? extends BaseItemModel> data);


    /*
     *格式化单个数据
     * */
    public abstract BaseItemModel formatData(BaseItemModel data);


    public HashMap<Integer, BaseSampleItem> mCardTreeMap = new HashMap<>();

    public void addCardVo(BaseSampleItem sampleItem, int position, boolean isadd) {
        Log.d("zxd", "addCardVo:===========position =====" + position);
        if (sampleItem instanceof BaseCardVo && isadd) {
            mProvideredCardVos.add((BaseCardVo) sampleItem);
        }
        int i = 0;
        if (mItems.size() == 0 && !loadingOV.get()) {
            loadData();
            if (servicefun == null) {
                if (mItems.size() >= position) {
                    mItems.add(position, sampleItem);
                } else {
                    mItems.add(sampleItem);
                }
                i = 1;
            } else {
                if (mCardTreeMap.containsKey(position)) {
                    position++;
                    ((BaseCardVo) sampleItem).position = position;
                    i = 2;
                }
                mCardTreeMap.put(position, sampleItem);

            }
        } else if (mItems.size() > 0 && servicefun != null) {
            if (mPage == 1 && !isadd) {
                mCardTreeMap.put(position, sampleItem);
            } else if (mItems.size() > position) {
                mItems.add(position, sampleItem);
                i = 32;
            } else {
                mCardTreeMap.put(position, sampleItem);
                i = 31;
            }
        } else {
            if (mItems.size() >= position && servicefun == null) {
                mItems.add(position, sampleItem);
                i = 4;
            } else {
                if (sampleItem instanceof BaseCardVo) {
                    i = 5;
                    if (mCardTreeMap.size() > 0) {
                        if (mCardTreeMap.containsKey(position)) {
                            position++;
                            ((BaseCardVo) sampleItem).position = position;
                        }
                        mCardTreeMap.put(position, sampleItem);
                    } else {
                        if (loadingOV.get()) {
                            if (mCardTreeMap.containsKey(position)) {
                                position++;
                                ((BaseCardVo) sampleItem).position = position;
                            }
                            mCardTreeMap.put(position, sampleItem);
                            i = 7;
                        } else {
                            mItems.add(sampleItem);
                            i = 8;
                        }
                    }
                } else {
                    i = 6;
                    mItems.add(sampleItem);
                }
            }
        }

        Log.d("zxd", "addCardVo: ====================" + i);
    }

    public void loadCardData(BaseCardVo baseCardVo) {

    }

    public void onOuterVmRefresh() {
        for (int i = 0; i < mProvideredCardVos.size(); i++) {
            this.addCardVo(mProvideredCardVos.get(i), mProvideredCardVos.get(i).position, false);
            if (!TextUtils.isEmpty(mProvideredCardVos.get(i).mVmPath) && mProvideredCardVos.get(i).mNitcommonCardViewModel != null) {
                mProvideredCardVos.get(i).mNitcommonCardViewModel.loadCardData(mProvideredCardVos.get(i));
            }
        }
    }

    // 只刷新不添加
    public void onJustRefresh() {
        for (int i = 0; i < mProvideredCardVos.size(); i++) {
            if (!TextUtils.isEmpty(mProvideredCardVos.get(i).mVmPath) && mProvideredCardVos.get(i).mNitcommonCardViewModel != null) {
                mProvideredCardVos.get(i).mNitcommonCardViewModel.loadCardData(mProvideredCardVos.get(i));
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mProvideredCardVos.clear();
        mProvideredCardVos = null;
        mItems.clear();
        mItems = null;
    }
}
