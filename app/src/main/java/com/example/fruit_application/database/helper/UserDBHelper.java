package com.example.fruit_application.database.helper;

import com.example.fruit_application.database.DbHelper;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.modelRealm.UserRealmModel;

import io.realm.Realm;

public class UserDBHelper {
    private static UserDBHelper instance = null;

    private void UserDBHelper() {

    }

    public static UserDBHelper getInstance() {
        if(instance == null) {
            instance = new UserDBHelper();
        }
        return instance;
    }

    public void createUser(String idUser, String fullName, String email,String password,String address, String town, IRealmResponse<Boolean, Boolean> callBack){
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // increment index
                try{
                    UserRealmModel userRealmModel= new UserRealmModel();
                    userRealmModel.setFullName(fullName);
                    userRealmModel.setuID(idUser);
                    userRealmModel.setEmail(email);
                    userRealmModel.setPassword(password);
                    userRealmModel.setAddress(address);
                    userRealmModel.setTown(town);
                    realm.insertOrUpdate(userRealmModel);
                    callBack.executeService(true);
                }catch (Exception ex) {
                    callBack.executeService(false);
                }
            }
        });
    }


    public void getUser(String idUser, IRealmResponse<Boolean, UserRealmModel> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealmModel userRealmModel = realm.where(UserRealmModel.class).equalTo("uID", idUser).findFirst();
                callBack.executeService(userRealmModel);
            }
        });
    }

}
