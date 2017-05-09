package com.project.andredantas.memoro.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class Reminder {
    private long id;
    private String title;
    private String descript;
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

    public static List<Reminder> convertFromRealm(List<ReminderRealm> listRealm){
        List<Reminder> reminders = new ArrayList<>();
        for (ReminderRealm reminderRealm : listRealm) {
            reminders.add(Reminder.copyFromRealm(reminderRealm));
        }
        return reminders;
    }

    private static Reminder copyFromRealm(ReminderRealm reminderRealm){
        Reminder reminder = new Reminder();
        reminder.setTime(reminderRealm.getTime());
        reminder.setMinutes(reminderRealm.getMinutes());
        reminder.setActive(reminderRealm.isActive());
        reminder.setAudio(reminderRealm.getAudio());
        reminder.setDayAlarm(reminderRealm.getDayAlarm());
        reminder.setDescript(reminderRealm.getDescript());
        reminder.setHour(reminderRealm.getHour());
        reminder.setId(reminderRealm.getId());
        reminder.setMonthAlarm(reminderRealm.getMonthAlarm());
        reminder.setScheduleRelated(reminderRealm.getScheduleRelated());
        reminder.setTitle(reminderRealm.getTitle());
        reminder.setType(reminderRealm.getType());

        return reminder;
    }

    public static List<ReminderRealm> convertFromNormal(List<Reminder> listNormal){
        List<ReminderRealm> reminderRealmRealm = new ArrayList<>();
        for (Reminder reminder : listNormal) {
            reminderRealmRealm.add(Reminder.copyFromNormal(reminder));
        }
        return reminderRealmRealm;
    }

    public static ReminderRealm copyFromNormal(Reminder reminder){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ReminderRealm reminderRealm = new ReminderRealm();
        reminderRealm.setTime(reminder.getTime());
        reminderRealm.setMinutes(reminder.getMinutes());
        reminderRealm.setActive(reminder.isActive());
        reminderRealm.setAudio(reminder.getAudio());
        reminderRealm.setDayAlarm(reminder.getDayAlarm());
        reminderRealm.setDescript(reminder.getDescript());
        reminderRealm.setHour(reminder.getHour());
        reminderRealm.setId(reminder.getId());
        reminderRealm.setMonthAlarm(reminder.getMonthAlarm());
        reminderRealm.setScheduleRelated(reminder.getScheduleRelated());
        reminderRealm.setTitle(reminder.getTitle());
        reminderRealm.setType(reminder.getType());
        reminderRealm.setImage(reminder.getImage());

        realm.commitTransaction();
        return reminderRealm;
    }
}
