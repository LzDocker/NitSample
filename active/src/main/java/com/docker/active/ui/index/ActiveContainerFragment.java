package com.docker.active.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.active.R;
import com.docker.active.BR;
import com.docker.active.databinding.ProActiveCoutainerFragmentBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.active.vo.FilterVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.XPopup.FilterPopupView;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

public class ActiveContainerFragment extends NitCommonFragment<ActiveCommonViewModel, ProActiveCoutainerFragmentBinding> {

    Fragment fragment;

    FilterPopupView filterPopupView;

    private NitAbsSampleAdapter mFilterAdapter;

    public ActiveCommonViewModel innerVm;


    public HashMap<String, String> reqparam = new HashMap<>();

    public static ActiveContainerFragment getInstance(HashMap<String, String> stringHashMap) {
        ActiveContainerFragment fragment = new ActiveContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("reqparam", stringHashMap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_coutainer_fragment;
    }

    @Override
    protected ActiveCommonViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }


    public void onSearching(String keywords) {
        ArrayList<Pair<String, String>> pairs = new ArrayList<>();
        HashMap<String, String> orderbymap = new HashMap<>();
        orderbymap.put("title", keywords);
        pairs.add(new Pair<>("filter", GsonUtils.toJson(orderbymap)));
        ((NitCommonListFragment) fragment).updateReqParamV2(null, pairs);
        ((NitCommonListFragment) fragment).onReFresh(null);
    }

    public void upDate(String cityCode, String cityname) {
        if (fragment != null) {
            ArrayList<Pair<String, String>> pairs = new ArrayList<>();
            HashMap<String, String> filtermap = new HashMap<>();
            filtermap.put("cityCode", cityCode);
            pairs.add(new Pair<>("filter", GsonUtils.toJson(filtermap)));
            ((NitCommonListFragment) fragment).updateReqParamV2(null, pairs);
            ((NitCommonListFragment) fragment).onReFresh(null);
        }
        mBinding.get().tvSortArea.setText(cityname);
    }

    private Disposable disposable;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reqparam = (HashMap<String, String>) getArguments().getSerializable("reqparam");
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.ReqParam = reqparam;
        commonListOptions.isActParent = false;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCoutainerForFrame(getChildFragmentManager(), R.id.frame_active, commonListOptions);

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("activedel")
                    || rxEvent.getT().equals("activeStusUpdate")
                    || rxEvent.getT().equals("active_refresh")
                    || rxEvent.getT().equals("activemodify")) {
                ((NitCommonFragment) fragment).onReFresh(null);
            }
        });
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;

        nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActiveCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innerVm = (ActiveCommonViewModel) commonListVm;
                innerVm.apiType = 1;
                fragment = nitCommonFragment;

            }
        };

        return nitDelegetCommand;
    }

    @Override
    protected void initView(View var1) {

        initFilterAdapter();

        mBinding.get().tvSortZone.setOnClickListener(v -> {
            if (filterPopupView == null) {
                filterPopupView =
                        (FilterPopupView) new XPopup.Builder(ActiveContainerFragment.this.getHoldingActivity())
                                .atView(mBinding.get().llFilter)
                                .asCustom(new FilterPopupView(ActiveContainerFragment.this.getHoldingActivity()));
                filterPopupView.providerPopAdapter(mFilterAdapter);
            }
            if (!filterPopupView.isShow()) {
                filterPopupView.show();
            }
        });

        mBinding.get().tvSortArea.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(this.getHoldingActivity(), 3001);
        });
    }

    private void initFilterAdapter() {
        mFilterAdapter = new NitAbsSampleAdapter(R.layout.pro_active_item_filter, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
            }
        };
        FilterVo filterVo0 = new FilterVo();
        filterVo0.title = "综合排序";
        filterVo0.state = 0;
        filterVo0.setChecked(true);

        FilterVo filterVo = new FilterVo();
        filterVo.title = "最新发布";
        filterVo.state = 1;

        FilterVo filterVo1 = new FilterVo();
        filterVo1.title = "最多参与";
        filterVo1.state = 2;


        mFilterAdapter.add(filterVo0);
        mFilterAdapter.add(filterVo);
        mFilterAdapter.add(filterVo1);

        mFilterAdapter.setOnItemClickListener((view, position) -> {
            filterPopupView.dismiss();
            mBinding.get().tvSortZone.setText(((FilterVo) mFilterAdapter.getItem(position)).title);

            switch (((FilterVo) mFilterAdapter.getItem(position)).state) {
                case 0:
                    ((NitCommonListVm) (((NitCommonListFragment) fragment).mViewModel)).mCommonListReq.ReqParam.put("orderBy", "");
                    ArrayList<Pair<String, String>> pairs = new ArrayList<>();
                    HashMap<String, String> orderbymap = new HashMap<>();
                    orderbymap.put("is_recommend", "desc");
                    orderbymap.put("inputtime", "desc");
                    pairs.add(new Pair<>("orderBy", GsonUtils.toJson(orderbymap)));
                    ((NitCommonListFragment) fragment).updateReqParamV2(null, pairs);
                    break;
                case 1:
                    ((NitCommonListVm) (((NitCommonListFragment) fragment).mViewModel)).mCommonListReq.ReqParam.put("orderBy", "");
                    ArrayList<Pair<String, String>> pairs1 = new ArrayList<>();
                    HashMap<String, String> orderbymap1 = new HashMap<>();
                    orderbymap1.put("inputtime", "desc");
                    pairs1.add(new Pair<>("orderBy", GsonUtils.toJson(orderbymap1)));
                    ((NitCommonListFragment) fragment).updateReqParamV2(null, pairs1);
                    break;
                case 2:

                    ((NitCommonListVm) (((NitCommonListFragment) fragment).mViewModel)).mCommonListReq.ReqParam.put("orderBy", "");
                    ArrayList<Pair<String, String>> pairs2 = new ArrayList<>();
                    HashMap<String, String> orderbymap2 = new HashMap<>();
                    orderbymap2.put("enrollNum", "desc");
                    pairs2.add(new Pair<>("orderBy", GsonUtils.toJson(orderbymap2)));
                    ((NitCommonListFragment) fragment).updateReqParamV2(null, pairs2);

                    break;
            }

            ((NitCommonListFragment) fragment).onReFresh(null);

            for (int i = 0; i < mFilterAdapter.getmObjects().size(); i++) {
                ((FilterVo) mFilterAdapter.getmObjects().get(i)).setChecked(false);
            }
            ((FilterVo) mFilterAdapter.getmObjects().get(position)).setChecked(true);
        });
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK) {
            WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
            ((NitCommonListFragment) fragment).onReFresh(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
