package com.docker.common.common.model;

import android.databinding.BaseObservable;

import java.io.Serializable;

public abstract class BaseSampleItem extends BaseObservable implements BaseItemModel, Serializable {

    public String sampleName = getClass().getSimpleName() + "style_0";


}
