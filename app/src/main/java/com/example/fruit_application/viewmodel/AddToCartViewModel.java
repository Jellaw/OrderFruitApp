package com.example.fruit_application.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.CartDBHelper;
import com.example.fruit_application.model.Fruit;

public class AddToCartViewModel extends ViewModel {
    private int idFruit = -1;
    private CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    public MutableLiveData<String> nameFruit = new MutableLiveData<>();
    public MutableLiveData<String> priceFruit = new MutableLiveData<>();
    public MutableLiveData<String> quantity = new MutableLiveData<>();
    public MutableLiveData<Fruit> fruitMutableLiveData = new MutableLiveData<>();;

    public MutableLiveData<Fruit> getFruitData(){
        if (fruitMutableLiveData== null){
            fruitMutableLiveData = new MutableLiveData<>();
        }
        return fruitMutableLiveData;
    }
    public void setIdFruit(int id){
        this.idFruit=id;
    }
    public void setQuantity(String quantity){
        this.quantity.setValue(quantity);
    }

    public MutableLiveData<String> getQuantity() {
        return quantity;
    }
    public void setNameFruit(String nameFruit){
        this.nameFruit.setValue(nameFruit);
    }
    public void setPrice(String price){
        this.priceFruit.setValue(price);
    }
    public void onClickAddCart(View view, String idUser){
        Fruit fruit = new Fruit(idFruit, 1,nameFruit.getValue(),priceFruit.getValue());
        fruitMutableLiveData.setValue(fruit);
        saveCart(idUser);
    }
    public void saveCart(String idUser){
        cartDBHelper.createCart(fruitMutableLiveData.getValue(), idUser, Integer.parseInt(quantity.getValue()), new IRealmResponse<Boolean, Boolean>() {

            @Override
            public Boolean executeService(Boolean args) {
                return null;
            }
        });
    }
}
