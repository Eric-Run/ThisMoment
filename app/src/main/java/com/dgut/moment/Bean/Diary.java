package com.dgut.moment.Bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Diary extends LitePalSupport implements Serializable  {

    private int id;
    private String title;  //日记标题
    private String content;  //日记内容
    private String mood;  //心情
    private String weather;  //天气
    private String date;  //日期

    public Diary() {
    }

    public Diary(String title, String content, String mood, String weather, String date) {
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.weather = weather;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", mood='" + mood + '\'' +
                ", weather='" + weather + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
