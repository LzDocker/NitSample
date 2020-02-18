package com.docker.apps.active.vo.card;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.view.View;

import com.docker.apps.R;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.vo.card.BaseCardVo;

import static com.dcbfhd.utilcode.utils.ActivityUtils.startActivity;

public class ActiveDetailHeadCard extends BaseCardVo {


    public ActiveDetailHeadCard(int style, int position) {
        super(style, position);
        mVmPath = "com.docker.apps.active.vm.ActiveHeadCardViewModel";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {
        callPhone(((ActiveDetailHeadCard) item).voObservableField.get().contact);
    }

    @Override
    public int getItemLayout() {
        return R.layout.pro_active_head_card;
    }


    public ObservableField<ActiveVo> voObservableField = new ObservableField<>();

    public ObservableField<ActiveVo> getVoObservableField() {
        return voObservableField;
    }

    public void setVoObservableField(ActiveVo activeVo) {
        this.voObservableField.set(activeVo);
//        bannerindex.set("1/1");
    }

    public ReplyCommandParam bannerIndex = new ReplyCommandParam() {
        @Override
        public void exectue(Object o) {
            bannerpos.set((String) o);
        }
    };

    public ObservableField<String> bannerpos = new ObservableField<>();


    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);

    }
}
