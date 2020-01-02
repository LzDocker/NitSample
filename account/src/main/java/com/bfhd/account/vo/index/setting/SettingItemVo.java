package com.bfhd.account.vo.index.setting;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.GlideCacheUtil;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.widget.BottomSheetDialog;

import cn.jpush.android.api.JPushInterface;


public class SettingItemVo extends BaseCardVo<MyInfoVo> {
    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();

    public SettingItemVo(int style, int position) {
        super(style, position);
        mVmPath = "com.bfhd.account.vm.AccountSettingViewModel";
    }

    @Override
    public int getItemLayout() {
        return R.layout.account_fragment_mine_setting_item;
    }


    @Override
    public void onItemClick(BaseCardVo item, View view) {
        if (view.getId() == R.id.ll_person_info) { // 个人信息
            CommonContainerOptions options = new CommonContainerOptions();
            options.title = "个人信息";
            options.commonListOptions = new CommonListOptions();
            ARouter.getInstance().build(AppRouter.COMMON_CONTAINER)
                    .withSerializable(Constant.ContainerParam, options)
                    .withSerializable(Constant.ContainerCommand, "com.bfhd.account.vm.AccountPersonInfoViewModel")
                    .navigation();
//            ((AccountPersonInfoViewModel) mNitcommonCardViewModel).process();
        }

        if (view.getId() == R.id.ll_address) { //地址管理
            ARouter.getInstance().build(AppRouter.COMMON_address_list).navigation();
        }

        if (view.getId() == R.id.ll_chache_clear) { //清除缓存

            bottomSheetDialog.setDataCallback(new String[]{"清除缓存"}, position -> {
                bottomSheetDialog.dismiss();
                switch (position) {
                    case 0:
                        clearChahe();
                        break;
                }
            });
            bottomSheetDialog.show((FragmentActivity) ActivityUtils.getTopActivity());
        }

        if (view.getId() == R.id.tv_go_out) { //退出登录
//            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
            CacheUtils.clearUser();
//            LogoutHelper.logout();
            JPushInterface.deleteAlias(ActivityUtils.getTopActivity(), AppUtils.getAppVersionCode());
            JPushInterface.clearAllNotifications(ActivityUtils.getTopActivity());
//            ActivityUtils.finishAllActivities();
            ActivityUtils.finishAllActivities(true);
        }
    }

    private void clearChahe() {
        GlideCacheUtil.getInstance().clearImageDiskCache(ActivityUtils.getTopActivity(), () -> {
            cacheSize = "0kb";
            notifyChange();
        });
    }

    public String cacheSize = GlideCacheUtil.getInstance().getCacheSize(ActivityUtils.getTopActivity());
}
