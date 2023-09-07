package com.example.fruit_application.database;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DBHelper {
    private Realm backgroundThread = null;
    private static  DBHelper instance = null;

    public void DBHelper(){

    };

    public static DBHelper getInstance(){
        if (instance == null){
            instance = new DBHelper();
        }
        return instance;
    }
    public void initRealm(){
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("FRUIT APP").build();
        backgroundThread = Realm.getInstance(configuration);
    }
    public Realm getRealm(){
        return backgroundThread;
    }
}
