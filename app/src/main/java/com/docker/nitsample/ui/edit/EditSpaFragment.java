package com.docker.nitsample.ui.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.vm.card.ProviderAccountCard;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.XPopup.XPopupActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.XPopup.PagerDrawerPopup;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.nitsample.R;
import com.docker.nitsample.card.CardProvideDispatcher;
import com.docker.nitsample.databinding.FragmentEditSpaBinding;
import com.docker.nitsample.vm.SampleEditSpaViewModel;
import com.docker.nitsample.vm.card.ProviderAppCard;
import com.docker.nitsample.vo.card.SampleCardVo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupPosition;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class EditSpaFragment extends NitCommonFragment<SampleEditSpaViewModel, FragmentEditSpaBinding> {

    private NitCommonListVm outervm;
    private NitCommonListFragment nitCardFragment;

    private BasePopupView editMenu;
    private BasePopupView popMenu;

    private String dbtabid;
    private Boolean isEdit = true;

    private ArrayList<BaseItemModel> config;

    private Disposable disposable;

    @Inject
    DbCacheUtils dbCacheUtils;

    public static EditSpaFragment getInstance(String dbtabid, Boolean isEdit) {
        EditSpaFragment editSpaFragment = new EditSpaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dbtabid", dbtabid);
        bundle.putBoolean("isEdit", isEdit);
        editSpaFragment.setArguments(bundle);
        return editSpaFragment;
    }

    public static EditSpaFragment getInstance(ArrayList<BaseCardVo> baseCardVos, Boolean isEdit) {
        EditSpaFragment editSpaFragment = new EditSpaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("config", baseCardVos);
        bundle.putBoolean("isEdit", isEdit);
        editSpaFragment.setArguments(bundle);
        return editSpaFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_spa;
    }

    @Override
    protected SampleEditSpaViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleEditSpaViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        mBinding.get().ivEditMenu.setOnClickListener(v -> {


            if (editMenu == null) {
                editMenu = new XPopup.Builder(EditSpaFragment.this.getHoldingActivity())
                        .hasShadowBg(false)
                        .asCustom(new EditPagerDrawerPopup(EditSpaFragment.this.getHoldingActivity()))
                        .show();
            } else {
                editMenu.show();
            }

        });

        mBinding.get().ivMakeMenu.setOnClickListener(v -> {
            if (popMenu == null) {
                popMenu = new XPopup.Builder(EditSpaFragment.this.getHoldingActivity())
                        .hasShadowBg(false)
                        .isCenterHorizontal(true) //是否与目标水平居中对齐
                        .offsetY(20)
                        .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"完成编辑", "取消当前", "预览当前"},
                                new int[]{},
                                (position, text) -> {
                                    switch (position) {
                                        case 0:
                                            if (outervm != null) {
                                                ArrayList<BaseCardVo> baseCardVos = new ArrayList<>();
                                                for (int i = 0; i < outervm.mItems.size(); i++) {
                                                    if (outervm.mItems.get(i) instanceof BaseCardVo) {
                                                        ((BaseCardVo) outervm.mItems.get(i)).position = i;
                                                        baseCardVos.add((BaseCardVo) outervm.mItems.get(i));
                                                    }
                                                }
                                                if (baseCardVos.size() > 0) {
                                                    if (!TextUtils.isEmpty(dbtabid)) {
                                                        dbCacheUtils.clearCache("db_tab_" + dbtabid, () -> save(dbtabid, baseCardVos));
                                                    } else {
                                                        ConfirmDialog.newInstance("提示", "保存页面").setConfimLietener(new ConfirmDialog.ConfimLietener() {
                                                            @Override
                                                            public void onCancle() {

                                                            }

                                                            @Override
                                                            public void onConfim() {

                                                            }

                                                            @Override
                                                            public void onConfim(String edit) {
                                                                save(edit, baseCardVos);
                                                            }
                                                        }).setMargin(20).show(getChildFragmentManager());
                                                    }
                                                } else {
                                                    ToastUtils.showShort("未检测到任何card");
                                                }
                                            }
                                            break;
                                        case 1:
                                            outervm.mItems.clear();
                                            ToastUtils.showShort("清空完成");
                                            break;
                                        case 2:
                                            // 预览当前
                                            if (!TextUtils.isEmpty(dbtabid)) {
                                                ARouter.getInstance().build(AppRouter.HOME_preview).withString("dbTabid", dbtabid).withBoolean("isEdit", false).navigation();
                                            } else {
                                                ArrayList<BaseCardVo> baseCardVos = new ArrayList<>();
                                                for (int i = 0; i < outervm.mItems.size(); i++) {
                                                    if (outervm.mItems.get(i) instanceof BaseCardVo) {
                                                        ((BaseCardVo) outervm.mItems.get(i)).position = i;
                                                        baseCardVos.add((BaseCardVo) outervm.mItems.get(i));
                                                    }
                                                }
                                                if (baseCardVos.size() > 0) {
                                                    ARouter.getInstance().build(AppRouter.HOME_preview).withBoolean("isEdit", false).withSerializable("config", baseCardVos).navigation();
                                                } else {
                                                    ToastUtils.showLong("请先编辑！！！");
                                                    return;
                                                }
                                            }
                                            break;
                                    }
                                })
                        .show();
            } else {
                popMenu.show();
            }
        });
    }

    private void save(String edit, ArrayList<BaseCardVo> baseCardVos) {
        FileUtils.delete(Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/db_tab_" + edit + ".jpg");
        ImageUtils.save(ImageUtils.view2Bitmap(mBinding.get().getRoot()),
                Environment.getExternalStorageDirectory() + "/" + AppUtils.getAppName() + "/chache/db_tab_" + edit + ".jpg",
                Bitmap.CompressFormat.JPEG);
        dbCacheUtils.save("db_tab_" + edit, baseCardVos, () -> {
            outervm.mItems.clear();
        });
        dbCacheUtils.loadFromDb("edit").observe(EditSpaFragment.this, o -> {
            ArrayList<String> arrayList;
            if (o == null) {
                arrayList = new ArrayList();
            } else {
                arrayList = (ArrayList<String>) o;
            }
            if (!arrayList.contains("db_tab_" + edit)) {
                arrayList.add("db_tab_" + edit);
            }
            dbCacheUtils.save("edit", arrayList, () -> ToastUtils.showShort("保存完成"));
            //refresh_spa
//            RxBus.getDefault().post(new RxEvent<>("refresh_spa", ""));
            if (!TextUtils.isEmpty(dbtabid)) {
                getHoldingActivity().finish();
            }

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 1002;
        ProviderAccountCard.providerCardForFrame(getChildFragmentManager(), R.id.frame_spa, commonListOptions);

        isEdit = getArguments().getBoolean("isEdit");
        dbtabid = getArguments().getString("dbtabid");
        config = (ArrayList<BaseItemModel>) getArguments().getSerializable("config");
        if (!isEdit) { // 预览
            mBinding.get().ivMakeMenu.setVisibility(View.GONE);
            mBinding.get().ivEditMenu.setVisibility(View.GONE);
        }
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("card_edit")) {
                NitBaseProviderCard.providerCard(outervm, ((SampleCardVo) rxEvent.getR()).mCardData, nitCardFragment);
            }
        });
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
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonListFragment nitCommonListFragment) {
                outervm = commonListVm;
                nitCardFragment = nitCommonListFragment;
                if (!TextUtils.isEmpty(dbtabid)) {
                    dbCacheUtils.loadFromDb("db_tab_" + dbtabid).observe(EditSpaFragment.this, o -> {
                        if (o != null) {
                            ArrayList<BaseItemModel> arrayList = (ArrayList<BaseItemModel>) o;
                            CardProvideDispatcher.dispatcherCard(commonListVm, nitCommonListFragment, arrayList);
                        }
                    });
                }
                if (config != null && config.size() > 0) {
                    CardProvideDispatcher.dispatcherCard(commonListVm, nitCommonListFragment, config);
                }
            }
        };

        return nitDelegetCommand;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
