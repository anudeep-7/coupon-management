package com.example.coupon_management.model;

import java.util.List;

public class Conditions {
    private Double cartTotalMinimum;
    private List<String> applicableProducts;
    private List<String> buyProducts;
    private List<String> getProducts;
    private Integer buyQuantity;
    private Integer getQuantity;
    private Integer repetitionLimit;

    // Getters and Setters
    public Double getCartTotalMinimum() {
        return cartTotalMinimum;
    }

    public void setCartTotalMinimum(Double cartTotalMinimum) {
        this.cartTotalMinimum = cartTotalMinimum;
    }

    public List<String> getApplicableProducts() {
        return applicableProducts;
    }

    public void setApplicableProducts(List<String> applicableProducts) {
        this.applicableProducts = applicableProducts;
    }

    public List<String> getBuyProducts() {
        return buyProducts;
    }

    public void setBuyProducts(List<String> buyProducts) {
        this.buyProducts = buyProducts;
    }

    public List<String> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(List<String> getProducts) {
        this.getProducts = getProducts;
    }

    public Integer getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Integer buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Integer getGetQuantity() {
        return getQuantity;
    }

    public void setGetQuantity(Integer getQuantity) {
        this.getQuantity = getQuantity;
    }

    public Integer getRepetitionLimit() {
        return repetitionLimit;
    }

    public void setRepetitionLimit(Integer repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }
}
