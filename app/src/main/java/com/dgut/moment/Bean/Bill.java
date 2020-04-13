package com.dgut.moment.Bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Collection;
import java.util.List;

public class Bill extends LitePalSupport implements Comparable<Bill> {

    public int bid;
    public String billday;
    public float income;
    public float outgo;
    public List<BillDetail> billdetail;

    public Bill() {
    }

    public Bill(String billDay, float dayIncome, float dayOutgo) {
        billday = billDay;
        income = dayIncome;
        outgo = dayOutgo;
    }

    public Bill(String billDay, float dayIncome, float dayOutgo, List<BillDetail> billdetail) {
        billday = billDay;
        income = dayIncome;
        outgo = dayOutgo;
        this.billdetail = billdetail;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBillday() {
        return billday;
    }

    public void setBillday(String billday) {
        this.billday = billday;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getOutgo() {
        return outgo;
    }

    public void setOutgo(float outgo) {
        this.outgo = outgo;
    }

    public List<BillDetail> getBilldetail() {
        return LitePal.where("bday = ?", String.valueOf(billday)).find(BillDetail.class);
    }

    public void setBilldetail(List<BillDetail> billdetail) {
        this.billdetail = billdetail;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "bid=" + bid +
                ", billday='" + billday + '\'' +
                ", income=" + income +
                ", outgo=" + outgo +
                ", billdetail=" + getBilldetail() +
                '}'+"\n";
    }

    @Override
    public int compareTo(Bill o) {
        if(bid > o.getBid()) return 1;
        if(bid < o.getBid()) return -1;
        return 0;
    }
}
