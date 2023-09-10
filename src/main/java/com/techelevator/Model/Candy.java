package com.techelevator.Model;

import java.math.BigDecimal;

public class Candy extends VendingItem{
    public Candy(String slotLocation, String productName, BigDecimal price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void deliverMessage() {
        System.out.println("Yummy Yummy, So Sweet!");
    }
}
