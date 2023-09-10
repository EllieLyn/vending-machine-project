package com.techelevator.Model;

import java.math.BigDecimal;

public class Gum extends VendingItem {
    public Gum(String slotLocation, String productName, BigDecimal price, String type) {
        super(slotLocation, productName, price, type);
    }

    @Override
    public void deliverMessage() {
        System.out.println("Chew Chew, Yum!");
    }
}
