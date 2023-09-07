package com.example.fruit_application.database.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartRealm extends RealmObject {
    @PrimaryKey
    private int idCart;
    private String idUs;
    private int idFruit;
    private String namefruit;
    private int imgFruit;
    private int quanlity;
    private String priceFruit;

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getIdUs() {
        return idUs;
    }

    public void setIdUs(String idUs) {
        this.idUs = idUs;
    }

    public int getIdFruit() {
        return idFruit;
    }

    public void setIdFruit(int idFruit) {
        this.idFruit = idFruit;
    }

    public String getNamefruit() {
        return namefruit;
    }

    public void setNamefruit(String namefruit) {
        this.namefruit = namefruit;
    }

    public int getImgFruit() {
        return imgFruit;
    }

    public void setImgFruit(int imgFruit) {
        this.imgFruit = imgFruit;
    }

    public int getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(int quanlity) {
        this.quanlity = quanlity;
    }

    public String getPriceFruit() {
        return priceFruit;
    }

    public void setPriceFruit(String priceFruit) {
        this.priceFruit = priceFruit;
    }
}
