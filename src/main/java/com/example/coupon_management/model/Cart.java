package com.example.coupon_management.model;

import java.util.List;

public class Cart {
    private List<CartItem> items; // List of items in the cart
    private Double total; // Total price of the items in the cart

    // Getters and Setters
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
