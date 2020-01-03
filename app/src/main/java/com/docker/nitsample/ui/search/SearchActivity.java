package com.docker.nitsample.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.RstServerVo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.core.BR;
import com.docker.nitsample.R;
import com.docker.nitsample.adapter.FlowAdapter;
import com.docker.nitsample.databinding.ActivitySearchBinding;
import com.docker.nitsample.vm.SearchViewModel;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.dcbfhd.utilcode.utils.ConvertUtils.dp2px;

/*
 * 搜索
 * */

@Route(path = AppRouter.App_SEARCH_index_TYGS)
public class SearchActivity extends NitCommonActivity<SearchViewModel, ActivitySearchBinding> {

    private String keyword = "";

    private boolean isSearching = false;

    @Autowired()
    public String t = "-1";   // type

    private List<RstServerVo> hotItemVoList;

    @Inject
    ViewModelProvider.Factory factory;
    private NitAbsSampleAdapter hivsSampleAdapter;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public SearchViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SearchViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    UserInfoVo userInfoVo = new UserInfoVo();// CacheUtils.getUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        mToolbar.hide();
        userInfoVo.memberid = "1";
        userInfoVo.uuid = "1";
        mBinding.llHistoryList.setVisibility(View.VISIBLE);
        mBinding.llHotList.setVisibility(View.VISIBLE);
        mBinding.llSearchResult.setVisibility(View.VISIBLE);
        initTab();
    }


    private void initTab() {


        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation());
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation());

        String mTitleList[] = new String[]{"动态", "文章", "活动", "问答"};

        mBinding.viewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, mTitleList));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(mTitleList, mBinding.viewpager, mBinding.magic, this);
        mBinding.viewpager.setOffscreenPageLimit(4);

    }


    private void doSerach() {
        mBinding.llHistoryList.setVisibility(View.GONE);
        mBinding.llHotList.setVisibility(View.GONE);
        mBinding.llSearchResult.setVisibility(View.VISIBLE);
        for (int i = 0; i < fragments.size(); i++) {
            ArrayList<Pair<String, String>> pairs = new ArrayList<>();
            pairs.add(new Pair<>("memberid", userInfoVo.uid));
            pairs.add(new Pair<>("uuid", userInfoVo.uuid));
            pairs.add(new Pair<>("keyword", keyword));
            ((NitCommonListFragment) fragments.get(i)).UpdateReqParam(false, pairs);
            ((NitCommonListFragment) fragments.get(i)).onReFresh(null);
        }

    }


    @Override
    public void initView() {

        mViewModel.fetchHotSearchLab();

        mBinding.edSerch.setOnEditorActionListener((v, actionId, event) -> {
            keyword = mBinding.edSerch.getText().toString().trim();
            if (TextUtils.isEmpty(keyword)) {
                mBinding.llHistoryList.setVisibility(View.VISIBLE);
                mBinding.llHotList.setVisibility(View.VISIBLE);
            } else {
                doSerach();
                if (!isSearching) {
                    CacheUtils.saveSerachChache(t, keyword);
                    List<String> lists = CacheUtils.getSerachChache(t);
                    hivsSampleAdapter.clear();
                    hivsSampleAdapter.add(lists);
                    isSearching = true;
                }
            }
            return true;
        });

        mBinding.linBack.setOnClickListener(v -> {
            finish();
        });

        mBinding.edSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mBinding.edSerch.getText().toString().trim())) {
                    mBinding.llHistoryList.setVisibility(View.VISIBLE);
                    mBinding.llHotList.setVisibility(View.VISIBLE);
                    mBinding.llSearchResult.setVisibility(View.GONE);
                    isSearching = false;
                }
            }
        });
        List<String> lists = CacheUtils.getSerachChache(t);
        hivsSampleAdapter = new NitAbsSampleAdapter(R.layout.item_search, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {

            }
        };
        hivsSampleAdapter.add(lists);
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.iv_del, R.id.item_coutainer}, (childview, position) -> {
            int i = childview.getId();
            if (i == R.id.iv_del) {
                CacheUtils.delSerachCache(t, (String) hivsSampleAdapter.getItem(position));
                hivsSampleAdapter.getmObjects().remove(position);
                hivsSampleAdapter.notifyDataSetChanged();
            } else if (i == R.id.item_coutainer) {
                mBinding.edSerch.setText((String) hivsSampleAdapter.getItem(position));
                keyword = (String) hivsSampleAdapter.getItem(position);
                doSerach();
            }
        });
        mBinding.tvDelAll.setOnClickListener(v -> {
            if (hivsSampleAdapter.getmObjects().size() > 0) {
                ConfirmDialog.newInstance("提示", "是否确定清除所有历史记录")
                        .setConfimLietener(new ConfirmDialog.ConfimLietener() {
                            @Override
                            public void onCancle() {

                            }

                            @Override
                            public void onConfim() {
                                hivsSampleAdapter.getmObjects().clear();
                                hivsSampleAdapter.notifyDataSetChanged();
                                CacheUtils.ClearSerachCache();
                            }

                            @Override
                            public void onConfim(String edit) {

                            }
                        }).setMargin(50).show(getSupportFragmentManager());

            } else {
                ToastUtils.showShort("暂无历史记录");
            }
        });
    }

    @Override
    public void initObserver() {

        mViewModel.hotLableLv.observe(this, rstServerVos -> {
            hotItemVoList = new ArrayList<>();
            FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
            mBinding.hotRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(4)));
            mBinding.hotRecyclerView.setLayoutManager(flowLayoutManager);
            FlowAdapter flowAdapter = new FlowAdapter(rstServerVos, SearchActivity.this, o -> {
                mBinding.edSerch.setText((String) o);
                keyword = (String) o;
                CacheUtils.saveSerachChache(t, (String) o);
                List<String> lists = CacheUtils.getSerachChache(t);
                hivsSampleAdapter.clear();
                hivsSampleAdapter.add(lists);
                doSerach();
            });
            mBinding.hotRecyclerView.setLayoutManager(flowLayoutManager);
            mBinding.hotRecyclerView.setAdapter(flowAdapter);
        });
    }

    @Override
    public void initRouter() {

    }

}
