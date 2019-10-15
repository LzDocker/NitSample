package com.bfhd.circle.vo;

import java.io.Serializable;

public class PersionPerssionVo implements Serializable {

    public PersionPerssionVo(boolean isManager, boolean isComment, boolean isPublish) {
        this.isManager = isManager;
        this.isComment = isComment;
        this.isPublish = isPublish;
    }

    public boolean isManager;
    public boolean isComment;
    public boolean isPublish;

}
