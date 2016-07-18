package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.Horario;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class HorarioDAO {

    public static void saveHorario(Realm realm, Horario horario) {
        realm.beginTransaction();
        realm.copyToRealm(horario);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateHorario(Realm realm, Horario horario) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(horario);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteHorario(Realm realm, Horario horario) {
        realm.beginTransaction();
        horario.setActive(false);
        realm.copyToRealmOrUpdate(horario);
        realm.commitTransaction();
        realm.close();
    }

    public static Horario getById(long horarioId){
        return Realm.getDefaultInstance().where(Horario.class).equalTo("id", horarioId).findFirst();
    }
    public static List<Horario> listHorarios(String dia){
        List<Horario> horarios = Realm.getDefaultInstance()
                .where(Horario.class)
                .equalTo("dia", dia)
                .equalTo("active", true)
                .findAll();
        return horariosOrdenados(horarios);
    }

    public static List<Horario> listTodosHorarios(){
        List<Horario> horarios = Realm.getDefaultInstance()
                .where(Horario.class)
                .equalTo("active", true)
                .findAll();
        return horarios;
    }

    public static List<Horario> horariosOrdenados(List<Horario> horarios){

        return horarios;
    }

}
