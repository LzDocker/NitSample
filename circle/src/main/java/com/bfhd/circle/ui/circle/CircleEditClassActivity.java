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
import com.bfhd.circle.databinding.CircleActivityCircleEditClassBinding;
import com.bfhd.circle.ui.adapter.CircleEditClassAdapter;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/*
 * 编辑分类
 * */
public class CircleEditClassActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleEditClassBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    private StaCirParam mStartParam;

    public static final int LEVEL_1_EDITCODE = 1001;
    public static final int LEVEL_2_EDITCODE = 1002;

    private CircleEditClassAdapter editClassAdapter;
    private ItemTouchHelperCallback itemTouchHelperCallback;
    private ItemTouchHelper touchHelper;
    private String mParentid;
    private List<CircleTitlesVo> oldList = new ArrayList<>();

//    private int type;

    public static void startMe(Context context, StaCirParam startCircleBean, int code) {

        Intent intent = new Intent(context, CircleEditClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, code);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_edit_class;
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
        mToolbar.setTitle("编辑分类");
        mToolbar.setTvRight("保存", v -> {
            if (checkData() && !TextUtils.isEmpty(mParentid)) {
                HashMap<String, String> params = new HashMap<>();
                params.put("circleid", mStartParam.getCircleid());
                params.put("utid", mStartParam.getUtid());
                params.put("parentid", mParentid);
                if (editClassAdapter.getmObjects().size() > 0) {
                    for (int i = 0; i < editClassAdapter.getmObjects().size(); i++) {
                        params.put("newClass[" + i + "][classid]", TextUtils.isEmpty(editClassAdapter.getmObjects().get(i).getClassid()) ? "0" : editClassAdapter.getmObjects().get(i).getClassid());
                        params.put("newClass[" + i + "][classname]", TextUtils.isEmpty(editClassAdapter.getmObjects().get(i).getName()) ? "0" : editClassAdapter.getmObjects().get(i).getName());
                        params.put("newClass[" + i + "][listorder]", String.valueOf(i));
                    }
                } else {
                    params.put("newClass", "");
                }
                oldList.removeAll(editClassAdapter.getmObjects());
                if (oldList.size() > 0) {
                    for (int i = 0; i < oldList.size(); i++) {
                        params.put("delClass[" + i + "]", oldList.get(i).getClassid());
                    }
                } else {
                    params.put("delClass", "");
                }
                mViewModel.saveCircleClass(params);
            }
        });
        editClassAdapter = new CircleEditClassAdapter();
        itemTouchHelperCallback = new ItemTouchHelperCallback(editClassAdapter);
        touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        mBinding.recyclerView.setAdapter(editClassAdapter);


        mStartParam = (StaCirParam) getIntent().getExtras().getSerializable("mStartParam");
        mViewModel.initCircleParam(mStartParam);
        mViewModel.getCircleClass();
        mBinding.circleTvAdd.setOnClickListener(v -> {
            if (checkData()) {
                CircleTitlesVo vo = new CircleTitlesVo();
                vo.setName("");
                vo.setClassid("0");
                editClassAdapter.getmObjects().add(vo);
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
            case 103: // 获取分类数据成功
                oldList.clear();
                if (!TextUtils.isEmpty(mStartParam.getExtentron())) {
                    mParentid = ((List<CircleTitlesVo>) viewEventResouce.data).get(Integer.parseInt(mStartParam.getExtentron())).getClassid();
                    oldList.addAll(((List<CircleTitlesVo>) viewEventResouce.data).get(Integer.parseInt(mStartParam.getExtentron())).getChildClass());
                    editClassAdapter.getmObjects().addAll(((List<CircleTitlesVo>) viewEventResouce.data).get(Integer.parseInt(mStartParam.getExtentron())).getChildClass());

                } else {
                    mParentid = "0"; // 一级栏目编辑
                    oldList.addAll((Collection<? extends CircleTitlesVo>) viewEventResouce.data);
                    editClassAdapter.getmObjects().addAll((Collection<? extends CircleTitlesVo>) viewEventResouce.data);

                }

                editClassAdapter.notifyDataSetChanged();
                break;
            case 104: // 保存成功
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    private boolean checkData() {
        if (editClassAdapter.getmObjects().size() != 0) {
            for (int i = 0; i < editClassAdapter.getmObjects().size(); i++) {
                if (TextUtils.isEmpty(editClassAdapter.getmObjects().get(i).getName().trim())) {
                    ToastUtils.showShort("请完善分类名称!");
                    return false;
                }

                String temp = editClassAdapter.getmObjects().get(i).getName().trim();
                for (int j = i + 1; j < editClassAdapter.getmObjects().size(); j++) {
                    if (temp.equals(editClassAdapter.getmObjects().get(j).getName().trim())) {
                        ToastUtils.showShort("分类名称不能相同!");
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
