package com.docker.cirlev2.util;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ItemEditClassBinding;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.BR;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.common.common.widget.dialog.ConfirmEdViewmDialog;
import com.docker.common.common.widget.recycledrag.ItemTouchHelperAdapter;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;

import java.util.Collections;

public class CircleEditClassAdapter extends SimpleCommonRecyclerAdapter<CircleTitlesVo> implements ItemTouchHelperAdapter {


    public boolean editFlag = false;

    public CircleEditClassAdapter() {
        super(R.layout.circlev2_item_edit_class, BR.item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Circlev2ItemEditClassBinding mbinding = (Circlev2ItemEditClassBinding) holder.getBinding();
        mbinding.setEditFlag(editFlag);
        mbinding.circleTvDel.setOnClickListener(v -> {
            getmObjects().remove(position);
            CircleEditClassAdapter.this.notifyItemRemoved(position);
            CircleEditClassAdapter.this.notifyItemRangeChanged(position, getmObjects().size() - position);
        });

        mbinding.tvTabName.setOnClickListener(v -> {
            showConfirm(getItem(position), mbinding.tvTabName);
        });
        mbinding.llEditPro.setOnClickListener(v -> {
            getItem(position).setIsEditPro(!getItem(position).getIsEditPro());
        });
        NitAbsSampleAdapter nitAbsSampleAdapter = new NitAbsSampleAdapter(R.layout.circlev2_item_pro, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {

            }
        };
        AppVo appVo = new AppVo();
        appVo.name = "动态";
        appVo.id = "0";
        nitAbsSampleAdapter.add(appVo);

        AppVo appVo1 = new AppVo();
        appVo1.name = "新闻";
        appVo1.id = "1";
        nitAbsSampleAdapter.add(appVo1);

        AppVo appVo2 = new AppVo();
        appVo2.name = "问答";
        appVo2.id = "2";
        nitAbsSampleAdapter.add(appVo2);

        mbinding.recycleInner.setLayoutManager(new LinearLayoutManager(mbinding.recycleInner.getContext()));
        mbinding.recycleInner.setAdapter(nitAbsSampleAdapter);


    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(this.getmObjects(), fromPosition, toPosition);
        this.notifyItemMoved(fromPosition, toPosition);
        this.notifyItemRangeChanged(0, this.getmObjects().size());
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    private void showConfirm(CircleTitlesVo circleTitlesVo, TextView textView) {

        ConfirmEdViewmDialog.newInstance("提示", "请输入栏目名称", circleTitlesVo.getName()).setConfimLietener(new ConfirmEdViewmDialog.ConfimLietener() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onConfim() {

            }

            @Override
            public void onConfim(String edit) {
                circleTitlesVo.setName(edit);
                textView.setText(edit);
            }
        }).setMargin(24).show(((FragmentActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager());

    }
}
