package com.example.fruit_application.application;

import android.app.Application;

import com.example.fruit_application.database.DbHelper;

import io.realm.Realm;

public class ApplicationDelegate extends Application {
    public ApplicationDelegate() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        DbHelper.getInstance().initRealm();

    }
}
