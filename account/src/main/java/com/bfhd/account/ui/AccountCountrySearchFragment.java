package com.bfhd.account.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountFragmentSearchListBinding;
import com.bfhd.circle.base.EmptyVm;
import com.bfhd.circle.base.HivsBaseFragment;
import com.docker.common.common.vo.WorldNumList;
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * : 搜索结果显示Fragment
 */
public class AccountCountrySearchFragment extends HivsBaseFragment<EmptyVm, AccountFragmentSearchListBinding> {
    private SearchAdapter mAdapter;
    public List<WorldNumList.WorldEnty> mDatas;

    private String mQueryText;
    private static int curtype = 0;

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment_search_list;
    }

    @Override
    protected EmptyVm getViewModel() {
        return ViewModelProviders.of(this, factory).get(EmptyVm.class);
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().recyclerView.setLayoutManager(new LinearLayoutManager(this.getHoldingActivity()));
        mBinding.get().recyclerView.setHasFixedSize(true);
        mBinding.get().recyclerView.setAdapter(mAdapter);
        if (mQueryText != null) {
            mAdapter.getFilter().filter(mQueryText);
        }
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this.getHoldingActivity())));
    }

    public void bindDatas(List<WorldNumList.WorldEnty> datas) {
        this.mDatas = datas;
        if (mAdapter == null) {
            mAdapter = new SearchAdapter();
        }
    }


    public void bindDatas(List<WorldNumList.WorldEnty> datas, int position) {
        this.mDatas = datas;
        if (mAdapter == null) {
            mAdapter = new SearchAdapter();
        }
        this.curtype = position;
    }

    /**
     * 根据newText 进行查找, 显示
     */
    public void bindQueryText(String newText) {
        if (mDatas == null) {
            mQueryText = newText.toLowerCase();
        } else if (!TextUtils.isEmpty(newText)) {
            mAdapter.getFilter().filter(newText.toLowerCase());
        }
    }

    @Override
    public void initImmersionBar() {

    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> implements Filterable {
        private List<WorldNumList.WorldEnty> items = new ArrayList<>();

        public SearchAdapter() {
            items.clear();
            items.addAll(mDatas);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            final VH holder = new VH(LayoutInflater.from(getActivity()).inflate(R.layout.account_item_city, parent, false));
            holder.itemView.setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                WorldNumList.WorldEnty worldEnty = items.get(position);
                worldEnty.curtype = String.valueOf(curtype);
                Intent intent = new Intent();
                intent.putExtra("data", worldEnty.id);
                intent.putExtra("num", worldEnty.global_num);
                intent.putExtra("name", worldEnty.name);
                intent.putExtra("datasource", worldEnty);
                getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                getHoldingActivity().finish();
            });
            return holder;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tvName.setText(items.get(position).name);
//            holder.tvNum.setText(items.get(position).num + "");

        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    ArrayList<WorldNumList.WorldEnty> list = new ArrayList<>();
                    for (WorldNumList.WorldEnty item : mDatas) {
                        if (item.pinyin.startsWith(constraint.toString()) || item.name.contains(constraint)) {
                            list.add(item);
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.count = list.size();
                    results.values = list;
                    return results;
                }

                @Override
                @SuppressWarnings("unchecked")
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    ArrayList<WorldNumList.WorldEnty> list = (ArrayList<WorldNumList.WorldEnty>) results.values;
                    mAdapter.items.clear();
                    mAdapter.items.addAll(list);
                    if (results.count == 0) {
                        mBinding.get().empty.showNodata();
                    } else {
                        mBinding.get().empty.hide();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            };
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView tvName;
//            private TextView tvNum;

            public VH(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
//                tvNum = (TextView) itemView.findViewById(R.id.tv_mobile);
            }
        }
    }
}
