package com.project.andredantas.memoro.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class Reminder extends RealmObject {
    @PrimaryKey
    private long id;
    private String title;
    private String descript;
    private String color;
    private String type;
    private String dayAlarm;
    private String monthAlarm;
    private String time;
    private int hour;
    private int minutes;
    private long scheduleRelated;
    private boolean active = true;

    private String image;
    private String audio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public long getScheduleRelated() {
        return scheduleRelated;
    }

    public void setScheduleRelated(long scheduleRelated) {
        this.scheduleRelated = scheduleRelated;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDayAlarm() {
        return dayAlarm;
    }

    public void setDayAlarm(String dayAlarm) {
        this.dayAlarm = dayAlarm;
    }

    public String getMonthAlarm() {
        return monthAlarm;
    }

    public void setMonthAlarm(String monthAlarm) {
        this.monthAlarm = monthAlarm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}