package com.example.fruit_application.activities.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.fruit_application.Model.Cart;
import com.example.fruit_application.Model.Order;
import com.example.fruit_application.database.CartDBHelper;
import com.example.fruit_application.database.OrderDBHelper;
import com.example.fruit_application.database.RealmResponse;
import com.example.fruit_application.database.modelRealm.CartRealm;
import com.example.fruit_application.database.modelRealm.OrderRealm;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private OrderDBHelper orderDBHelper = OrderDBHelper.getInstance();
    private CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    List<Order> list = new ArrayList<>();
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void saveOrder(Cart cart, String delivery, Boolean isShipped){
        orderDBHelper.createOrder(cart, delivery, isShipped, new RealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                return null;
            }
        });
    }
    public List<Order> getDataOrder(String idUser){
        orderDBHelper.getOrderByUser(idUser, new RealmResponse<Boolean, List<OrderRealm>>() {
            @Override
            public Boolean executeService(List<OrderRealm> args) {
                if (args!=null){
                    for (OrderRealm orderRealmModel:args){
                        list.add(new Order(orderRealmModel.getIdOrder(),
                                orderRealmModel.getNameFruit(),
                                orderRealmModel.getQuantity(),
                                orderRealmModel.getTotalPrice(),
                                orderRealmModel.getDelivery(),
                                orderRealmModel.getShipped()));
                    }
                }
                return null;
            }
        });
        return list;
    }
    public void getCartData(int idCart, String idUser, RealmResponse<Boolean, CartRealm> callback){
        cartDBHelper.getCartById(idUser, idCart, callback);
    }
    public void deleteCartItem(int cartID, String idUser, RealmResponse<Boolean, Boolean> callback){
        cartDBHelper.deleteCartOnTop(cartID, idUser,callback);
    }}
