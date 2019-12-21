package com.docker.cirlev2.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ClassListActivityBinding;
import com.docker.cirlev2.databinding.Circlev2ItemCircleTypeTitleBinding;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.CirlceTypeVo;
import com.docker.common.BR;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;

import java.util.ArrayList;
import java.util.List;

import static com.docker.common.common.router.AppRouter.CIRCLE_SEARCH_LIST;

/*
 * 圈子分类列表
 * */
@Route(path = AppRouter.CIRCLE_CLASS_LIST)
public class CircleClassListActivity extends NitCommonActivity<SampleListViewModel, Circlev2ClassListActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_class_list_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("更多");
        mToolbar.setIvRight(R.mipmap.serch, v -> {
//            Intent intent = new Intent(CircleTypeListActivity.this, CircleSearchActivity.class);
//            startActivity(intent);
            ARouter.getInstance().build(CIRCLE_SEARCH_LIST).navigation();
        });

        NitAbsSampleAdapter mAdapter = new NitAbsSampleAdapter(R.layout.circlev2_item_circle_type_title, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                Circlev2ItemCircleTypeTitleBinding itembind = (Circlev2ItemCircleTypeTitleBinding) holder.getBinding();
                if (((CirlceTypeVo) getItem(position)).isCheck) {
                    itembind.tvTitle.setBackgroundColor(getResources().getColor(R.color.circle_white));
                } else {
                    itembind.tvTitle.setBackgroundColor(getResources().getColor(R.color.circle_gray_common));
                }
            }
        };
        List<CirlceTypeVo> titleList = new ArrayList<>();
        titleList.add(new CirlceTypeVo("企业圈", true));
        titleList.add(new CirlceTypeVo("兴趣圈", false));
        titleList.add(new CirlceTypeVo("国别圈", false));

        mBinding.circleRecycleType.setAdapter(mAdapter);
        mAdapter.add(titleList);

        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
            CommonListOptions commonListOptions = new CommonListOptions();
            commonListOptions.falg = 102;
            commonListOptions.isActParent = true;
            commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
            commonListOptions.ReqParam.put("type", i + 1 + "");
            commonListOptions.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
            commonListOptions.ReqParam.put("memberid", "3");  //uuid=3c29a4eed44db285468df3443790e64a&memberid=3
            fragmentList.add(NitCommonContainerFragmentV2.newinstance(commonListOptions));
        }
        CommonpagerAdapter pagerAdapter = new CommonpagerAdapter(getSupportFragmentManager(), fragmentList);
        mBinding.circleViewpager.setAdapter(pagerAdapter);
        mBinding.circleViewpager.setOffscreenPageLimit(fragmentList.size());
        mAdapter.setOnItemClickListener((v, po) -> {
            mBinding.circleViewpager.setCurrentItem(po, false);
            for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                ((CirlceTypeVo) mAdapter.getmObjects().get(i)).isCheck = false;
            }
            ((CirlceTypeVo) mAdapter.getmObjects().get(po)).isCheck = true;
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleMinesViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

            }
        };
        return nitDelegetCommand;
    }
}
