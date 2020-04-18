package com.dgut.moment.Bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {

    private int id;
    private String username;
    private String password;
    private int diarycount;
    private int billcount;
    private int plancount;
    private String dpassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDiarycount() {
        return diarycount;
    }

    public void setDiarycount(int diarycount) {
        this.diarycount = diarycount;
    }

    public int getBillcount() {
        return billcount;
    }

    public void setBillcount(int billcount) {
        this.billcount = billcount;
    }

    public int getPlancount() {
        return plancount;
    }

    public void setPlancount(int plancount) {
        this.plancount = plancount;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", diarycount=" + diarycount +
                ", billcount=" + billcount +
                ", plancount=" + plancount +
                ", dpassword='" + dpassword + '\'' +
                '}';
    }
}
