package com.example.fruit_application.database.helper;

import com.example.fruit_application.database.DbHelper;
import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.modelRealm.CartRealmModel;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.model.Fruit;

import java.util.List;

import io.realm.Realm;

public class CartDBHelper {
    private static CartDBHelper instance = null;

    private void CartDBHelper() {

    }

    public static CartDBHelper getInstance() {
        if(instance == null) {
            instance = new CartDBHelper();
        }
        return instance;
    }

    public void createCart(Fruit fruit, String idUser, int quantity, IRealmResponse<Boolean, Boolean> callBack){
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // increment index
                try{
                        Number currentIdNum = realm.where(CartRealmModel.class).max("cartID");
                        int nextId;
                        if(currentIdNum == null) {
                            nextId = 1;
                        } else {
                            nextId = currentIdNum.intValue() + 1;
                        }
                        CartRealmModel cartRealm= new CartRealmModel();
                        cartRealm.setCartID(nextId);
                        cartRealm.setuID(idUser);
                        cartRealm.setIdFruit(fruit.getId());
                        cartRealm.setNameFruit(fruit.getName());
                        cartRealm.setQuantity(quantity);
                        cartRealm.setPrice(fruit.getPrice());
                        realm.insertOrUpdate(cartRealm);
                        callBack.executeService(true);
                }catch (Exception ex) {
                    callBack.executeService(false);
                }
            }
        });
    }


    public void getListCartByUser(String idUser, IRealmResponse<Boolean, List<CartRealmModel>> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<CartRealmModel> list = realm.where(CartRealmModel.class).equalTo("uID", idUser).findAll();
                callBack.executeService(list);
            }
        });
    }

    public void getCartById(String idUser,int cartID, IRealmResponse<Boolean,CartRealmModel> callBack){
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealmModel cartRealmModel = realm.where(CartRealmModel.class).equalTo("uID", idUser)
                        .equalTo("cartID",cartID ).findFirst();
                callBack.executeService(cartRealmModel);
            }
        });
    }

    public void updateQuantityCart(Cart cart, String idUser, int positionAdapter, int quantity, IRealmResponse<Boolean, Boolean> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealmModel cartRealmModel = realm.where(CartRealmModel.class).equalTo("uID", idUser)
                        .equalTo("cartID", cart.getCartID())
                        .findFirst();
                cartRealmModel.setPrice("$ "+(Float.parseFloat(cart.getPrice().substring(2))/cart.getQuantity())*quantity);
                cartRealmModel.setQuantity(quantity);
                callBack.executeService(true);
            }
        });
    }

    public void deleteCartItem(Cart cart, String idUser, IRealmResponse<Boolean, Boolean> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealmModel cartRealmModel = realm.where(CartRealmModel.class).equalTo("uID", idUser)
                        .equalTo("cartID", cart.getCartID())
                        .findFirst();
                if (cartRealmModel!=null){
                    cartRealmModel.deleteFromRealm();
                }
                callBack.executeService(true);
            }
        });
    }
    public void deleteCartOnTop(int cartID, String idUser, IRealmResponse<Boolean, Boolean> callBack) {
        DbHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealmModel cartRealmModel = realm.where(CartRealmModel.class).equalTo("uID", idUser)
                        .equalTo("cartID", cartID)
                        .findFirst();
                if (cartRealmModel!=null){
                    cartRealmModel.deleteFromRealm();
                }
                callBack.executeService(true);
            }
        });
    }
}
