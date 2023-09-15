package com.example.fruit_application.database;

import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.database.modelRealm.OrderRealm;

import java.util.List;

import io.realm.Realm;

public class OrderDBHelper {
    private  static OrderDBHelper instance = null;

    public OrderDBHelper() {
    }

    public static OrderDBHelper getInstance() {
        if(instance == null) {
            instance = new OrderDBHelper();
        }
        return instance;
    }

    public void createOrder(Cart cart, String delivery, Boolean isShipped, RealmResponse<Boolean, Boolean> callBack){
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // increment index
                try{
                    Number currentIdNum = realm.where(OrderRealm.class).max("idOrder");
                    int nextId;
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    OrderRealm orderRealmModel= new OrderRealm();
                    orderRealmModel.setIdOrder(nextId);
                    orderRealmModel.setuID(cart.getIdUs());
                    orderRealmModel.setDelivery(delivery);
                    orderRealmModel.setIdFruit(cart.getIdFruit());
                    orderRealmModel.setNameFruit(cart.getNamefruit());
                    orderRealmModel.setQuantity(String.valueOf(cart.getQuanlity()));
                    orderRealmModel.setTotalPrice(cart.getPriceFruit());
                    orderRealmModel.setShipped(isShipped);
                    realm.insertOrUpdate(orderRealmModel);
                    callBack.executeService(true);
                }catch (Exception ex) {
                    callBack.executeService(false);
                }
            }
        });
    }


    public void getOrderByUser(String idUser, RealmResponse<Boolean, List<OrderRealm>> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<OrderRealm> orderRealmModelList = realm.where(OrderRealm.class).equalTo("uID", idUser).findAll();
                callBack.executeService(orderRealmModelList);
            }
        });
    }
}
