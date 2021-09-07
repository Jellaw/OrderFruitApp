package com.example.fruit_application.database;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DbHelper {
    String realmName = "FRUIT APP";
    private Realm backgroundThreadRealm = null;
    private static DbHelper instance = null;
    private void DbHelper() {

    }

    public static DbHelper getInstance() {
        if(instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    public void initRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).build();
        backgroundThreadRealm = Realm.getInstance(config);
    }

    public Realm getRealm() {
        return backgroundThreadRealm;
    }

}
