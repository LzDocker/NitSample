package com.bfhd.account.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountFragmentCityCoutainerBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.base.HivsBaseFragment;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.vo.WorldNumList;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class AccountCityCoutainerFragment extends HivsBaseFragment<AccountViewModel, AccountFragmentCityCoutainerBinding> {

    AccountCountryNumListFragment accountCountryNumListFragment1;
    AccountCountryNumListFragment accountCountryNumListFragment2;
    ArrayList<Fragment> fragments = new ArrayList<>();
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment_city_coutainer;
    }

    @Override
    public AccountViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        accountCountryNumListFragment1 = AccountCountryNumListFragment.newinstance("1");
        accountCountryNumListFragment2 = AccountCountryNumListFragment.newinstance("2");

        fragments.add(accountCountryNumListFragment1);
        fragments.add(accountCountryNumListFragment2);
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, new String[]{"国内(含港澳台)", "国际"}));
        mBinding.get().tabCircleTitle.setViewPager(mBinding.get().viewpager);
        mBinding.get().tabCircleTitle.setCurrentTab(0);

    }

    public List<WorldNumList.WorldEnty> getList() {
        return ((AccountCountryNumListFragment) fragments.get(mBinding.get().viewpager.getCurrentItem())).list;
    }

    public int getCurrentPosition() {
        return mBinding.get().viewpager.getCurrentItem();
    }

    public void processLocation(WorldNumList.WorldEnty worldEnty, String type) {
        boolean isFind = false;
        if (worldEnty != null && accountCountryNumListFragment1.list != null) {

            for (int i = 0; i < accountCountryNumListFragment1.list.size(); i++) {
                if (worldEnty.name.equals(accountCountryNumListFragment1.list.get(i).name)) {

                    Intent intent = new Intent();
                    WorldNumList.WorldEnty curwo = accountCountryNumListFragment1.list.get(i);
                    curwo.curtype = String.valueOf(getCurrentPosition());
                    intent.putExtra("data", curwo.id);
                    intent.putExtra("num", curwo.global_num);
                    intent.putExtra("name", curwo.name);
                    intent.putExtra("datasource", curwo);
                    getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                    getHoldingActivity().finish();
                    isFind = true;
                    break;
                }
            }
        }
        if (worldEnty != null && accountCountryNumListFragment2.list != null && !isFind) {
            for (int i = 0; i < accountCountryNumListFragment2.list.size(); i++) {
                if (worldEnty.name.equals(accountCountryNumListFragment2.list.get(i).name)) {
                    Intent intent = new Intent();
                    WorldNumList.WorldEnty curwo = accountCountryNumListFragment2.list.get(i);
                    curwo.curtype = String.valueOf(getCurrentPosition());
                    intent.putExtra("data", curwo.id);
                    intent.putExtra("num", curwo.global_num);
                    intent.putExtra("name", curwo.name);
                    intent.putExtra("datasource", curwo);
                    getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                    getHoldingActivity().finish();
                    isFind = true;
                    break;
                }
            }
        }

    }

    @Override
    public void initImmersionBar() {

    }
}
