package com.example.fruit_application.Model;

public class Cart {
    private String namefruit;
    private int price;
    private int weight;
    private String idCart;

    public Cart() {
    }

    public String getNamefruit() {
        return namefruit;
    }

    public void setNamefruit(String namefruit) {
        this.namefruit = namefruit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }
}
