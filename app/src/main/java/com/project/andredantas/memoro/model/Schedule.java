package com.project.andredantas.memoro.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class Schedule {
    public static int NOT_ALERT = 0;
    public static int ONCE_ALERT = 1;
    public static int ALWAYS_ALERT = 2;

    public static int ALERT_ON_TIME = 0;
    public static int ALERT_10_BEFORE = 1;
    public static int ALERT_30_BEFORE = 2;

    private long id;
    private String day;
    private String title;
    private String descript;
    private String time;
    private int hour;
    private int minutes;
    private boolean active = true;
    private int alertType;
    private int alertFrequency;
    private ColorRealm colorRealm;

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

    public int getAlertType() {
        return alertType;
    }

    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }

    public int getAlertFrequency() {
        return alertFrequency;
    }

    public void setAlertFrequency(int alertFrequency) {
        this.alertFrequency = alertFrequency;
    }

    public static List<Schedule> convertFromRealm(List<ScheduleRealm> listRealm){
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleRealm scheduleRealm : listRealm) {
            schedules.add(Schedule.copyFromRealm(scheduleRealm));
        }
        return schedules;
    }

    public static Schedule copyFromRealm(ScheduleRealm scheduleRealm){
        Schedule schedule = new Schedule();
        schedule.setActive(scheduleRealm.isActive());
        schedule.setMinutes(scheduleRealm.getMinutes());
        schedule.setHour(scheduleRealm.getHour());
        schedule.setDay(scheduleRealm.getDay());
        schedule.setColorRealm(scheduleRealm.getColorRealm());
        schedule.setDescript(scheduleRealm.getDescript());
        schedule.setId(scheduleRealm.getId());
        schedule.setTime(scheduleRealm.getTime());
        schedule.setTitle(scheduleRealm.getTitle());
        schedule.setAlertType(scheduleRealm.getAlertType());
        schedule.setAlertFrequency(scheduleRealm.getAlertFrequency());
        return schedule;
    }

    public static List<ScheduleRealm> convertFromNormal(List<Schedule> listNormal){
        List<ScheduleRealm> schedulesRealm = new ArrayList<>();
        for (Schedule schedule : listNormal) {
            schedulesRealm.add(Schedule.copyFromNormal(schedule));
        }
        return schedulesRealm;
    }

    public static ScheduleRealm copyFromNormal(Schedule schedule){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ScheduleRealm scheduleRealm = new ScheduleRealm();
        scheduleRealm.setActive(schedule.isActive());
        scheduleRealm.setMinutes(schedule.getMinutes());
        scheduleRealm.setHour(schedule.getHour());
        scheduleRealm.setDay(schedule.getDay());
        scheduleRealm.setColorRealm(schedule.getColorRealm());
        scheduleRealm.setDescript(schedule.getDescript());
        scheduleRealm.setId(schedule.getId());
        scheduleRealm.setTime(schedule.getTime());
        scheduleRealm.setTitle(schedule.getTitle());
        scheduleRealm.setAlertType(schedule.getAlertType());
        scheduleRealm.setAlertFrequency(schedule.getAlertFrequency());
        realm.commitTransaction();
        return scheduleRealm;
    }
}
