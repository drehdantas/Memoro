package com.project.andredantas.memoro.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 4/27/17.
 */

public class ColorRealm extends RealmObject{
    @PrimaryKey
    private long colorNumber;
    private String color;

    public ColorRealm() {
    }

    public ColorRealm(long colorNumber, String color) {
        this.colorNumber = colorNumber;
        this.color = color;
    }

    public long getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(long colorNumber) {
        this.colorNumber = colorNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
