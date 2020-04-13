package com.dgut.moment.Bean;

import org.litepal.crud.LitePalSupport;

public class Plan extends LitePalSupport {

    private int Pid;
    private String Content;  //计划内容
    private String PlanTime;  //计划时间
    private int isFinished;  //是否完成，1完成，0未完成

    public Plan() {
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPlanTime() {
        return PlanTime;
    }

    public void setPlanTime(String planTime) {
        PlanTime = planTime;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "Plan{" +
                ", Content='" + Content + '\'' +
                ", PlanTime='" + PlanTime + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
