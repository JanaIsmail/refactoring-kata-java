package com.sipios.refactoring.controller;

import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.CustomerType;
import com.sipios.refactoring.model.Item;
import com.sipios.refactoring.model.ItemType;
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
    public String getPrice(@RequestBody Body b) {
        double p = 0;
        double d;

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        // Compute discount for customer
        if (CustomerType.STANDARD_CUSTOMER.equals(b.getType())) {
            d = 1;
        } else if (CustomerType.PREMIUM_CUSTOMER.equals(b.getType())) {
            d = 0.9;
        } else if (CustomerType.PLATINUM_CUSTOMER.equals(b.getType())) {
            d = 0.5;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (
            !(
                cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                    cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                    cal.get(Calendar.MONTH) == 5
            ) &&
                !(
                    cal.get(Calendar.DAY_OF_MONTH) < 15 &&
                        cal.get(Calendar.DAY_OF_MONTH) > 5 &&
                        cal.get(Calendar.MONTH) == 0
                )
        ) {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().size(); i++) {
                Item it = b.getItems().get(i);

                if (ItemType.TSHIRT.equals(it.getType())) {
                    p += 30 * it.getNb() * d;
                } else if (ItemType.DRESS.equals(it.getType())) {
                    p += 50 * it.getNb() * d;
                } else if (ItemType.JACKET.equals(it.getType())) {
                    p += 100 * it.getNb() * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                //     price += 80 * it.getNb();
                // }
            }
        } else {
            if (b.getItems() == null) {
                return "0";
            }

            for (int i = 0; i < b.getItems().size(); i++) {
                Item it = b.getItems().get(i);

                if (ItemType.TSHIRT.equals(it.getType())) {
                    p += 30 * it.getNb() * d;
                } else if (ItemType.DRESS.equals(it.getType())) {
                    p += 50 * it.getNb() * 0.8 * d;
                } else if (ItemType.JACKET.equals(it.getType())) {
                    p += 100 * it.getNb() * 0.9 * d;
                }
                // else if (ItemType.SWEATSHIRT.equals(it.getType())) {
                //     price += 80 * it.getNb();
                // }
            }
        }

        try {
            if (CustomerType.STANDARD_CUSTOMER.equals(b.getType())) {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            } else if (CustomerType.PREMIUM_CUSTOMER.equals(b.getType())) {
                if (p > 800) {
                    throw new Exception("Price (" + p + ") is too high for premium customer");
                }
            } else if (CustomerType.PLATINUM_CUSTOMER.equals(b.getType())) {
                if (p > 2000) {
                    throw new Exception("Price (" + p + ") is too high for platinum customer");
                }
            } else {
                if (p > 200) {
                    throw new Exception("Price (" + p + ") is too high for standard customer");
                }
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }
}

