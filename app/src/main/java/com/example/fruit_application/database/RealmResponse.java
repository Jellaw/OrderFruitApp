package com.example.fruit_application.database;

public interface RealmResponse<T, U> {
    T executeService(U args);
}
