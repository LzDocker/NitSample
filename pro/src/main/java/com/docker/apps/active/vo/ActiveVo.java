package com.docker.apps.active.vo;

import com.docker.apps.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.entity.ResourceBean;

import java.util.List;

public class ActiveVo extends BaseSampleItem {


    @Override
    public int getItemLayout() {
        return R.layout.pro_item_active;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }


    public String endDate;
    public String cityCode;
    public String sponsorName;
    public String utid;
    public String title;
    public String uuid;
    public String content;
    public List<ResourceBean> banner;

    public String contact;
    public String isDate;
    public String actType;
    public String circleid;
    public String lat;
    public String lng;
    public String limitNum;
    public String address;
    public String signAudit;
    public String situs;
    public String location;
    public String startDate;
    public String memberid;

    public List<ResourceBean> resource;

    public String updatetime;
    public String inputtime;
    public int status;
    public String dataid;

    /*
    *  "updatetime": 1581665407,
            "inputtime": 1581665407,
            "status": -1,
            "dataid": 46
    *
    *
    *
    * {
    "errno": "0",
    "errmsg": "ok",
    "rst": [
        {
            "endDate": "01/01",
            "cityCode": "101",
            "sponsorName": "zjjx",
            "utid": "8d93e6a11a530bafabe31724e2b35972",
            "title": "Android new",
            "uuid": "d901f5754f8b35622215ca8ad0825d4a",
            "content": "zjsnnd",
            "banner": [
                {
                    "img": "/static/var/upload/img20200214/upload/image/1581665300758_440x400.png",
                    "url": "/static/var/upload/img20200214/upload/image/1581665300758_440x400.png",
                    "sort": "1",
                    "t": "1"
                }
            ],
            "contact": "bznx",
            "isDate": "1",
            "actType": "3570",
            "circleid": "598",
            "lat": "99.9",
            "limitNum": "bxbx",
            "address": "v在不在家的",
            "lng": "88.99",
            "signAudit": "0",
            "situs": "2",
            "location": "北京市",
            "startDate": "01/01",
            "memberid": "938",
            "resource": [
                {
                    "img": "/static/var/upload/img20200214/upload/image/1581665300758_440x400.png",
                    "url": "/static/var/upload/img20200214/upload/image/1581665300758_440x400.png",
                    "sort": "1",
                    "t": "1"
                }
            ],
            "updatetime": 1581665407,
            "inputtime": 1581665407,
            "status": -1,
            "dataid": 46
        }
    ]
}
    * */

}

