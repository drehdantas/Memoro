package com.project.andredantas.memoro.model.dao;

import android.content.Context;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.ColorRealm;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.model.Schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class ScheduleDAO {
    private static Realm realm = Realm.getDefaultInstance();

    public static void saveSchedule(ScheduleRealm scheduleRealm) {
        realm.beginTransaction();
        realm.copyToRealm(scheduleRealm);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateSchedule(long id, String title, String descript, int hour, int minutes, String time, ColorRealm colorRealm) {
        realm.beginTransaction();
        ScheduleRealm scheduleRealm = getById(id);
        scheduleRealm.setTitle(title);
        scheduleRealm.setDescript(descript);
        scheduleRealm.setColorRealm(colorRealm);
        if (time != null && !time.equals("")){
            scheduleRealm.setHour(hour);
            scheduleRealm.setMinutes(minutes);
            scheduleRealm.setTime(time);
        }
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteSchedule(ScheduleRealm scheduleRealm) {
        realm.beginTransaction();
        scheduleRealm.setActive(false);
        realm.copyToRealmOrUpdate(scheduleRealm);
        realm.commitTransaction();
        realm.close();
    }

    public static ScheduleRealm getById(long scheduleId){
        return realm.where(ScheduleRealm.class).equalTo("id", scheduleId).findFirst();
    }
    public static List<ScheduleRealm> listSchedules(String day){
        List<ScheduleRealm> scheduleRealms = realm
                .where(ScheduleRealm.class)
                .equalTo("day", day)
                .equalTo("active", true)
                .findAll();
        return schedulesOrders(scheduleRealms);
    }

    public static List<ScheduleRealm> listSchedules(){
        return realm
                .where(ScheduleRealm.class)
                .equalTo("active", true)
                .findAll();
    }

    public static List<ScheduleRealm> schedulesOrders(List<ScheduleRealm> scheduleRealms){
        List<Schedule> schedule = Schedule.convertFromRealm(scheduleRealms);
        Collections.sort(schedule, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule obj1, Schedule obj2) {
                long value1 = (obj1.getHour() * 60) + obj1.getMinutes();
                long value2 = (obj2.getHour() * 60) + obj2.getMinutes();

                return value1 < value2 ? -1
                        : value1 > value2 ? 1
                        : 0;
            }
        });

        List<ScheduleRealm> schedulesOrders = new ArrayList<>();
        for (int i = 0; i < schedule.size(); i++) {
            schedulesOrders.add(getById(schedule.get(i).getId()));
        }
        return schedulesOrders;
    }

    public static void createScheduleNone(Context context) {
        if (ScheduleDAO.getById(1) == null){
            ScheduleRealm scheduleRealmNenhum = new ScheduleRealm();
            scheduleRealmNenhum.setId(1);
            scheduleRealmNenhum.setTitle(context.getString(R.string.none));
            saveSchedule(scheduleRealmNenhum);
        }
    }
}
