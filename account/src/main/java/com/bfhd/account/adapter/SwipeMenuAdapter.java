package com.bfhd.account.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bfhd.account.R;
import com.bfhd.account.myview.SwipeMenuView;
import com.bfhd.account.vo.HelpUserVo;
import com.docker.common.common.command.ReplyCommandParam;

public class SwipeMenuAdapter extends ListBaseAdapter<HelpUserVo> {

    public SwipeMenuAdapter(Context context, ReplyCommandParam replyCommand) {
        super(context);
        this.replyCommand = replyCommand;
    }

    private ReplyCommandParam replyCommand;

    @Override
    public int getLayoutId() {
        return R.layout.list_item_swipe;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        View contentView = holder.getView(R.id.swipe_content);
        TextView tv_name = holder.getView(R.id.tv_name);
        TextView tv_phone = holder.getView(R.id.tv_phone);
        TextView tv_remark = holder.getView(R.id.tv_remark);
        TextView tv_area_code = holder.getView(R.id.tv_area_code);

        Button btnDelete = holder.getView(R.id.btnDelete);


        //这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        ((SwipeMenuView) holder.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : true);

//        title.setText(getDataList().get(position).title + (position % 2 == 0 ? "我只能右滑动" : "我只能左滑动"));
        tv_name.setText(getDataList().get(position).getConcat_name());
        tv_phone.setText(getDataList().get(position).getConcat_phone());
        tv_remark.setText(getDataList().get(position).getRemark());
        tv_area_code.setText(getDataList().get(position).getConcat_area_code());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                    //且如果想让侧滑菜单同时关闭，需要同时调用 ((CstSwipeDelMenu) holder.itemView).quickClose();
                    //((CstSwipeDelMenu) holder.itemView).quickClose();
                    mOnSwipeListener.onDel(position);

                }
            }
        });
        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppToast.makeShortToast(mContext, getDataList().get(position).title);
                //Log.d("TAG", "onClick() called with: v = [" + v + "]");
                replyCommand.exectue(getDataList().get(position));
            }
        });

    }

    /**
     * 和Activity通信的接口
     */
    public interface onSwipeListener {
        void onDel(int pos);

        void onTop(int pos);
    }

    private onSwipeListener mOnSwipeListener;

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }


}

