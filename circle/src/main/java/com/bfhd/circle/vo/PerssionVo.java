package com.bfhd.circle.vo;

import java.io.Serializable;

public class PerssionVo implements Serializable {

    /*
    * "isPublishDynamic": "1",
      "c": "0",*/


    public String isPublishDynamic;
    public String isComment;

    public String getIsPublishDynamic() {
        return isPublishDynamic;
    }

    public void setIsPublishDynamic(String isPublishDynamic) {
        this.isPublishDynamic = isPublishDynamic;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }
}
