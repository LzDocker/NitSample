package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleFragmentCircleCoverBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.NetImgVo;
import com.bfhd.circle.vo.NetImgWapperVo;
import com.bfhd.circle.vo.bean.SourceCoverParam;
import com.dcbfhd.utilcode.utils.ConvertUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.AppExecutors;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

/* 选图片
 *
 * */
public class CircleCovertFragment extends CommonFragment<CircleViewModel, CircleFragmentCircleCoverBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    AppExecutors appExecutors;
    private SmartRefreshLayout smartRefreshLayout;
    private HivsAbsSampleAdapter adapter;
    public final static int IMGTYPE_BAIDU = 101;  // 百度图库
    public final static int IMGTYPE_JINGXUAN = 102; // 精选图库
    private int mImgtype = IMGTYPE_BAIDU;
    public String keyWords = null; // 搜索关键字
    private SourceCoverParam mSourceCoverParam;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_cover;
    }

    public static CircleCovertFragment newInstance(int ImgType, SourceCoverParam mSourceCoverParam) {
        CircleCovertFragment circleCovertFragment = new CircleCovertFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", ImgType);
        bundle.putSerializable("mSourceCoverParam", mSourceCoverParam);
        circleCovertFragment.setArguments(bundle);
        return circleCovertFragment;
    }

    @Override
    protected CircleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().recyclerView.setNestedScrollingEnabled(true);
        adapter = new HivsAbsSampleAdapter(R.layout.circle_item_circle_net_img, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
//                CircleItemCircleNetImgBinding netImgBinding = (CircleItemCircleNetImgBinding) holder.getBinding();
//                ViewGroup.LayoutParams params = netImgBinding.itemBaiduImageIv.getLayoutParams();//得到item的LayoutParams布局参数
//                params.height = (int) (300 + Math.random() * 400);//把随机的高度赋予itemView布局
//                netImgBinding.itemBaiduImageIv.setLayoutParams(params);//把params设置给itemView布局
            }
        };
        mBinding.get().recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mBinding.get().recyclerView.setAdapter(adapter);
        mBinding.get().recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(4);
                outRect.left = ConvertUtils.dp2px(2);
                outRect.right = ConvertUtils.dp2px(2);
            }
        });
        adapter.setOnItemClickListener((view, position) -> {
            if (mSourceCoverParam.sourceMaxNum == 1) { //数量为1的时候有可能需要剪裁
                if (mSourceCoverParam.needCrop) {
                    if (TextUtils.isEmpty(((NetImgVo) adapter.getItem(position)).getThumbURL())) {
                        return;
                    }
                    processCrop(((NetImgVo) adapter.getItem(position)).getThumbURL());
                } else {
                    processSelectChange(position);
                }
            } else {
                processSelectChange(position);
            }
        });
    }


    // 异步从glide 缓存拿图片    拿到后再去剪裁 没拿到就不再剪裁了
    private void processCrop(String url) {
        showWaitDialog("加载中...");
        if (mSourceCoverParam.needCrop) {
            appExecutors.diskIO().execute(() -> {
                File imgFile = getImagePathFromCache(url, mSourceCoverParam.width, mSourceCoverParam.height);
                appExecutors.mainThread().execute(() -> {
                    hidWaitDialog();
                    if (imgFile != null) {
                        Uri sourceUri = Uri.fromFile(imgFile);
                        String cropName = System.currentTimeMillis() + ".jpg";
                        Uri dstUri = Uri.fromFile(new File(getHoldingActivity().getCacheDir(), cropName));
                        UCrop uCrop = UCrop.of(sourceUri, dstUri);
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(getHoldingActivity().getResources().getColor(R.color.colorPrimary));
                        options.setToolbarTitle("图片剪裁");
                        options.setStatusBarColor(getHoldingActivity().getResources().getColor(R.color.colorPrimary));
                        options.setFreeStyleCropEnabled(true);
                        options.withMaxResultSize(mSourceCoverParam.width, mSourceCoverParam.height);
                        uCrop.withOptions(options).start(CircleCovertFragment.this.getHoldingActivity());
                    } else {
                        mSourceCoverParam.getImgList().add(url);
                        mSourceCoverParam.status.set(1);
                    }
                });
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            mSourceCoverParam.getImgList().add(resultUri.getPath());
            mSourceCoverParam.status.set(1);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            ToastUtils.showShort("剪裁出错了，请重试");
        }
    }

    /**
     * 从Glide缓存磁盘中拿图片
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    private File getImagePathFromCache(String url, int width, int height) {
//        FutureTarget<File> future = Glide.with(getContext()).load(url).downloadOnly(width, height);
//        try {
//            File cacheFile = future.get();
//            String path = cacheFile.getAbsolutePath();
//            return path;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;

        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(mSourceCoverParam.width, mSourceCoverParam.height);
            requestOptions.priorityOf(Priority.HIGH).onlyRetrieveFromCache(true);
            File imageFile = Glide.with(getContext()).asFile()
                    .apply(requestOptions)
                    .load(url)
                    .submit().get();
            if (imageFile != null && imageFile.exists()) {
                return imageFile;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private void processSelectChange(int position) {
        if (((NetImgVo) adapter.getItem(position)).isSelected()) {  // 取消选中
            int cancelPos = Integer.parseInt(((NetImgVo) adapter.getItem(position)).getNumber());
            if (cancelPos != mSourceCoverParam.getImgList().size()) { // 删除最后一个
                for (int i = 0; i < adapter.getmObjects().size(); i++) {
                    if ((((NetImgVo) adapter.getItem(i)).isSelected())) {
                        int number = Integer.parseInt(((NetImgVo) adapter.getItem(i)).getNumber());
                        if (cancelPos < number) {
                            number = number - 1;
                            ((NetImgVo) adapter.getItem(i)).setNumber(String.valueOf(number));
                            adapter.notifyItemRangeChanged(i, 1);
                        }
                    }
                }
            }
            mSourceCoverParam.getImgList().remove(((NetImgVo) adapter.getItem(position)).getThumbURL());
            ((NetImgVo) adapter.getItem(position)).setSelected(!((NetImgVo) adapter.getItem(position)).isSelected());
        } else {   // 新增选中
            if (mSourceCoverParam.getImgList().size() == mSourceCoverParam.sourceMaxNum) {
                ToastUtils.showShort("最多选择" + mSourceCoverParam.sourceMaxNum + "张");
                return;
            }
            if (TextUtils.isEmpty(((NetImgVo) adapter.getItem(position)).getThumbURL())) {
                return;
            }
            ((NetImgVo) adapter.getItem(position)).setSelected(!((NetImgVo) adapter.getItem(position)).isSelected());
            mSourceCoverParam.getImgList().add(((NetImgVo) adapter.getItem(position)).getThumbURL());
            ((NetImgVo) adapter.getItem(position)).setNumber(String.valueOf(mSourceCoverParam.getImgList().size()));
        }
        adapter.notifyItemRangeChanged(position, 1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImgtype = getArguments().getInt("type");
        mSourceCoverParam = (SourceCoverParam) getArguments().getSerializable("mSourceCoverParam");
    }


    @Override
    public void initImmersionBar() {

    }

    /*
     *
     * 加载更多的时候调用
     * */
    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        this.smartRefreshLayout = smartRefreshLayout;
        if (mImgtype == IMGTYPE_BAIDU) {
            mViewModel.getBaiduImgList(keyWords);
        }
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
        this.smartRefreshLayout = smartRefreshLayout;
        if (mImgtype == IMGTYPE_BAIDU) {
            mViewModel.mBaiduImgPage = 0;
            mViewModel.getBaiduImgList(keyWords);
        }
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (mImgtype == IMGTYPE_BAIDU && adapter.getmObjects().size() == 0) {
            mViewModel.getBaiduImgList(keyWords);
        } else {
            if (adapter.getmObjects().size() == 0) {
                mViewModel.getDefaultImgList();
            }
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 105:
                mBinding.get().empty.hide();
                if (this.smartRefreshLayout != null) {
                    this.smartRefreshLayout.finishRefresh();
                    this.smartRefreshLayout.finishLoadMore();
                }
                if (mViewModel.mBaiduImgPage == 0) {
                    adapter.getmObjects().clear();
                    adapter.notifyDataSetChanged();
                }
                if ((viewEventResouce.data) != null && ((NetImgWapperVo) viewEventResouce.data).getData().size() > 0) {
                    int sta = adapter.getmObjects().size();
                    adapter.getmObjects().addAll(((NetImgWapperVo) viewEventResouce.data).getData());
                    adapter.notifyItemRangeChanged(sta, adapter.getmObjects().size());
                }
                break;

            case 106:
                mBinding.get().empty.hide();
                if ((viewEventResouce.data) != null && ((NetImgWapperVo) viewEventResouce.data).getData() != null && ((NetImgWapperVo) viewEventResouce.data).getData().size() > 0) {
                    adapter.getmObjects().addAll(((NetImgWapperVo) viewEventResouce.data).getData());
                    adapter.notifyDataSetChanged();
                } else {
                    mBinding.get().empty.showNodata();
                }
                break;


        }
    }
}
