package com.bfhd.evaluate.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * kxf -> 2019-09-06
 **/
public class StudyReadVo implements Parcelable {


    /**
     * order : 1
     * timestamp : 00:00.00
     * timestamps : [00:00.00],[00:04.44]
     * text : 你好，我想买些名片。
     * en : Hello. I would like to buy some business cards.
     * hashvalue : YGhDbPBfJRdM1RmfDtO/8y2AycM=
     */

    private int order;
    private String timestamp;
    private String timestamps;
    private String text;
    private String en;
    private String hashvalue;
    private boolean isChoose = false;
    private boolean isCH = false;

    private String img;

    protected StudyReadVo(Parcel in) {
        order = in.readInt();
        timestamp = in.readString();
        timestamps = in.readString();
        text = in.readString();
        en = in.readString();
        hashvalue = in.readString();
        isChoose = in.readByte() != 0;
        isCH = in.readByte() != 0;
        img = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order);
        dest.writeString(timestamp);
        dest.writeString(timestamps);
        dest.writeString(text);
        dest.writeString(en);
        dest.writeString(hashvalue);
        dest.writeByte((byte) (isChoose ? 1 : 0));
        dest.writeByte((byte) (isCH ? 1 : 0));
        dest.writeString(img);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StudyReadVo> CREATOR = new Creator<StudyReadVo>() {
        @Override
        public StudyReadVo createFromParcel(Parcel in) {
            return new StudyReadVo(in);
        }

        @Override
        public StudyReadVo[] newArray(int size) {
            return new StudyReadVo[size];
        }
    };

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public boolean isCH() {
        return isCH;
    }

    public void setCH(boolean CH) {
        isCH = CH;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getHashvalue() {
        return hashvalue;
    }

    public void setHashvalue(String hashvalue) {
        this.hashvalue = hashvalue;
    }

    public StudyReadVo(int order, String timestamp, String timestamps, String text, String en, String hashvalue, boolean isChoose, boolean isCH, String img) {
        this.order = order;
        this.timestamp = timestamp;
        this.timestamps = timestamps;
        this.text = text;
        this.en = en;
        this.hashvalue = hashvalue;
        this.isChoose = isChoose;
        this.isCH = isCH;
        this.img = img;
    }

    @Override
    public String toString() {
        return "StudyReadVo{" +
                "order=" + order +
                ", timestamp='" + timestamp + '\'' +
                ", timestamps='" + timestamps + '\'' +
                ", text='" + text + '\'' +
                ", en='" + en + '\'' +
                ", hashvalue='" + hashvalue + '\'' +
                ", isChoose=" + isChoose +
                ", isCH=" + isCH +
                ", img='" + img + '\'' +
                '}';
    }

    public StudyReadVo() {
    }


}
