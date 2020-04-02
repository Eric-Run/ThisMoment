package com.dgut.moment.Bean;

import java.util.List;

public class BillCheckItem {

    public int BCIid;
    public String BillDay;
    public double DayIncome;
    public double DayOutgo;
    public List<BillCheckDetail> billCheckDetails;

    public BillCheckItem(String billDay, double dayIncome, double dayOutgo) {
        BillDay = billDay;
        DayIncome = dayIncome;
        DayOutgo = dayOutgo;
    }

    public BillCheckItem(String billDay, double dayIncome, double dayOutgo, List<BillCheckDetail> billCheckDetails) {
        BillDay = billDay;
        DayIncome = dayIncome;
        DayOutgo = dayOutgo;
        this.billCheckDetails = billCheckDetails;
    }

    public int getBCIid() {
        return BCIid;
    }

    public void setBCIid(int BCIid) {
        this.BCIid = BCIid;
    }

    public List<BillCheckDetail> getBillCheckDetails() {
        return billCheckDetails;
    }

    public void setBillCheckDetails(List<BillCheckDetail> billCheckDetails) {
        this.billCheckDetails = billCheckDetails;
    }

    public void setBillDay(String billDay) {
        BillDay = billDay;
    }

    public void setDayIncome(double dayIncome) {
        DayIncome = dayIncome;
    }

    public void setDayOutgo(double dayOutgo) {
        DayOutgo = dayOutgo;
    }

    public String getBillDay() {
        return BillDay;
    }

    public double getDayIncome() {
        return DayIncome;
    }

    public double getDayOutgo() {
        return DayOutgo;
    }

    @Override
    public String toString() {
        return "BillCheckItem{" +
                "BillDay='" + BillDay + '\'' +
                ", DayIncome=" + DayIncome +
                ", DayOutgo=" + DayOutgo +
                ", billCheckDetails=" + billCheckDetails +
                '}';
    }
}
