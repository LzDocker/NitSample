package com.docker.cirlev2.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleGroupClassBinding;
import com.docker.cirlev2.util.CircleEditMemberGroupAdapter;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * 编辑成员分组
 * */
public class CircleEditMemberGroupActivity extends NitCommonActivity<CircleDetailIndexViewModel, Circlev2ActivityCircleGroupClassBinding> {

    private StaCirParam mStartParam;
    private CircleEditMemberGroupAdapter editClassAdapter;
    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;
    private List<MemberGroupingVo> oldList = new ArrayList<>();

    public static void startMe(Context context, StaCirParam startCircleBean) {
        Intent intent = new Intent(context, CircleEditMemberGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, 101);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_group_class;
    }

    @Override
    public CircleDetailIndexViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDetailIndexViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("分组管理");
        mToolbar.setTvRight("保存", v -> {
            if (checkData()) {
                UserInfoVo userInfoVo = CacheUtils.getUser();
                HashMap<String, String> params = new HashMap<>();
                params.put("circleid", mStartParam.getCircleid());
                params.put("utid", mStartParam.getUtid());
                params.put("memberid", userInfoVo.uid);
                params.put("uuid", userInfoVo.uuid);
                if (editClassAdapter.getmObjects().size() > 0) {
                    for (int i = 0; i < editClassAdapter.getmObjects().size(); i++) {
                        params.put("newGroup[" + i + "][groupid]", editClassAdapter.getmObjects().get(i).getGroupid());
                        params.put("newGroup[" + i + "][groupName]", editClassAdapter.getmObjects().get(i).getGroupName());
                        params.put("newGroup[" + i + "][listorder]", String.valueOf(i));
                    }
                } else {
                    params.put("newGroup", "");
                }
                oldList.removeAll(editClassAdapter.getmObjects());
                if (oldList.size() > 0) {
                    for (int i = 0; i < oldList.size(); i++) {
                        params.put("delGroup[" + i + "]", oldList.get(i).getGroupid());
                    }
                } else {
                    params.put("delGroup", "");
                }
                mViewModel.updateMemberGroup(params);
            }
        });
        editClassAdapter = new CircleEditMemberGroupAdapter();
        itemTouchHelperCallback = new ItemTouchHelperCallback(editClassAdapter);
        touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        mBinding.recyclerView.setAdapter(editClassAdapter);
        mStartParam = (StaCirParam) getIntent().getExtras().getSerializable("mStartParam");
        mViewModel.getMemberGroup(mStartParam);
        mBinding.circleTvAdd.setOnClickListener(v -> {
            if (checkData()) {
                MemberGroupingVo addvo = new MemberGroupingVo();
                addvo.setGroupid("0");
                addvo.setGroupName("");
                editClassAdapter.getmObjects().add(addvo);
                editClassAdapter.notifyItemInserted(editClassAdapter.getmObjects().size());
            }
        });
        mBinding.circleTvTypeProcess.setOnClickListener(v -> {
            editClassAdapter.editFlag = !editClassAdapter.editFlag;
            itemTouchHelperCallback.setLongPressDragEnabled(editClassAdapter.editFlag);
            touchHelper.attachToRecyclerView(mBinding.recyclerView);
            editClassAdapter.notifyDataSetChanged();
            if (editClassAdapter.editFlag) {
                mBinding.circleTvTypeProcess.setTextColor(getResources().getColor(R.color.circle_red));
            } else {
                mBinding.circleTvTypeProcess.setTextColor(getResources().getColor(R.color.circle_gray));
            }
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mMemberGroupLv.observe(this, memberGroupingVos -> {
            oldList.clear();
            oldList.addAll(memberGroupingVos);
            editClassAdapter.getmObjects().addAll(memberGroupingVos);
            editClassAdapter.notifyDataSetChanged();
        });

        mViewModel.mUpdateMemberGroupLv.observe(this, memberGroupingVos -> {
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    private boolean checkData() {
        if (editClassAdapter.getmObjects().size() != 0) {
            for (int i = 0; i < editClassAdapter.getmObjects().size(); i++) {
                if (TextUtils.isEmpty(editClassAdapter.getmObjects().get(i).getGroupName().trim())) {
                    ToastUtils.showShort("请完善分组名称!");
                    return false;
                }
                String temp = editClassAdapter.getmObjects().get(i).getGroupName().trim();
                for (int j = i + 1; j < editClassAdapter.getmObjects().size(); j++) {
                    if (temp.equals(editClassAdapter.getmObjects().get(j).getGroupName().trim())) {
                        ToastUtils.showShort("分组名称不能相同!");
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
