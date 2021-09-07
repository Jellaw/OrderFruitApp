package com.example.fruit_application.model;

public class Order {
    int idOrder;
    String nameFruit;
    String quantity;
    String totalPrice;
    String delivery;
    Boolean isShipped;
    Boolean isExpand = false;

    public Order(int idOrder, String nameFruit, String quantity, String totalPrice, String delivery, Boolean isShipped) {
        this.idOrder = idOrder;
        this.nameFruit = nameFruit;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.delivery = delivery;
        this.isShipped = isShipped;
    }

    public Boolean getExpand() {
        return isExpand;
    }

    public void setExpand(Boolean expand) {
        isExpand = expand;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getNameFruit() {
        return nameFruit;
    }

    public void setNameFruit(String nameFruit) {
        this.nameFruit = nameFruit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Boolean getShipped() {
        return isShipped;
    }

    public void setShipped(Boolean shipped) {
        isShipped = shipped;
    }
}
