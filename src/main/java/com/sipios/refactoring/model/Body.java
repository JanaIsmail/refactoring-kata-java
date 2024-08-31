package com.sipios.refactoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Body {

    private List<Item> items = new ArrayList<>();
    private CustomerType type;
}
