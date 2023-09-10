package com.techelevator.Model;

import java.math.BigDecimal;

public abstract class VendingItem {
    private String productName;
    private String slotLocation;
    private BigDecimal price;
    private String type;
    private int quantity;
    private int itemCode;

    private int regularSale = 0;
    private int BOGODOSale = 0;

    public int getTRegularSale() {
        return regularSale;
    }

    public void addRegularSale() {
        this.regularSale++;
    }

    public int getBOGODOSale() {
        return BOGODOSale;
    }

    public void addBOGODOSale() {
        this.BOGODOSale++;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(){
        if(quantity > 0){
            quantity--;
        }
    }

    public String getProductName() {
        return productName;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public VendingItem(String slotLocation, String productName, BigDecimal price, String type) {
        this.slotLocation = slotLocation;
        this.productName = productName;
        this.price = price;
        this.type = type;
        this.quantity = 5;
    }

    public abstract void deliverMessage();

}
