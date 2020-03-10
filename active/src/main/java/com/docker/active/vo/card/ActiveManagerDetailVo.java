package com.docker.active.vo.card;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class ActiveManagerDetailVo extends BaseObservable implements Serializable {

    public String title;
    public String thumb;
    public String ispayment;
    public String enrollNum;
    public String readNum;
    public String shareNum;
    public String collectNum;
    public String commentNum;
    public String isDate;
    public String startDate;
    public String endDate;
    public String status;
    public String shortUrl;
    public String detailUrl;

    public String dataid;

    public class EnrollfieldInfo {
        public String fieldType;
        public String fieldName;
        public String ismust;
        public String options;
        public String isselected;
        public String issys;
        public String sysField;
    }

}
