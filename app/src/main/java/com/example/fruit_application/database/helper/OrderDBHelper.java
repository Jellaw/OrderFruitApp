package com.example.fruit_application.database.helper;

import com.example.fruit_application.database.DbHelper;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.modelRealm.OrderRealmModel;
import com.example.fruit_application.model.Cart;

import java.util.List;

import io.realm.Realm;

public class OrderDBHelper {
    private static OrderDBHelper instance = null;

    private void OrderDBHelper() {

    }

    public static OrderDBHelper getInstance() {
        if(instance == null) {
            instance = new OrderDBHelper();
        }
        return instance;
    }

    public void createOrder(Cart cart, String delivery, Boolean isShipped, IRealmResponse<Boolean, Boolean> callBack){
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // increment index
                try{
                        Number currentIdNum = realm.where(OrderRealmModel.class).max("idOrder");
                        int nextId;
                        if(currentIdNum == null) {
                            nextId = 1;
                        } else {
                            nextId = currentIdNum.intValue() + 1;
                        }
                    OrderRealmModel orderRealmModel= new OrderRealmModel();
                    orderRealmModel.setIdOrder(nextId);
                    orderRealmModel.setuID(cart.getuID());
                    orderRealmModel.setDelivery(delivery);
                    orderRealmModel.setIdFruit(cart.getIdFruit());
                    orderRealmModel.setNameFruit(cart.getNameFruit());
                    orderRealmModel.setQuantity(String.valueOf(cart.getQuantity()));
                    orderRealmModel.setTotalPrice(cart.getPrice());
                    orderRealmModel.setShipped(isShipped);
                    realm.insertOrUpdate(orderRealmModel);
                        callBack.executeService(true);
                }catch (Exception ex) {
                    callBack.executeService(false);
                }
            }
        });
    }


    public void getOrderByUser(String idUser, IRealmResponse<Boolean, List<OrderRealmModel>> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<OrderRealmModel> orderRealmModelList = realm.where(OrderRealmModel.class).equalTo("uID", idUser).findAll();
                callBack.executeService(orderRealmModelList);
            }
        });
    }
}
