package com.docker.common.common.widget.card;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.docker.common.common.utils.lv.MserialMedatorLv;

import java.io.Serializable;

public class McardObserver implements Observer, Serializable {
    MserialMedatorLv mCardVoLiveData;

    public McardObserver(MserialMedatorLv mCardVoLiveData) {
        this.mCardVoLiveData = mCardVoLiveData;
    }

    public void addCardVo(){

    }

    @Override
    public void onChanged(@Nullable Object o) {
        if (o != null) {
            mCardVoLiveData.removeObserver(this);
        } else {

        }
    }
}
