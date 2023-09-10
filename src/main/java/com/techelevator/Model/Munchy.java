package com.techelevator.Model;

import java.math.BigDecimal;

public class Munchy extends VendingItem{
    public Munchy(String slotLocation, String productName, BigDecimal price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void deliverMessage() {
        System.out.println("Crunch Crunch, Yum!");
    }
}
