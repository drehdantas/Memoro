package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.ReminderRealm;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class ReminderDAO {

    public static void saveReminder(Realm realm, ReminderRealm reminderRealm) {
        realm.beginTransaction();
        realm.copyToRealm(reminderRealm);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateReminder(Realm realm, long id, String title, String descript, ScheduleRealm scheduleRealmChosen, int selectedHour, int selectedMinute, int day, int month, AudioRecordLayout audioRecordLayout, String fileImage, String type) {
        realm.beginTransaction();
        ReminderRealm reminderRealm = getById(id);
        reminderRealm.setTitle(title);
        reminderRealm.setDescript(descript);
        reminderRealm.setScheduleRelated(scheduleRealmChosen.getId());

        if (selectedHour != 99){
            reminderRealm.setHour(selectedHour);
            reminderRealm.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (selectedMinute != 99){
            reminderRealm.setMinutes(selectedMinute);
            reminderRealm.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (day != 99){
            reminderRealm.setDayAlarm(day);
        }

        if (month != 99){
            reminderRealm.setMonthAlarm(month);
        }

        if (audioRecordLayout.isStopped()) {
            reminderRealm.setAudio(audioRecordLayout.getFilePath());
        }
        if (fileImage != null) {
            reminderRealm.setImage(fileImage);
        }

        reminderRealm.setType(type);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteReminder(Realm realm, ReminderRealm reminderRealm) {
        realm.beginTransaction();
        reminderRealm.setActive(false);
        realm.copyToRealmOrUpdate(reminderRealm);
        realm.commitTransaction();
        realm.close();
    }

    public static ReminderRealm getById(long reminderId){
        return Realm.getDefaultInstance().where(ReminderRealm.class).equalTo("id", reminderId).findFirst();
    }

    public static List<ReminderRealm> listReminders(){
        List<ReminderRealm> reminderRealms = Realm.getDefaultInstance()
                .where(ReminderRealm.class)
                .equalTo("active", true)
                .findAllSorted("id", Sort.DESCENDING);

        return reminderRealms;
    }
}
