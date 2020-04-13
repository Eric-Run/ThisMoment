package com.dgut.moment.Bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class Bill extends LitePalSupport {

    public int bid;
    public String billday;
    public double income;
    public double outgo;
    public List<BillDetail> billdetail;

    public Bill() {
    }

    public Bill(String billDay, double dayIncome, double dayOutgo) {
        billday = billDay;
        income = dayIncome;
        outgo = dayOutgo;
    }

    public Bill(String billDay, double dayIncome, double dayOutgo, List<BillDetail> billdetail) {
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getOutgo() {
        return outgo;
    }

    public void setOutgo(double outgo) {
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
}
