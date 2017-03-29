package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.Reminder;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class ReminderDAO {

    public static void saveReminder(Realm realm, Reminder reminder) {
        realm.beginTransaction();
        realm.copyToRealm(reminder);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateReminder(Realm realm, long id, String title, String descript, Schedule scheduleChosen, int selectedHour, int selectedMinute, int day, int month, AudioRecordLayout audioRecordLayout, String fileImage, String type) {
        realm.beginTransaction();
        Reminder reminder = getById(id);
        reminder.setTitle(title);
        reminder.setDescript(descript);
        reminder.setScheduleRelated(scheduleChosen != null ? scheduleChosen.getId() : 1);

        if (selectedHour != 99){
            reminder.setHour(selectedHour);
            reminder.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (selectedMinute != 99){
            reminder.setMinutes(selectedMinute);
            reminder.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (day != 99){
            reminder.setDayAlarm(day);
        }

        if (month != 99){
            reminder.setMonthAlarm(month);
        }

        if (audioRecordLayout.isStopped()) {
            reminder.setAudio(audioRecordLayout.getFilePath());
        }
        if (fileImage != null) {
            reminder.setImage(fileImage);
        }

        reminder.setType(type);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteReminder(Realm realm, Reminder reminder) {
        realm.beginTransaction();
        reminder.setActive(false);
        realm.copyToRealmOrUpdate(reminder);
        realm.commitTransaction();
        realm.close();
    }

    public static Reminder getById(long reminderId){
        return Realm.getDefaultInstance().where(Reminder.class).equalTo("id", reminderId).findFirst();
    }

    public static List<Reminder> listReminders(){
        List<Reminder> reminders = Realm.getDefaultInstance()
                .where(Reminder.class)
                .equalTo("active", true)
                .findAllSorted("id", Sort.DESCENDING);

        return reminders;
    }
}
