package com.bfhd.circle.vo.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// 圈子列表 入参数
public class StaCircleListParam implements Serializable {

 public Map<String,String> paramMap = new HashMap<>();

 /*
 * 0 内部自己控制刷新控件
 * 1 外部控制刷新
 * */
 public int Uity = 0;

 /*
 * 0 获取已加入的圈子
 * 1 获取圈子列表
 * */
 public int ReqType = 0;

}
