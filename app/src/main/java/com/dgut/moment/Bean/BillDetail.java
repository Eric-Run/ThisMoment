package com.dgut.moment.Bean;

import org.litepal.crud.LitePalSupport;

public class BillDetail extends LitePalSupport {

    private int id;
    private String tag;
    private float sum;
    private String bday;

    public BillDetail() {
    }

    public BillDetail(String tag, float sum) {
        this.tag = tag;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", sum=" + sum +
                ", bday='" + bday + '\'' +
                '}'+"\n";
    }
}
