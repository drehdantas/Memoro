package com.project.andredantas.memoro.model.dao;

import com.project.andredantas.memoro.model.ColorRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by Andre Dantas on 5/3/17.
 */

public class ColorDAO {
    public static ColorRealm getById(long colorId){
        return Realm.getDefaultInstance().where(ColorRealm.class).equalTo("colorNumber", colorId).findFirst();
    }

    public static List<ColorRealm> listColors(){
        List<ColorRealm> colors = Realm.getDefaultInstance()
                .where(ColorRealm.class)
                .findAllSorted("colorNumber", Sort.ASCENDING);

        return colors;
    }

    public static void saveColors(List<ColorRealm> colors){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < colors.size(); i++) {
            realm.copyToRealm(colors.get(i));
        }
        realm.commitTransaction();
        realm.close();
    }
}
