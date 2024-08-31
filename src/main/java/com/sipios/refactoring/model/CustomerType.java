package com.sipios.refactoring.model;

public enum CustomerType {
    STANDARD_CUSTOMER(1),
    PREMIUM_CUSTOMER(0.9),
    PLATINUM_CUSTOMER(0.5);

    private final double discount;

    CustomerType(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
