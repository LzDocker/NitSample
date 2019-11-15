package com.docker.cirlev2.vo.entity;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.List;

public class CircleGroupPerssionVo extends BaseObservable implements Serializable {


 /*
 * "ungroupedIsPublishDynamic": "1",
      "ungroupedIsComment": "1",
      "allIsComment": "1" ,
      "allIsPublishDynamic": "1",
 * */


    public String ungroupedIsPublishDynamic;
    public String ungroupedIsComment;
    public String allIsComment;
    public String allIsPublishDynamic;
    public List<Group> group;


    public class Group extends BaseObservable {

        /*
        *
        * "groupid":"",
               "groupName":"",
               "isPublishDynamic":"",
               "isComment":"",
    */
        public String groupid;
        public String groupName;
        public int isPublishDynamic;
        public int isComment;
        public boolean isExpand = false;

    }

}
