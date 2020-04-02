package com.dgut.moment.Bean;

import java.io.Serializable;

public class Diary implements Serializable {

    public int Did;
    public String Title;
    public String Content;
    public String Mood;
    public String Weather;
    public String Date;

    public Diary(String title, String content, String mood, String weather, String date) {
        Title = title;
        Content = content;
        Mood = mood;
        Weather = weather;
        Date = date;
    }

    public int getDid() {
        return Did;
    }

    public void setDid(int did) {
        Did = did;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMood() {
        return Mood;
    }

    public void setMood(String mood) {
        Mood = mood;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
