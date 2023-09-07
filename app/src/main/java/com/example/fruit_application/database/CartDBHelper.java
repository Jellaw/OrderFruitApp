package com.example.fruit_application.database;

import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.Model.Fruit;
import com.example.fruit_application.database.modelRealm.CartRealm;

import java.util.List;

import io.realm.Realm;

public class CartDBHelper {
    private static CartDBHelper instance = null;

    public void CartDBHelper(){

    }

    public static CartDBHelper getInstance(){
        if (instance == null){
            instance = new CartDBHelper();
        }
        return instance;
    }

    public void creatCart(Fruit fruit, String idUser , int quantity , RealmResponse<Boolean, Boolean> callBack){
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try{
                    Number currenId = realm.where(CartRealm.class).max("idCart");
                    int nextid;

                    if (currenId== null){
                        nextid =1;
                    }else {
                        nextid = currenId.intValue() +1;
                    }
                    CartRealm cartRealm = new CartRealm();
                    cartRealm.setIdCart(nextid);
                    cartRealm.setIdUs(idUser);
                    cartRealm.setIdFruit(fruit.getIdFruit());
                    cartRealm.setNamefruit(fruit.getName());
                    cartRealm.setImgFruit(fruit.getIgm());
                    cartRealm.setQuanlity(quantity);
                    cartRealm.setPriceFruit(fruit.getPrice());
                    realm.insert(cartRealm);
                    callBack.executeService(true);
                }catch (Exception e){
                    callBack.executeService(false);
                }
            }
        });
    }

    public void getListCartByUser(String idUser, RealmResponse<Boolean, List<CartRealm>> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<CartRealm> list = realm.where(CartRealm.class).equalTo("idUser", idUser).findAll();
                callBack.executeService(list);
            }
        });
    }

    public void getCartById(String idUser,int idCart, RealmResponse<Boolean,CartRealm> callBack){
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealm cartRealm = realm.where(CartRealm.class).equalTo("idUser", idUser)
                        .equalTo("idCart",idCart ).findFirst();
                callBack.executeService(cartRealm);
            }
        });
    }

    public void updateQuantityCart(Cart cart, String idUser, int positionAdapter, int quantity, RealmResponse<Boolean, Boolean> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealm cartRealm = realm.where(CartRealm.class).equalTo("idUser", idUser)
                        .equalTo("idCart", cart.getIdCart())
                        .findFirst();
                cartRealm.setPriceFruit("$ "+(Float.parseFloat(cart.getPriceFruit().substring(2))/cart.getQuanlity())*quantity);
                cartRealm.setQuanlity(quantity);
                callBack.executeService(true);
            }
        });
    }

    public void deleteCartItem(Cart cart, String idUser, RealmResponse<Boolean, Boolean> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealm cartRealm = realm.where(CartRealm.class).equalTo("idUser", idUser)
                        .equalTo("idCart", cart.getIdCart())
                        .findFirst();
                if (cartRealm!=null){
                    cartRealm.deleteFromRealm();
                }
                callBack.executeService(true);
            }
        });
    }
    public void deleteCartOnTop(int idCart, String idUser, RealmResponse<Boolean, Boolean> callBack) {
        DBHelper.getInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartRealm cartRealm = realm.where(CartRealm.class).equalTo("idUser", idUser)
                        .equalTo("idCart", idCart)
                        .findFirst();
                if (cartRealm!=null){
                    cartRealm.deleteFromRealm();
                }
                callBack.executeService(true);
            }
        });
    }
}