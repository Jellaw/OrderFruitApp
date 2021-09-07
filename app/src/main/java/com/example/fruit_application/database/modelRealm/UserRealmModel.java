package com.example.fruit_application.database.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserRealmModel extends RealmObject {
    @PrimaryKey
    String uID;
    String fullName;
    String address;
    String email;
    String password;
    String town;

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
