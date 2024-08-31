package com.sipios.refactoring.controller;

import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @PostMapping
    public String getPrice(@RequestBody Body body) {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.setTime(date);

        // get discount for customer
        double discount = body.getType().getDiscount();

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        boolean isDiscountPeriod = isDiscountPeriod(calendar);
        double price = body.getItems().stream().mapToDouble(item -> item.getTotalPrice(isDiscountPeriod) * discount).sum();

        try {
            if (CustomerType.STANDARD_CUSTOMER.equals(body.getType()) && price > 200) {
                throw new Exception("Price (" + price + ") is too high for standard customer");
            } else if (CustomerType.PREMIUM_CUSTOMER.equals(body.getType()) && price > 800) {
                throw new Exception("Price (" + price + ") is too high for premium customer");
            } else if (CustomerType.PLATINUM_CUSTOMER.equals(body.getType()) && price > 2000) {
                throw new Exception("Price (" + price + ") is too high for platinum customer");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(price);
    }

    private boolean isDiscountPeriod(Calendar calendar) {
        return (calendar.get(Calendar.MONTH) == 5 || calendar.get(Calendar.MONTH) == 0)&&
            calendar.get(Calendar.DAY_OF_MONTH) < 15 &&
            calendar.get(Calendar.DAY_OF_MONTH) > 5;
    }
}

