package com.techelevator.Model;

import java.math.BigDecimal;
public class Beverages extends VendingItem{
    public Beverages(String slotLocation, String productName, BigDecimal price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void deliverMessage() {
        System.out.println("Glug Glug, Yum!");
    }
}
