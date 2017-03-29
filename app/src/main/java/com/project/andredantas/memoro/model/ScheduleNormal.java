package com.project.andredantas.memoro.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class ScheduleNormal {
    private long id;
    private String day;
    private String title;
    private String descript;
    private String time;
    private String color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static List<ScheduleNormal> convertFromRealm(List<Schedule> listRealm){
        List<ScheduleNormal> scheduleNormals = new ArrayList<>();
        for (Schedule schedule : listRealm) {
            scheduleNormals.add(ScheduleNormal.copyFromRealm(schedule));
        }
        return scheduleNormals;
    }

    private static ScheduleNormal copyFromRealm(Schedule schedule){
        ScheduleNormal scheduleNormal = new ScheduleNormal();
        scheduleNormal.setActive(schedule.isActive());
        scheduleNormal.setMinutes(schedule.getMinutes());
        scheduleNormal.setHour(schedule.getHour());
        scheduleNormal.setDay(schedule.getDay());
        scheduleNormal.setColor(schedule.getColor());
        scheduleNormal.setDescript(schedule.getDescript());
        scheduleNormal.setId(schedule.getId());
        scheduleNormal.setTime(schedule.getTime());
        scheduleNormal.setTitle(schedule.getTitle());
        return scheduleNormal;
    }

    public static List<Schedule> convertFromNormal(List<ScheduleNormal> listNormal){
        List<Schedule> schedulesRealm = new ArrayList<>();
        for (ScheduleNormal scheduleNormal : listNormal) {
            schedulesRealm.add(ScheduleNormal.copyFromNormal(scheduleNormal));
        }
        return schedulesRealm;
    }

    public static Schedule copyFromNormal(ScheduleNormal scheduleNormal){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Schedule schedule = new Schedule();
        schedule.setActive(scheduleNormal.isActive());
        schedule.setMinutes(scheduleNormal.getMinutes());
        schedule.setHour(scheduleNormal.getHour());
        schedule.setDay(scheduleNormal.getDay());
        schedule.setColor(scheduleNormal.getColor());
        schedule.setDescript(scheduleNormal.getDescript());
        schedule.setId(scheduleNormal.getId());
        schedule.setTime(scheduleNormal.getTime());
        schedule.setTitle(scheduleNormal.getTitle());
        realm.commitTransaction();
        return schedule;
    }
}
