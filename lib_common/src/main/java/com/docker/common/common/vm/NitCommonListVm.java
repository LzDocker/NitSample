package com.docker.common.common.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.view.View;

import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.common.BR;
import com.docker.common.common.command.ReponseCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

    public CommonListOptions mCommonListReq;
    public ObservableBoolean bdenable = new ObservableBoolean(false);
    public ObservableBoolean bdenablemore = new ObservableBoolean(false);
    public ObservableBoolean bdenablenodata = new ObservableBoolean(false);
    public ObservableBoolean bdenablerefresh = new ObservableBoolean(true);
    public ObservableBoolean loadingOV = new ObservableBoolean(false);

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
    public OnItemBind<BaseItemModel> mutipartItemsBinding = (itemBinding, position, item) -> {
        itemBinding.set(BR.item, item.getItemLayout());
    };
    // itembinding
    public ItemBinding<BaseItemModel> itemBinding = ItemBinding.of(mutipartItemsBinding)
            .bindExtra(BR.viewmodel, this);


    private void setLoadControl(boolean enable) {
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

    public void addCardVo(BaseSampleItem sampleItem, int position) {
        if (mItems.size() == 0 && !loadingOV.get()) {
            loadData();
        }
        if (mItems.size() > 0 && mItems.size() >= position) {
            mItems.add(position, sampleItem);
        } else {
            mItems.add(sampleItem);
        }
    }

    public void loadCardData(CommonListOptions commonListOptions) {

    }

}
