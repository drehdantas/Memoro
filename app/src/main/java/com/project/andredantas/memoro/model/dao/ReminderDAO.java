package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.Reminder;

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

    public static void updateReminder(Realm realm, Reminder reminder) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(reminder);
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
