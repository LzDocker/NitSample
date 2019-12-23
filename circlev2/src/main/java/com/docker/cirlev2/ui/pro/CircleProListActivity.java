package com.docker.cirlev2.ui.pro;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ClassListActivityBinding;
import com.docker.cirlev2.databinding.Circlev2ItemCircleTypeTitleBinding;
import com.docker.cirlev2.databinding.Circlev2ProManagerActivityBinding;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.CirlceTypeVo;
import com.docker.cirlev2.vo.pro.AppVo;
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
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.empty.EmptyStatus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.router.AppRouter.CIRCLE_SEARCH_LIST;

/*
 * 圈子应用管理
 * */
@Route(path = AppRouter.CIRCLE_DETAIL_v2_PRO_MANAGER)
public class CircleProListActivity extends NitCommonActivity<SampleListViewModel, Circlev2ProManagerActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_pro_manager_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }


    @Override
    public void initView() {
        mToolbar.setTitle("应用管理");

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getSupportFragmentManager(), R.id.frame_pro, commonListOptions);


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
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                commonListVm.mEmptycommand.set(EmptyStatus.BdHiden);
                commonListVm.mCompleteCommand.set(true);
                commonListVm.mCompleteCommand.notifyChange();

                AppVo appVo = new AppVo();
                appVo.name = "动态";
                appVo.id = "0";
                commonListVm.mItems.add(appVo);

                AppVo appVo1 = new AppVo();
                appVo1.name = "新闻";
                appVo1.id = "1";
                commonListVm.mItems.add(appVo1);

                AppVo appVo2 = new AppVo();
                appVo2.name = "问答";
                appVo2.id = "2";
                commonListVm.mItems.add(appVo2);
            }
        };
        return nitDelegetCommand;
    }
}
