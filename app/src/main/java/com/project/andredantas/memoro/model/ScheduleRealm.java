package com.project.andredantas.memoro.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class ScheduleRealm extends RealmObject{
    public static String BLUE = "blue";
    public static String RED = "red";
    public static String YELLOW = "yellow";
    public static String GREEN = "green";

    @PrimaryKey
    private long id;
    private String day;
    private String title;
    private String descript;
    private String time;
    private ColorRealm colorRealm;
    private int hour;
    private int minutes;
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ColorRealm getColorRealm() {
        return colorRealm;
    }

    public void setColorRealm(ColorRealm colorRealm) {
        this.colorRealm = colorRealm;
    }
}
