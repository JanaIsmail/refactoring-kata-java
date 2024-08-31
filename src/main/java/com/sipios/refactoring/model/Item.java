package com.sipios.refactoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private ItemType type;
    private int nb;

    public double getTotalPrice(boolean isDiscountPeriod) {
        return isDiscountPeriod ? nb * type.getPrice() * type.getSeasonDiscount() : nb * type.getPrice();
    }
}
