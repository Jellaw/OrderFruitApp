package com.example.fruit_application.Model;

public class Fruit {
    private int idFruit;
    private String name;
    private String price;
    private int igm;

    public Fruit(int idFruit, String name, String price, int igm) {
        this.idFruit = idFruit;
        this.name = name;
        this.price = price;
        this.igm = igm;
    }

    public int getIdFruit() {
        return idFruit;
    }

    public void setIdFruit(int idFruit) {
        this.idFruit = idFruit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIgm() {
        return igm;
    }

    public void setIgm(int igm) {
        this.igm = igm;
    }
}