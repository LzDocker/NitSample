package com.bfhd.circle.ui.circle;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;

import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleGroupClassBinding;
import com.bfhd.circle.ui.adapter.CircleEditMemberGroupAdapter;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/*
 * 编辑成员分组
 * */
public class CircleEditMemberGroupActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleGroupClassBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    private StaCirParam mStartParam;
    private CircleEditMemberGroupAdapter editClassAdapter;
    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;
    private List<MemberGroupingVo> oldList = new ArrayList<>();

//    private int type;

    public static void startMe(Context context, StaCirParam startCircleBean) {
        Intent intent = new Intent(context, CircleEditMemberGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,101);
//        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_group_class;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
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
                params.put("memberid",userInfoVo.uid);
                params.put("uuid",userInfoVo.uuid);
                if (editClassAdapter.getmObjects().size() > 0) {
                    for (int i = 0; i < editClassAdapter.getmObjects().size(); i++) {
                        params.put("newGroup[" + i + "][groupid]", editClassAdapter.getmObjects().get(i).getGroupid());
                        params.put("newGroup[" + i + "][groupName]",editClassAdapter.getmObjects().get(i).getGroupName());
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
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 112: // 获取分类数据成功
                oldList.clear();
                oldList.addAll(((List<MemberGroupingVo>) viewEventResouce.data));
                editClassAdapter.getmObjects().addAll(((List<MemberGroupingVo>) viewEventResouce.data));
                editClassAdapter.notifyDataSetChanged();
                break;
            case 113: // 保存成功
                setResult(RESULT_OK);
                finish();
                break;
        }
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
