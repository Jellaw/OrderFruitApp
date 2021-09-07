package com.example.fruit_application.database;

public interface IRealmResponse<T, U> {
    T executeService(U args);
}
