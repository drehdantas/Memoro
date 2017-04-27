package com.project.andredantas.memoro.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class ReminderNormal{
    private long id;
    private String title;
    private String descript;
    private String color;
    private String type;
    private int dayAlarm;
    private int monthAlarm;
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

    public int getDayAlarm() {
        return dayAlarm;
    }

    public void setDayAlarm(int dayAlarm) {
        this.dayAlarm = dayAlarm;
    }

    public int getMonthAlarm() {
        return monthAlarm;
    }

    public void setMonthAlarm(int monthAlarm) {
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

    public static List<ReminderNormal> convertFromRealm(List<Reminder> listRealm){
        List<ReminderNormal> reminderNormals = new ArrayList<>();
        for (Reminder reminder : listRealm) {
            reminderNormals.add(ReminderNormal.copyFromRealm(reminder));
        }
        return reminderNormals;
    }

    private static ReminderNormal copyFromRealm(Reminder reminder){
        ReminderNormal reminderNormal = new ReminderNormal();
        reminderNormal.setTime(reminder.getTime());
        reminderNormal.setMinutes(reminder.getMinutes());
        reminderNormal.setActive(reminder.isActive());
        reminderNormal.setAudio(reminder.getAudio());
        reminderNormal.setColor(reminder.getColor());
        reminderNormal.setDayAlarm(reminder.getDayAlarm());
        reminderNormal.setDescript(reminder.getDescript());
        reminderNormal.setHour(reminder.getHour());
        reminderNormal.setId(reminder.getId());
        reminderNormal.setMonthAlarm(reminder.getMonthAlarm());
        reminderNormal.setScheduleRelated(reminder.getScheduleRelated());
        reminderNormal.setTitle(reminder.getTitle());
        reminderNormal.setType(reminder.getType());

        return reminderNormal;
    }

    public static List<Reminder> convertFromNormal(List<ReminderNormal> listNormal){
        List<Reminder> reminderRealm = new ArrayList<>();
        for (ReminderNormal reminderNormal : listNormal) {
            reminderRealm.add(ReminderNormal.copyFromNormal(reminderNormal));
        }
        return reminderRealm;
    }

    public static Reminder copyFromNormal(ReminderNormal reminderNormal){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Reminder reminder = new Reminder();
        reminder.setTime(reminderNormal.getTime());
        reminder.setMinutes(reminderNormal.getMinutes());
        reminder.setActive(reminderNormal.isActive());
        reminder.setAudio(reminderNormal.getAudio());
        reminder.setColor(reminderNormal.getColor());
        reminder.setDayAlarm(reminderNormal.getDayAlarm());
        reminder.setDescript(reminderNormal.getDescript());
        reminder.setHour(reminderNormal.getHour());
        reminder.setId(reminderNormal.getId());
        reminder.setMonthAlarm(reminderNormal.getMonthAlarm());
        reminder.setScheduleRelated(reminderNormal.getScheduleRelated());
        reminder.setTitle(reminderNormal.getTitle());
        reminder.setType(reminderNormal.getType());
        reminder.setImage(reminderNormal.getImage());

        realm.commitTransaction();
        return reminder;
    }
}
