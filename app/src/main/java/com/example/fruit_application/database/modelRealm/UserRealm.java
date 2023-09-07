package com.example.fruit_application.database.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealm extends RealmObject {
    @PrimaryKey
    private String idUser;
    private String fullname;
    private String email;
    private String password;
    private String street;
    private String town;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
