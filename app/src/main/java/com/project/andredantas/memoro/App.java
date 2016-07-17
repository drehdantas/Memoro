package com.project.andredantas.memoro;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;

/**
 * Created by Andre Dantas on 7/15/16.
 */
public class App extends Application {
    public static App instance;
    private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        gson = new GsonBuilder().setDateFormat(dateFormat).create();

        setupGson();
    }

    /**
     * Recursividade quando faz um parse do Objeto que extende RealmObject e tenta enviar por parametro via bundle
     * <p/>
     * http://stackoverflow.com/questions/34735639/retrofit-realmlist-gson-stuck-in-a-loop-until-out-of-memory/34736978#34736978
     */
    public void setupGson() {
        gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
    }

    public static App getInstance() {
        return instance;
    }

    public static Gson gsonInstance() {
        return instance.gson;
    }
}

