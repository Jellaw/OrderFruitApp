package com.example.fruit_application.Class;

import android.app.Application;

import com.example.fruit_application.database.DBHelper;

import io.realm.Realm;

public class ApplicationDelegate extends Application {

    public ApplicationDelegate() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        DBHelper.getInstance().initRealm();
    }
}
