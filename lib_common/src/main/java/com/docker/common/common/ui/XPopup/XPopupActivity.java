package com.docker.common.common.ui.XPopup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.BR;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.ItemVo;
import com.docker.common.common.widget.XPopup.BottomPopup;
import com.docker.common.common.widget.XPopup.CenterPopup;
import com.docker.common.databinding.CommonActivityXpopupBinding;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

@Route(path = AppRouter.COMMON_XPOPUP)
public class XPopupActivity extends AppCompatActivity {

    private BasePopupView basePopupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_xpopup);
        ItemVo itemVo = new ItemVo("1", "编辑");
        ItemVo itemVo1 = new ItemVo("2", "删除");
        ItemVo itemVo2 = new ItemVo("3", "取消");
        List<ItemVo> itemVoList = new ArrayList<>();
        itemVoList.add(itemVo);
        itemVoList.add(itemVo1);
        itemVoList.add(itemVo2);


        SimpleCommonRecyclerAdapter simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.common_xpopup_item_center, BR.item);
        simpleCommonRecyclerAdapter.setOnItemClickListener((view, position) -> {
            basePopupView.dismiss();

        });
        simpleCommonRecyclerAdapter.getmObjects().addAll(itemVoList);

        findViewById(R.id.center).setOnClickListener(v -> {
            CenterPopup centerPopup = new CenterPopup(this);
            basePopupView = new XPopup.Builder(this).setPopupCallback(new XPopupCallback() {
                @Override
                public void onCreated() {
                    RecyclerView recyclerView = centerPopup.findViewById(R.id.open_bottom_list_recycle);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(XPopupActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(simpleCommonRecyclerAdapter);
                    simpleCommonRecyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void beforeShow() {

                }

                @Override
                public void onShow() {

                }

                @Override
                public void onDismiss() {

                }

                @Override
                public boolean onBackPressed() {
                    return false;
                }
            }).asCustom(centerPopup).show();

        });


        findViewById(R.id.bottom).
                setOnClickListener(v ->
                {
                    BottomPopup bottomPopup = new BottomPopup(this);
                    basePopupView = new XPopup.Builder(this).setPopupCallback(new XPopupCallback() {
                        @Override
                        public void onCreated() {
                            TextView tv_wx = bottomPopup.findViewById(R.id.tv_wx);
                            TextView tv_pyq = bottomPopup.findViewById(R.id.tv_pyq);
                            TextView tv_qq = bottomPopup.findViewById(R.id.tv_qq);
                            TextView tv_zfb = bottomPopup.findViewById(R.id.tv_zfb);
                            TextView tv_lianjie = bottomPopup.findViewById(R.id.tv_lianjie);
                            TextView tv_jb = bottomPopup.findViewById(R.id.tv_jb);
                            TextView tv_cancel = bottomPopup.findViewById(R.id.tv_cancel);

                            tv_wx.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });
                            tv_pyq.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });
                            tv_qq.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });
                            tv_zfb.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });
                            tv_lianjie.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });
                            tv_jb.setOnClickListener(view -> {
                                basePopupView.dismiss();

                            });
                            tv_cancel.setOnClickListener(view -> {
                                basePopupView.dismiss();
                            });

                        }

                        @Override
                        public void beforeShow() {

                        }

                        @Override
                        public void onShow() {

                        }

                        @Override
                        public void onDismiss() {

                        }

                        @Override
                        public boolean onBackPressed() {
                            return false;
                        }
                    }).asCustom(bottomPopup).show();
                });
    }


}
