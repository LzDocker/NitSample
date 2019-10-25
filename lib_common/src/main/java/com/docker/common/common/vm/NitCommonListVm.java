package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.view.View;

import com.docker.common.BR;
import com.docker.common.common.command.ReponseCommand;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.httpmodule.ApiResponse;
import com.docker.core.di.httpmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import retrofit2.Call;
import timber.log.Timber;

public abstract class NitCommonListVm<T> extends NitCommonVm {

    public int mPage = 1;
    public int mPageSize = 20;
    /*
     * vm 数据源
     * */
    public final MediatorLiveData<Object> mServerLiveData = new MediatorLiveData<Object>();

    public final ObservableBoolean mRefreshStateLiveData = new ObservableBoolean();
    public final ObservableBoolean mRefreshStateNodataLiveData = new ObservableBoolean();
    public CommonListOptions mCommonListReq;
    public ObservableBoolean bdenable = new ObservableBoolean(false);
    public ObservableBoolean bdenablemore = new ObservableBoolean(false);

    public void initParam(CommonListOptions commonListReq) {
        this.mCommonListReq = commonListReq;
    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            mPage = 1;
            loadData();
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
    public OnItemBind<BaseItemModel> mutipartItemsBinding = (itemBinding, position, item) -> {
        itemBinding.set(BR.item, item.getItemLayout());
    };
    // itembinding
    public ItemBinding<BaseItemModel> itemBinding = ItemBinding.of(mutipartItemsBinding)
            .bindExtra(BR.viewmodel, this);


    private void setLoadControl(boolean enable) {
        switch (mCommonListReq.refreshState) {
            case 0:
                bdenable.set(enable);
                bdenablemore.set(enable);
                bdenable.notifyChange();
                bdenablemore.notifyChange();
                break;
            case 1:
                bdenablemore.set(enable);
                bdenablemore.notifyChange();
                break;
        }
    }

    public void loadData() {
        LiveData<ApiResponse<BaseResponse<T>>> servicefun = null;
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
                        mRefreshStateLiveData.set(false);
                        mRefreshStateLiveData.notifyChange();
                    }

                    @Override
                    public void onLoading() {
                        super.onLoading();
                        loadingState = true;
                        if (mPage == 1) {
                            mRefreshStateNodataLiveData.set(false);
                            mRefreshStateNodataLiveData.notifyChange();
                            if (mIsfirstLoad) {
                                mEmptycommand.set(EmptyStatus.BdLoading);
                                setLoadControl(false);
                            } else {
                                setLoadControl(true);
                            }
                        }
                    }

                    @Override
                    public void onComplete(Resource<T> resource) {
                        super.onComplete(resource);
                        loadingState = false;
                        mIsfirstLoad = false;
                        if (mPage == 1) {
                            mItems.clear();
                        }
                        if (resource.data != null) {
                            setLoadControl(true);
                            mEmptycommand.set(EmptyStatus.BdHiden);
                            if (resource.data instanceof List) {
                                if (((List) resource.data).size() == 0 || ((List) resource.data).size() < mPageSize) {
                                    mRefreshStateNodataLiveData.set(true);
                                } else {
                                    mRefreshStateNodataLiveData.set(false);
                                    mItems.addAll((Collection<? extends BaseItemModel>) resource.data);
                                }
                                mRefreshStateNodataLiveData.notifyChange();
                            } else {
                                mItems.add((BaseItemModel) resource.data);
                                setLoadControl(true);
                            }
                            mPage++;
                        } else {
                            if (mItems.size() == 0) { // 暂无数据
                                mEmptycommand.set(EmptyStatus.BdEmpty);
                            } else {
                                mRefreshStateNodataLiveData.set(true);
                                mRefreshStateNodataLiveData.notifyChange();
                            }
                            setLoadControl(false);
                        }
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mRefreshStateLiveData.set(true);
                        mRefreshStateLiveData.notifyChange();
                        loadingState = false;
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
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                        setLoadControl(false);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }
                }));

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

}
