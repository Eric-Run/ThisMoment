package com.dgut.moment.Bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Plan extends LitePalSupport implements Serializable {

    private int id;
    private String content;  //计划内容
    private String plantime;  //计划时间
    private int previousTime;  //计划时间
    private int isfinished = 0;  //是否完成，1完成，0未完成
    private int isremind = 0;  //是否提醒，1提醒，0未提醒


    public Plan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPlantime() {
        return plantime;
    }

    public void setPlantime(String plantime) {
        this.plantime = plantime;
    }

    public int getIsfinished() {
        return isfinished;
    }

    public void setIsfinished(int isfinished) {
        this.isfinished = isfinished;
    }

    public int getIsremind() {
        return isremind;
    }

    public void setIsremind(int isremind) {
        this.isremind = isremind;
    }

    public int getPreviousTime() {
        return previousTime;
    }

    public void setPreviousTime(int previousTime) {
        this.previousTime = previousTime;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", plantime='" + plantime + '\'' +
                ", previousTime=" + previousTime +
                ", isfinished=" + isfinished +
                ", isremind=" + isremind +
                '}';
    }
}
