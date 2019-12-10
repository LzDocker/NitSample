package com.docker.common.common.ui.XPopup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.BR;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.ItemVo;
import com.docker.common.common.widget.XPopup.BottomPopup;
import com.docker.common.common.widget.XPopup.CenterPopup;
import com.docker.common.common.widget.XPopup.CustomAttachPopup;
import com.docker.common.common.widget.XPopup.CustomDrawerPopupView;
import com.docker.common.common.widget.XPopup.CustomPartShadowPopupView;
import com.docker.common.common.widget.XPopup.PagerDrawerPopup;
import com.docker.common.common.widget.XPopup.QQMsgPopup;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.interfaces.XPopupCallback;

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


        findViewById(R.id.part_shadow).setOnClickListener(v -> {
            show(v);
        });

        findViewById(R.id.confim).setOnClickListener(v -> {

            new XPopup.Builder(XPopupActivity.this)
//                         .dismissOnTouchOutside(false)
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                    .setPopupCallback(new SimpleCallback() {
                        @Override
                        public void onCreated() {
                            Log.e("tag", "弹窗创建了");
                        }

                        @Override
                        public void onShow() {
                            Log.e("tag", "onShow");
                        }

                        @Override
                        public void onDismiss() {
                            Log.e("tag", "onDismiss");
                        }

                        //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                        @Override
                        public boolean onBackPressed() {
                            ToastUtils.showShort("我拦截的返回按键，按返回键XPopup不会关闭了");
                            return true;
                        }
                    }).asConfirm("我是标题", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                    "取消", "确定",
                    new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                        }
                    }, null, false)
                    .show();

        });

        findViewById(R.id.pop_hor).setOnClickListener(v -> {
            new XPopup.Builder(XPopupActivity.this)
//                        .offsetX(-10) //往左偏移10
//                        .offsetY(10)  //往下偏移10
//                        .popupPosition(PopupPosition.Right) //手动指定位置，有可能被遮盖
                    .hasShadowBg(false) // 去掉半透明背景
                    .atView(v)
                    .asCustom(new CustomAttachPopup(XPopupActivity.this))
                    .show();
        });
        findViewById(R.id.pop_vir).setOnClickListener(v -> {
            new XPopup.Builder(XPopupActivity.this)
                    .hasShadowBg(false)
//                        .popupAnimation(PopupAnimation.NoAnimation) //NoAnimation表示禁用动画
//                        .isCenterHorizontal(true) //是否与目标水平居中对齐
//                        .offsetY(-10)
//                        .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                    .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                    .asAttachList(new String[]{"分享", "编辑", "不带icon"},
                            new int[]{R.mipmap.close_icon, R.mipmap.close_icon},
                            (position, text) -> {

                            })
                    .show();
        });
        findViewById(R.id.fullscreen).setOnClickListener(v -> {
            new XPopup.Builder(XPopupActivity.this)
                    .hasStatusBarShadow(true)
                    .autoOpenSoftInput(true)
                    .asCustom(new FullScreenPopupView(XPopupActivity.this))
                    .show();
        });

        findViewById(R.id.input_up).setOnClickListener(v -> {
            new XPopup.Builder(XPopupActivity.this)
                    .autoOpenSoftInput(true)
                    .asCustom(new BottomPopup(XPopupActivity.this))
                    .show();
        });

        findViewById(R.id.custom_pos).setOnClickListener(v -> {

            new XPopup.Builder(XPopupActivity.this)
                    .offsetY(100)
                    .hasShadowBg(false)
                    .popupAnimation(PopupAnimation.TranslateFromLeft)
                    .asCustom(new QQMsgPopup(XPopupActivity.this))
                    .show();

        });

        CustomDrawerPopupView drawerPopupView = new CustomDrawerPopupView(XPopupActivity.this);

        findViewById(R.id.drawer_left).setOnClickListener(v -> {

            new XPopup.Builder(XPopupActivity.this)
                    .popupPosition(PopupPosition.Right)//右边
                    .hasStatusBarShadow(true) //启用状态栏阴影
                    .asCustom(drawerPopupView)
                    .show();
        });


        findViewById(R.id.drawer_right).setOnClickListener(v -> {

            new XPopup.Builder(XPopupActivity.this)
//                        .asCustom(new CustomDrawerPopupView(getContext()))
//                        .hasShadowBg(false)
                    .asCustom(new PagerDrawerPopup(XPopupActivity.this))
//                        .asCustom(new ListDrawerPopupView(getContext()))
                    .show();
        });

        findViewById(R.id.notice).setOnClickListener(v -> {
            new XPopup.Builder(XPopupActivity.this)
                    .hasShadowBg(false)
                    .isCenterHorizontal(true)
                    .offsetY(100)
                    .asCustom(new QQMsgPopup(XPopupActivity.this))
                    .show();
        });

    }


    private void show(View v) {
        CustomPartShadowPopupView customPartShadowPopupView = (CustomPartShadowPopupView) new XPopup.Builder(XPopupActivity.this)
                .atView(v)
                .autoOpenSoftInput(true)
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onShow() {
                    }

                    @Override
                    public void onDismiss() {
                    }
                })
                .asCustom(new CustomPartShadowPopupView(XPopupActivity.this));
        customPartShadowPopupView.show();
    }


}
