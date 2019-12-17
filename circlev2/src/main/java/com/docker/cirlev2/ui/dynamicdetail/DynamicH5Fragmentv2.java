package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5V3Binding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.widget.nested.InfoBean;
import com.docker.cirlev2.widget.nested.RvAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class DynamicH5Fragmentv2 extends NitCommonFragment<SampleListViewModel, Circlev2FragmentDetailH5V3Binding> {

    public ServiceDataBean serviceDataBean;

    public static DynamicH5Fragmentv2 getInstance(ServiceDataBean serviceDataBean) {
        DynamicH5Fragmentv2 dynamicH5Fragment = new DynamicH5Fragmentv2();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_h5_v3;
    }

    @Override
    protected SampleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);
//
//        CommonListOptions commonListOptions = new CommonListOptions();
//        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
//        commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
//        commonListOptions.falg = 1002;
//        commonListOptions.ReqParam.put("uuid", "420cd8fd09e4ae6cfb8f3b3fdf5b7af4");
//        commonListOptions.ReqParam.put("memberid", "67");
//        commonListOptions.ReqParam.put("companyid", "1");
//        NitBaseProviderCard.providerCoutainerForFrame(getChildFragmentManager(), R.id.frame_content, commonListOptions);


        initWebView();
        initRecyclerView();


    }


    private void initWebView() {
        mBinding.get().webContainer.getSettings().setJavaScriptEnabled(true);
        mBinding.get().webContainer.setWebViewClient(new WebViewClient());
        mBinding.get().webContainer.setWebChromeClient(new WebChromeClient());
        mBinding.get().webContainer.loadUrl("https://github.com/wangzhengyi/Android-NestedDetail");
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getHoldingActivity());
        mBinding.get().rvList.setLayoutManager(layoutManager);
        List<InfoBean> data = getCommentData();
        final RvAdapter rvAdapter = new RvAdapter(this.getHoldingActivity(), data);
        mBinding.get().rvList.setAdapter(rvAdapter);

        mBinding.get().refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                rvAdapter.setmData(getCommentData());
                mBinding.get().refresh.finishLoadMore();
            }
        });

//        mBinding.get().tvTtt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rvAdapter.setmData(getCommentData());
//
//            }
//        });
    }

    private List<InfoBean> getCommentData() {
        List<InfoBean> commentList = new ArrayList<>();
        InfoBean titleBean = new InfoBean();
        titleBean.type = InfoBean.TYPE_TITLE;
        titleBean.title = "评论列表";
        commentList.add(titleBean);
        for (int i = 0; i < 20; i++) {
            InfoBean contentBean = new InfoBean();
            contentBean.type = InfoBean.TYPE_ITEM;
            contentBean.title = "评论标题" + i;
            contentBean.content = "评论内容" + i;
            commentList.add(contentBean);
        }
        return commentList;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand;
        nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleDynamicDetailViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonListFragment) {
//                mCardframevm = commonListVm;
//                mCardFragment = (NitCommonContainerFragmentV2) nitCommonListFragment;

            }
        };
        return nitDelegetCommand;
    }


}
