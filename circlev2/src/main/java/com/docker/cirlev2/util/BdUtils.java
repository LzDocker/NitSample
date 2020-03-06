package com.docker.cirlev2.util;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidubce.util.StringUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.MemberSettingsVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.entity.TradingCommonVo;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.MoneyDetailVo;
import com.docker.core.util.LayoutManager;
import com.library.flowlayout.FlowLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.docker.common.common.binding.CommonBdUtils.getCompleteImageUrl;

/*
 * xml 文件中的操作辅助类
 * */
public class BdUtils {


    public static boolean isJoin(String isjoin) {
        if (TextUtils.isEmpty(isjoin)) {
            return false;
        }
        return "1".equals(isjoin);
    }

    public static boolean isPointEmpty(String ss) {
        if ("0.00".equals(ss) || TextUtils.isEmpty(ss)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getAudioUrl(String url) {

        String utl = getCompleteImageUrl(url);
        if (utl.contains("https")) {
            String newUrl = utl.substring(0, 4) + utl.substring(5, utl.length());
            return newUrl;
        }
        return utl;

    }

    public static int getCommentPic(int position, int curposition) {
        if (position > curposition) {
            return R.mipmap.pro_comment_star1;
        } else {
            return R.mipmap.pro_comment_star2;
        }
    }

    public static boolean isShowStar(CommentVo commentVo) {
        if (commentVo == null) {
            return false;
        }
        if (TextUtils.isEmpty(String.valueOf(commentVo.starNum)) || commentVo.starNum == 0) {
            return false;
        } else {
            return true;
        }
    }

    private static SimpleDateFormat sdf = null;
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    public static String StringValveof(int obj) {
        return obj + "";
    }

    /*
     * 获取图片url
     * */
    public static String getImgUrl(String url) {
        return getCompleteImageUrl(url);
    }

    /*
     * 获取图片url
     * */
    public static String getResourceImgUrl(ServiceDataBean.ResourceBean resourceBean) {
        if (resourceBean != null) {
            if (TextUtils.isEmpty(resourceBean.getImg())) {
                return getImgUrl(resourceBean.getUrl());
            } else {
                return getImgUrl(resourceBean.getImg());
            }
        }
        return "";
    }

    public static void processVisiable(RecyclerView recyclerView, ServiceDataBean.ResourceBean resourceBean) {

    }

    /*获取时间
     * */
    public static String getStandardDate(String timeStr) {
        String str = "";
        long time = System.currentTimeMillis();
        Date d1 = new Date(time);
        if (timeStr != null) {
            Date d2 = new Date(Long.parseLong(timeStr + "000"));
            if (d1.getYear() - d2.getYear() > 0) {
                sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                str = sdf.format(d2);
            } else if (d1.getMonth() - d2.getMonth() == 0) {
                sdf = new SimpleDateFormat("HH:mm");
                if (d1.getDate() - d2.getDate() == 0) {
//                str = "今天" + sdf.format(d2);

                    if (d1.getHours() - d2.getHours() == 0) {
                        if (d1.getMinutes() - d2.getMinutes() >= 5) {
                            str = d1.getMinutes() - d2.getMinutes() + "分钟前";
                        } else {
                            str = "刚刚";
                        }
                    } else if (d1.getHours() - d2.getHours() == 1 && d1.getMinutes() - d2.getMinutes() < 0) {
                        if (d1.getMinutes() + 60 - d2.getMinutes() >= 5) {
                            str = d1.getMinutes() + 60 - d2.getMinutes() + "分钟前";
                        } else {
                            str = "刚刚";
                        }
                    } else {
                        str = d1.getHours() - d2.getHours() + "小时前";
                    }

                } else if (d1.getDate() - d2.getDate() == 1) {
                    str = "昨天" + sdf.format(d2);
                } else if (d1.getDate() - d2.getDate() == 2) {
                    str = "前天" + sdf.format(d2);
                } else {
                    sdf = new SimpleDateFormat("yyyy/M/d  HH:mm");
                    str = sdf.format(d2);
                }
            } else if (d1.getMonth() - d2.getMonth() == 1) {
                if (d2.getMonth() == 1) {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d2.getYear() % 4 == 0) {
                        if (d1.getDate() - d2.getDate() == -28) {
                            str = "昨天" + sdf.format(d2);
                        } else if (d1.getDate() - d2.getDate() == -27) {
                            str = "前天" + sdf.format(d2);
                        } else {
                            sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                            str = sdf.format(d2);
                        }
                    } else {
                        if (d1.getDate() - d2.getDate() == -27) {
                            str = "昨天" + sdf.format(d2);
                        } else if (d1.getDate() - d2.getDate() == -26) {
                            str = "前天" + sdf.format(d2);
                        } else {
                            sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                            str = sdf.format(d2);
                        }
                    }
                } else if (d2.getMonth() == 0 || d2.getMonth() == 2
                        || d2.getMonth() == 4 || d2.getMonth() == 6
                        || d2.getMonth() == 7 || d2.getMonth() == 9
                        || d2.getMonth() == 11) {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d1.getDate() - d2.getDate() == -30) {
                        str = "昨天" + sdf.format(d2);
                    } else if (d1.getDate() - d2.getDate() == -29) {
                        str = "前天" + sdf.format(d2);
                    } else {
                        sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                        str = sdf.format(d2);
                    }
                } else {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d1.getDate() - d2.getDate() == -29) {
                        str = "昨天" + sdf.format(d2);
                    } else if (d1.getDate() - d2.getDate() == -28) {
                        str = "前天" + sdf.format(d2);
                    } else {
                        sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                        str = sdf.format(d2);
                    }
                }
            } else {
                sdf = new SimpleDateFormat("yyyy/M/d HH:mm");
                str = sdf.format(d2);
            }

        }
        return str;
    }


    public static String getStandardDate(String timeStr, String parten) {

        String str = "";
        long time = System.currentTimeMillis();
        Date d1 = new Date(time);
        if (timeStr != null) {
            Date d2 = new Date(Long.parseLong(timeStr + "000"));
            if (d1.getYear() - d2.getYear() > 0) {
                sdf = new SimpleDateFormat(parten);
                str = sdf.format(d2);
            } else if (d1.getMonth() - d2.getMonth() == 0) {
                sdf = new SimpleDateFormat("HH:mm");
                if (d1.getDate() - d2.getDate() == 0) {
//                str = "今天" + sdf.format(d2);

                    if (d1.getHours() - d2.getHours() == 0) {
                        if (d1.getMinutes() - d2.getMinutes() >= 5) {
                            str = d1.getMinutes() - d2.getMinutes() + "分钟前";
                        } else {
                            str = "刚刚";
                        }
                    } else if (d1.getHours() - d2.getHours() == 1 && d1.getMinutes() - d2.getMinutes() < 0) {
                        if (d1.getMinutes() + 60 - d2.getMinutes() >= 5) {
                            str = d1.getMinutes() + 60 - d2.getMinutes() + "分钟前";
                        } else {
                            str = "刚刚";
                        }
                    } else {
                        str = d1.getHours() - d2.getHours() + "小时前";
                    }

                } else if (d1.getDate() - d2.getDate() == 1) {
                    str = "昨天" + sdf.format(d2);
                } else if (d1.getDate() - d2.getDate() == 2) {
                    str = "前天" + sdf.format(d2);
                } else {
                    sdf = new SimpleDateFormat(parten);
                    str = sdf.format(d2);
                }
            } else if (d1.getMonth() - d2.getMonth() == 1) {
                if (d2.getMonth() == 1) {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d2.getYear() % 4 == 0) {
                        if (d1.getDate() - d2.getDate() == -28) {
                            str = "昨天" + sdf.format(d2);
                        } else if (d1.getDate() - d2.getDate() == -27) {
                            str = "前天" + sdf.format(d2);
                        } else {
                            sdf = new SimpleDateFormat(parten);
                            str = sdf.format(d2);
                        }
                    } else {
                        if (d1.getDate() - d2.getDate() == -27) {
                            str = "昨天" + sdf.format(d2);
                        } else if (d1.getDate() - d2.getDate() == -26) {
                            str = "前天" + sdf.format(d2);
                        } else {
                            sdf = new SimpleDateFormat(parten);
                            str = sdf.format(d2);
                        }
                    }
                } else if (d2.getMonth() == 0 || d2.getMonth() == 2
                        || d2.getMonth() == 4 || d2.getMonth() == 6
                        || d2.getMonth() == 7 || d2.getMonth() == 9
                        || d2.getMonth() == 11) {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d1.getDate() - d2.getDate() == -30) {
                        str = "昨天" + sdf.format(d2);
                    } else if (d1.getDate() - d2.getDate() == -29) {
                        str = "前天" + sdf.format(d2);
                    } else {
                        sdf = new SimpleDateFormat(parten);
                        str = sdf.format(d2);
                    }
                } else {
                    sdf = new SimpleDateFormat("HH:mm");
                    if (d1.getDate() - d2.getDate() == -29) {
                        str = "昨天" + sdf.format(d2);
                    } else if (d1.getDate() - d2.getDate() == -28) {
                        str = "前天" + sdf.format(d2);
                    } else {
                        sdf = new SimpleDateFormat(parten);
                        str = sdf.format(d2);
                    }
                }
            } else {
                sdf = new SimpleDateFormat(parten);
                str = sdf.format(d2);
            }

        }
        return str;


    }

    public static String getAnswer(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getCommentUsers() != null && serviceDataBean.getCommentUsers().size() > 0) {
            return serviceDataBean.getCommentUsers().get(0).getContent();
        }
        return "";
    }

    public static String getAnswerName(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getCommentUsers() != null && serviceDataBean.getCommentUsers().size() > 0) {
            return serviceDataBean.getCommentUsers().get(0).getNickname();
        }
        return "";
    }

    public static boolean isShowSingleImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
            if (serviceDataBean.getExtData().getResource().size() == 1 && serviceDataBean.getExtData().getResource().get(0).getT() == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean isShowSingleNewsImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getNewsImgs() != null) {
            if (serviceDataBean.getExtData().getNewsImgs().size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * PHP时间戳转成java时间
     *
     * @param time
     * @return
     */
    public static String getTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String dateTime;
        Date d2 = new Date(Long.parseLong(time + "000"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = df.format(d2);
        return dateTime;
    }

    public static void setTextData(MoneyDetailVo moneyDetailVo, View view) {
        TextView textView = (TextView) view;
        String type = moneyDetailVo.getDotype();
        if ("1".equals(type)) {
            textView.setTextColor(Color.parseColor("#BD4952"));
        } else if ("2".equals(type)) {
            textView.setTextColor(Color.parseColor("#3F9A7C"));
        }
        textView.setText(moneyDetailVo.getOperation());

    }

    /**
     * java 时间转成PHP时间戳
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String DateTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static boolean isShowVideoSingleImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
            if (serviceDataBean.getExtData().getResource().size() == 1 && serviceDataBean.getExtData().getResource().get(0).getT() == 2) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static boolean isShowSingleImg(List<ServiceDataBean.ResourceBean> resource) {
        if (resource == null || resource.size() == 0) {
            return false;
        }
        if (resource.get(0).getImg() != null) {
            return true;
        } else {
            return false;
        }

    }

    public static String getCommentSingleImg(List<ServiceDataBean.ResourceBean> resource) {
        if (resource == null || resource.size() == 0) {
            return "";
        }
        if (resource.get(0).getImg() != null) {
            return getCompleteImageUrl(resource.get(0).getImg());
        } else {
            return "";
        }
    }

    public static String getCompleteImg(String imgurl) {
        if (TextUtils.isEmpty(imgurl)) {
            return "";
        }

        return getCompleteImageUrl(imgurl);

    }

    public static boolean isShowCommentVideoImg(List<ServiceDataBean.ResourceBean> resource) {
        if (resource == null || resource.size() == 0) {
            return false;
        }
        if (resource.get(0).getImg() != null && "2".equals(resource.get(0).getT())) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isshowBottomImg(ServiceDataBean serviceDataBean) {
        if (isShowSingleImg(serviceDataBean) || isShowVideoSingleImg(serviceDataBean)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isShowRvImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
            if (serviceDataBean.getExtData().getResource().size() > 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getDynamicSingleImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getResource() != null) {
            if (serviceDataBean.getExtData().getResource().size() > 0) {
                if (TextUtils.isEmpty(serviceDataBean.getExtData().getResource().get(0).getImg())) {
                    return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
                } else {
                    return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
                }
            }
        }
        return "";
    }

    public static String getDynamicNewsSingleImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean != null && serviceDataBean.getExtData() != null && serviceDataBean.getExtData().getNewsImgs() != null) {
            if (serviceDataBean.getExtData().getNewsImgs().size() > 0) {
                return serviceDataBean.getExtData().getNewsImgs().get(0);
            }
        }
        return "";
    }


    public static String replaySize(CommentVo commentVo) {
        if (commentVo != null) {
            return "查看全部" + (commentVo.getReplyNum()) + "条回复";
        }
        return "";
    }

    public static boolean isShowReplayMore(CommentVo commentVo) {

        if (commentVo != null) {
            if (!TextUtils.isEmpty(commentVo.getReplyNum())) {
                if (Integer.parseInt(commentVo.getReplyNum()) > 2) {
                    return true;
                }
            } else {
                return false;
            }
            return false;
        }
        return false;
    }


    public static String replyNickName(String nickname, String reply_nickname) {
        if (StringUtils.isEmpty(nickname)) {
            return reply_nickname;
        } else {
            return nickname + " 回复 " + reply_nickname + " : ";
        }
    }

    public static boolean isShowDel(CommentVo commentVo) {
        if (CacheUtils.getUser() != null) {
            return CacheUtils.getUser().uid.equals(commentVo.getMemberid());
        } else {
            return false;
        }
    }


    public static boolean isShowReplay(CommentVo commentVo) {
        if (commentVo != null && commentVo.getReply() != null) {
            if (commentVo.getReply().size() > 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isShowAudio(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null) {
            return false;
        }
        if (TextUtils.isEmpty(serviceDataBean.getExtData().getAudio()) || "0".equals(serviceDataBean.getExtData().getAudio())) {
            return false;
        } else {
            return true;
        }
    }

    public static String audioDuration(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null) {
            return "";
        }
        return TextUtils.isEmpty(serviceDataBean.getExtData().getAudio_duration()) ? "" : serviceDataBean.getExtData().getAudio_duration() + "''";
    }

    public static boolean isShowAudio(CommentVo commentVo) {
        if (commentVo == null) {
            return false;
        }
        return !TextUtils.isEmpty(commentVo.getAudio());
    }

    public static String audioDuration(CommentVo commentVo) {
        if (commentVo == null) {
            return "";
        }
        return TextUtils.isEmpty(commentVo.getAudio_duration()) ? "" : commentVo.getAudio_duration() + "''";
    }


    /*
     * 获取图片url
     * */
    public static String getEventImgUrl(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getExtData() == null || serviceDataBean.getExtData().getResource() == null || serviceDataBean.getExtData().getResource().size() == 0) {
            return "";
        }
        if (TextUtils.isEmpty(serviceDataBean.getExtData().getResource().get(0).getImg())) {
            return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
        } else {
            return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
        }
//        return Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
    }

    public static boolean isShowEventImg(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getExtData() == null || serviceDataBean.getExtData().getResource() == null || serviceDataBean.getExtData().getResource().size() == 0) {
            return false;
        }
        return true;
    }

    public static String getServerPushImgUrl(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getExtData() == null || serviceDataBean.getExtData().getResource() == null || serviceDataBean.getExtData().getResource().size() == 0) {
            return "";
        }
        if (TextUtils.isEmpty(serviceDataBean.getExtData().getResource().get(0).getImg())) {
            return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
        } else {
            return getImgUrl(serviceDataBean.getExtData().getResource().get(0).getImg());
        }
//        return Constant.getCompleteImageUrl(serviceDataBean.getExtData().getResource().get(0).getUrl());
    }

    public static String getReportType(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getExtData() == null) {
            return "";
        }
        String opty = "";
        switch (serviceDataBean.getExtData().option_type) {
            case "1":
                opty = "日报";
                break;
            case "2":
                opty = "周报";
                break;
            case "3":
                opty = "月报";
                break;
        }
        return opty;
    }


    public static String getSex(String sex) {
        if (TextUtils.isEmpty(sex)) {
            return "";
        } else if ("0".equals(sex)) {
            return "保密";
        } else if ("1".equals(sex)) {
            return "男";
        } else {
            return "女";
        }
    }


    public static String getBloodType(String bloodType) {
        if (TextUtils.isEmpty(bloodType)) {
            return "";
        } else if ("0".equals(bloodType)) {
            return "A型";
        } else if ("1".equals(bloodType)) {
            return "B型";
        } else if ("2".equals(bloodType)) {
            return "AB型";
        } else {
            return "o型";
        }

    }


    //1点赞 0取消点赞
    public static String isFav(int fav) {
        if (0 == (fav)) {
            return "点赞";
        } else {
            return "已赞";
        }
    }


    public static String getFavNum(int favnum) {
        if (favnum <= 0) {
            return "点赞";
        } else if (favnum > 100) {
            return "100+";
        } else {
            return String.valueOf(favnum);
        }
    }

    //1收藏 0取消收藏
    public static String isCollect(int collect) {
        if (0 == collect) {
            return "收藏";
        } else {
            return "收藏";
        }
    }

    //type:官方圈0 兴趣圈1 企业圈|2
    public static String circleType(String type) {

        if ("0".equals(type)) {
            return "官方圈";
        } else if ("1".equals(type)) {
            return "兴趣圈";
        } else {
            return "企业圈";
        }
    }

    public static boolean isShowJoin(CircleListVo circleListVo) {
        if (circleListVo == null) {
            return false;
        }
        if (TextUtils.isEmpty(circleListVo.getIsJoin())) {
            return false;
        }
        if (!"1".equals(circleListVo.getIsJoin())) {
            return true;
        }
        return false;
    }

    //0普通成员 1管理员
    public static String roleType(String type) {
        if ("0".equals(type)) {
            return "个人";
        } else {
            return "企业";
        }
    }  //0普通成员 1管理员

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static String getAnswerIcon(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getCommentUsers() == null || serviceDataBean.getCommentUsers().size() == 0) {
            return "";
        }
        if (TextUtils.isEmpty(serviceDataBean.getCommentUsers().get(0).getAvatar())) {
            return "";
        } else {
            return getImgUrl(serviceDataBean.getCommentUsers().get(0).getAvatar());
        }
    }

    public static boolean isShowAnswerIcon(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getCommentUsers() == null || serviceDataBean.getCommentUsers().size() == 0) {
            return false;
        }
        return true;
    }


    public static boolean isshowlv3(String warn) {
        if (TextUtils.isEmpty(warn)) {
            return false;
        }
        return "紧急警报".equals(warn);
    }


    public static boolean isshowlv2(String warn) {
        if (TextUtils.isEmpty(warn)) {
            return false;
        }
        return "信息警报".equals(warn);
    }


    public static boolean isshowlv1(String warn) {
        if (TextUtils.isEmpty(warn)) {
            return false;
        }
        return "警告警报".equals(warn);
    }


    // 警报推送标签文字颜色
    public static int getLevelTcolor(String warn) {
        if (TextUtils.isEmpty(warn)) {
            return R.color.circle_level1;
        }
        int drable = R.color.circle_level1;
        switch (warn) {
            case "信息警报":
                drable = R.color.circle_level2;
                break;
            case "警告警报":
                drable = R.color.circle_level1;
                break;
            case "紧急警报":
                drable = R.color.circle_level3;
                break;
        }
        return drable;
    }

    public static String getJob(String job) {
        if (TextUtils.isEmpty(job)) {
            return "未填写";
        }
        return job;
    }

    public static boolean isShowJoin(String isjoin) {
        if (TextUtils.isEmpty(isjoin)) {
            return false;
        }
        if ("1".equals(isjoin)) { // 已关注
            return true;
        } else {
            return false;
        }
    }

    public static String getUsernick(TradingCommonVo item) {
        if (item == null) {
            return "";
        }
        if (TextUtils.isEmpty(item.getFullName()) && TextUtils.isEmpty(item.getNickname())) {
            return "";
        }

        if (!TextUtils.isEmpty(item.getNickname())) {
            if (item.getNickname().length() <= 2) {
                return item.getNickname();
            } else {
                return item.getNickname().substring(item.getNickname().length() - 2, item.getNickname().length());
            }
        } else {
            if (!TextUtils.isEmpty(item.getFullName())) {
                if (item.getFullName().length() <= 2) {
                    return item.getFullName();
                } else {
                    return item.getFullName().substring(item.getFullName().length() - 2, item.getFullName().length());
                }
            }
        }
        return "";
    }

    public static String getUsername(MemberSettingsVo memberSettingsVo) {
        if (memberSettingsVo == null) {
            return "";
        }
        if (!TextUtils.isEmpty(memberSettingsVo.getNickname())) {
            return memberSettingsVo.getNickname();
        } else {
            return memberSettingsVo.getFullName();
        }
    }

    public static boolean isShowPop(String ss) {
        if (TextUtils.isEmpty(ss)) {
            return false;
        }
        if ("0".equals(ss)) {
            return false;
        } else {
            return true;
        }
    }

    public static String getGroupName(String name) {
        if ("false".equals(name) || TextUtils.isEmpty(name)) {
            return "未分组";
        } else {
            return name;
        }
    }

    public static String getPayattr(String payment, String integral, String real_price) {
        if (TextUtils.isEmpty(payment)) {
            return "";
        } else {
            if ("4".equals(payment)) {
                if (TextUtils.isEmpty(integral)) {
                    return "";
                } else {
                    return "合计" + integral + "积分";
                }
            } else {
                if (TextUtils.isEmpty(real_price)) {
                    return "";
                } else {
                    return "合计" + real_price + "元";
                }
            }
        }
    }

    public static String getConstus(String tel) {
        if (TextUtils.isEmpty(tel)) {
            return "";
        } else {
            return "联系我们:" + tel;
        }
    }

    public static String getMessageTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return "太极星球";
        } else {
            return title;
        }
    }

    public static boolean isDefault(String isDefault) {
        if (isDefault != null) {
            if ("1".equals(isDefault)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static String getSeeNum(String num) {
        if (TextUtils.isEmpty(num)) {
            return "";
        } else {
            if (Integer.parseInt(num) > 99) {
                return "99+ 付款";
            }
            return num + "人付款";
        }
    }

    public static boolean isShowPrice(String type) {
        if (TextUtils.isEmpty(type)) {
            return false;
        }
        if ("project".equals(type)) {
            return false;
        } else {
            return true;
        }
    }


    public static String getGoodsFoot(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null || serviceDataBean.getExtData() == null) {
            return "";
        }
        String time = "";
        if (!TextUtils.isEmpty(String.valueOf(serviceDataBean.getExtData().getInputtime()))) {
            time = getStandardDate(String.valueOf(serviceDataBean.getExtData().getInputtime()), "yyyy.M.d");
        } else {
            time = getStandardDate(String.valueOf(serviceDataBean.getInputtime()), "yyyy.M.d");
        }
        String address = "";
        if (TextUtils.isEmpty(serviceDataBean.area2)) {
            address = serviceDataBean.area1;
        } else {
            address = serviceDataBean.area2;
        }
        if (TextUtils.isEmpty(address)) {
            address = "";
        }
        return time + " | " + address;
    }


    // 积分
    public static String getPoint(ServiceDataBean serviceDataBean) {

        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                return "赠送" + serviceDataBean.getExtData().point + "积分";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    // money
    public static String getPayMoney(ServiceDataBean serviceDataBean) {
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                return serviceDataBean.getExtData().price;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getPayMoneyforHourse(ServiceDataBean serviceDataBean) {
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                if (!TextUtils.isEmpty(serviceDataBean.getExtData().zu_date)) {
                    if (serviceDataBean.getExtData().zu_date.contains("每")) {
                        return serviceDataBean.getExtData().price + "/" + serviceDataBean.getExtData().zu_date.replace("每", "");
                    } else {
                        return serviceDataBean.getExtData().price + "/" + serviceDataBean.getExtData().zu_date;
                    }
                } else {
                    return serviceDataBean.getExtData().price;
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    public static boolean CheckServerData(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckServerExtData(ServiceDataBean.ExtDataBean extDataBean) {
        if (extDataBean == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String getUserVoType(String regtype) {
        if (TextUtils.isEmpty(regtype)) {
            return "星球居民";
        }
        if ("1".equals(regtype)) {
            return "星球居民";
        } else {
            return "和太极家族成员";
        }
    }

    public static String getUserType(String string) {
        if (TextUtils.isEmpty(string)) {
            return "星球居民";
        } else {
            return string;
        }
    }

    public static String getUserType2(String string) {
        if (TextUtils.isEmpty(string)) {
            return "星球居民";
        } else {
            return string + "家族成员";
        }
    }

    public static String getGoodsTitleSpeical(ServiceDataBean serviceDataBean) {
        StringBuilder title = new StringBuilder();
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                title.append("$自营$  ");
                if (TextUtils.isEmpty(serviceDataBean.getExtData().getName())) {
                    title.append(serviceDataBean.getExtData().getContent());
                    return title.toString();
                } else {
                    title.append(serviceDataBean.getExtData().getName()).append(serviceDataBean.getExtData().getContent());
                    return title.toString();
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getPayWay(String payway, String is_point) {
        String way = "";
        if (!TextUtils.isEmpty(payway) && !TextUtils.isEmpty(is_point)) {
            if ("1".equals(payway)) {
                if ("1".equals(is_point)) {
                    way = "微信+积分支付";
                } else {
                    way = "微信支付";
                }
            } else if ("2".equals(payway)) {
                if ("1".equals(is_point)) {
                    way = "支付宝+积分支付";
                } else {
                    way = "支付宝支付";
                }
            } else if ("3".equals(payway)) {
                if ("1".equals(is_point)) {
                    way = "钱包余额+积分支付";
                } else {
                    way = "钱包余额支付";
                }
            } else if ("4".equals(payway)) {
                way = "积分支付";
            }
        }
        return way;
    }

    public static String getWlStatus(String status) {
        String string = "";
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    string = "在途中";
                    break;
                case "1":
                    string = "已揽收";
                    break;
                case "2":
                    string = "疑难";
                    break;
                case "3":
                    string = "已签收";
                    break;
                case "4":
                    string = "已退签";
                    break;
                case "5":
                    string = "正在派件";
                    break;
                case "6":
                    string = "已退回";
                    break;
                case "7":
                    string = "已转投";
                    break;
                default:
                    string = "等待揽件";
                    break;
            }
        }
        return string;
    }

    public static String getGoodsTitle(ServiceDataBean serviceDataBean) {

        StringBuilder title = new StringBuilder();
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                if (TextUtils.isEmpty(serviceDataBean.getExtData().getName())) {
                    title.append(serviceDataBean.getExtData().getContent());
                    return title.toString();
                } else {
                    title.append(serviceDataBean.getExtData().getName()).append(serviceDataBean.getExtData().getContent());
                    return title.toString();
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static boolean getLeftDrawable(ServiceDataBean serviceDataBean) {
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                if (TextUtils.isEmpty(serviceDataBean.getExtData().getName())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static String getHourseInfo(ServiceDataBean serviceDataBean) {
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                String huixing = serviceDataBean.getExtData().huxing;
                String mianji = serviceDataBean.getExtData().mianji + "㎡";
                String location = serviceDataBean.getExtData().location;
                return huixing + " | " + mianji + " | " + location;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getHourseLocInfo(ServiceDataBean serviceDataBean) {
        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                String location = serviceDataBean.getExtData().location;
                return "定位于" + location;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }


    public static boolean isShowPoint(ServiceDataBean serviceDataBean) {

        if (CheckServerData(serviceDataBean)) {
            if (CheckServerExtData(serviceDataBean.getExtData())) {
                if (TextUtils.isEmpty(serviceDataBean.getExtData().point)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int detailFootStyle(ServiceDataBean serviceDataBean) {

        if (CheckServerData(serviceDataBean)) {
            if ("goods".equals(serviceDataBean.getType())) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 2;
        }
    }

    public static ArrayList<String> getBannerImg(List<ServiceDataBean.ResourceBean> resourceBeans) {

        if (resourceBeans == null) {
            return null;
        }
        ArrayList<String> imglist = new ArrayList<>();
        for (int i = 0; i < resourceBeans.size(); i++) {
            imglist.add(resourceBeans.get(i).getImg());
        }
        return imglist;
    }

    public static String getActiveTimeStr(String sta, String end) {
        return sta + " 至 " + end;
    }

}