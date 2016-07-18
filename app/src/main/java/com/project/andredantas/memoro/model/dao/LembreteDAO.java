package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Andre Dantas on 7/17/16.
 */
public class LembreteDAO {

    public static void saveLembrete(Realm realm, Lembrete lembrete) {
        realm.beginTransaction();
        realm.copyToRealm(lembrete);
        realm.commitTransaction();
        realm.close();
    }

    public static void updateLembrete(Realm realm, Lembrete lembrete) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(lembrete);
        realm.commitTransaction();
        realm.close();
    }

    public static void deleteLembrete(Realm realm, Lembrete lembrete) {
        realm.beginTransaction();
        lembrete.setActive(false);
        realm.copyToRealmOrUpdate(lembrete);
        realm.commitTransaction();
        realm.close();
    }

    public static Lembrete getById(long lembreteId){
        return Realm.getDefaultInstance().where(Lembrete.class).equalTo("id", lembreteId).findFirst();
    }
    public static List<Horario> listLembretes(){
        return Realm.getDefaultInstance()
                .where(Horario.class)
                .equalTo("active", true)
                .findAll();
    }

}
