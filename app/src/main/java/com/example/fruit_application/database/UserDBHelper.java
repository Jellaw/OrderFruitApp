package com.example.fruit_application.database;

import com.example.fruit_application.Model.Fruit;
import com.example.fruit_application.database.modelRealm.CartRealm;
import com.example.fruit_application.database.modelRealm.UserRealm;

import io.realm.Realm;

public class UserDBHelper {
    private static UserDBHelper instance = null;

    public void CartDBHelper(){

    }

    public static UserDBHelper getInstance(){
        if (instance == null){
            instance = new UserDBHelper();
        }
        return instance;
    }

    public void creatUser( String idUser , String fullname, String email, String password , String street , String town  , RealmResponse<Boolean, Boolean> callBack){
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try{

                    UserRealm userRealm = new UserRealm();
                    userRealm.setIdUser(idUser);
                    userRealm.setFullname(fullname);
                    userRealm.setEmail(email);
                    userRealm.setPassword(password);
                    userRealm.setStreet(street);
                    userRealm.setTown(town);
                    realm.insertOrUpdate(userRealm);
                    callBack.executeService(true);
                }catch (Exception e){
                    callBack.executeService(false);
                }
            }
        });
    }
    public void getUser(String idUser, RealmResponse<Boolean, UserRealm> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm userRealm= realm.where(UserRealm.class).equalTo("idUser", idUser).findFirst();
                callBack.executeService(userRealm);
            }
        });
    }
}
