package com.example.fruit_application.model;

public class Cart {
    private int cartID;
    private String uID;
    private int idFruit;
    private int imgFruit;
    private String nameFruit;
    private int quantity;
    private String price;

    public Cart(int cartID, String uID, int idFruit, int imgFruit, String nameFruit, int quantity, String price) {
        this.cartID = cartID;
        this.uID = uID;
        this.idFruit = idFruit;
        this.imgFruit = imgFruit;
        this.nameFruit = nameFruit;
        this.quantity = quantity;
        this.price = price;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getIdFruit() {
        return idFruit;
    }

    public void setIdFruit(int idFruit) {
        this.idFruit = idFruit;
    }

    public int getImgFruit() {
        return imgFruit;
    }

    public void setImgFruit(int imgFruit) {
        this.imgFruit = imgFruit;
    }

    public String getNameFruit() {
        return nameFruit;
    }

    public void setNameFruit(String nameFruit) {
        this.nameFruit = nameFruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
