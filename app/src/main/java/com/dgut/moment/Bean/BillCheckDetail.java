package com.dgut.moment.Bean;

public class BillCheckDetail {

    private int BCDid;
    private String tag;
    private String num;

    public BillCheckDetail(String tag, String num) {
        this.tag = tag;
        this.num = num;
    }

    public int getBCDid() {
        return BCDid;
    }

    public void setBCDid(int BCDid) {
        this.BCDid = BCDid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "BillCheckDetail{" +
                "tag='" + tag + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
