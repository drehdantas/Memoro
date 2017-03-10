package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.ScheduleNormal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class ScheduleDAO {

    public static void saveSchedule(Realm realm, Schedule schedule) {
        realm.beginTransaction();
        realm.copyToRealm(schedule);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateSchedule(Realm realm, Schedule schedule) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(schedule);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteSchedule(Realm realm, Schedule schedule) {
        realm.beginTransaction();
        schedule.setActive(false);
        realm.copyToRealmOrUpdate(schedule);
        realm.commitTransaction();
        realm.close();
    }

    public static Schedule getById(long scheduleId){
        return Realm.getDefaultInstance().where(Schedule.class).equalTo("id", scheduleId).findFirst();
    }
    public static List<Schedule> listSchedules(String day){
        List<Schedule> schedules = Realm.getDefaultInstance()
                .where(Schedule.class)
                .equalTo("day", day)
                .equalTo("active", true)
                .findAll();
        return schedulesOrders(schedules);
    }

    public static List<Schedule> listSchedules(){
        return Realm.getDefaultInstance()
                .where(Schedule.class)
                .equalTo("active", true)
                .findAll();
    }

    public static List<Schedule> schedulesOrders(List<Schedule> schedules){
        List<ScheduleNormal> scheduleNormal = ScheduleNormal.convertFromRealm(schedules);
        Collections.sort(scheduleNormal, new Comparator<ScheduleNormal>() {
            @Override
            public int compare(ScheduleNormal obj1, ScheduleNormal obj2) {
                long value1 = (obj1.getHour() * 60) + obj1.getMinutes();
                long value2 = (obj2.getHour() * 60) + obj2.getMinutes();

                return value1 < value2 ? -1
                        : value1 > value2 ? 1
                        : 0;
            }
        });

        List<Schedule> schedulesOrders = new ArrayList<>();
        for (int i = 0; i < scheduleNormal.size(); i++) {
            schedulesOrders.add(getById(scheduleNormal.get(i).getId()));
        }
        return schedulesOrders;
    }

}
