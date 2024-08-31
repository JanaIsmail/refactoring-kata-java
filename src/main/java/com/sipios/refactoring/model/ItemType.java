package com.sipios.refactoring.model;

public enum ItemType {
    TSHIRT(30.0, 1.0),
    DRESS(50.0, 0.8),
    JACKET(100.0, 0.9),
    SWEATSHIRT(80.0, 1.0);

    private final double price;
    private final double seasonDiscount;

    ItemType(double price, double seasonDiscount) {
        this.price = price;
        this.seasonDiscount = seasonDiscount;
    }

    public double getPrice() {
        return price;
    }

    public double getSeasonDiscount() {
        return seasonDiscount;
    }
}
