package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.CustomerType;
import com.sipios.refactoring.model.Item;
import com.sipios.refactoring.model.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new ArrayList<>() {}, CustomerType.STANDARD_CUSTOMER))
        );
    }

    @Test
    void get_price_returns_the_correct_price() {
        // TODO: check how test would run in discount season
        Item item1 = new Item(ItemType.DRESS, 2);
        Item item2 = new Item(ItemType.TSHIRT, 1);
        List<Item> items = List.of(item1, item2);
        Body body_standard = new Body(items, CustomerType.STANDARD_CUSTOMER);
        Body body_premium = new Body(items, CustomerType.PREMIUM_CUSTOMER);
        Body body_platinum = new Body(items, CustomerType.PLATINUM_CUSTOMER);

        String price_standard = controller.getPrice(body_standard);
        String price_premium = controller.getPrice(body_premium);
        String price_platinum = controller.getPrice(body_platinum);

        assertEquals("130.0", price_standard);
        assertEquals("117.0", price_premium);
        assertEquals("65.0", price_platinum);
    }
}
