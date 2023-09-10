package com.techelevator.view;

import com.techelevator.Model.Beverages;
import com.techelevator.Model.Candy;
import com.techelevator.Model.VendingItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BeveragesTest {
    VendingItem sut;

    @Before
    public void setUp(){
        sut = new Beverages("B3", "Mountain Melter", new BigDecimal(3.55), "Drink");
    }

    @Test
    public void test_for_constructor(){
        int result = sut.getQuantity();
        String slotNumber = "B3";
        String productName = "Mountain Melter";
        BigDecimal price = new BigDecimal(3.55);
        String productType = "Drink";

        Assert.assertEquals(5, result);
        Assert.assertEquals(slotNumber, sut.getSlotLocation());
        Assert.assertEquals(productName, sut.getProductName());
        Assert.assertEquals(price, sut.getPrice());
        Assert.assertEquals(productType, sut.getType());

    }

    @Test
    public void test_for_extra_getters(){
        Assert.assertEquals(0, sut.getBOGODOSale());
        Assert.assertEquals(0, sut.getTRegularSale());
    }

    @Test
    public void test_for_reduce_and_add(){
        sut.reduceQuantity();
        sut.addRegularSale();
        sut.addBOGODOSale();
        Assert.assertEquals(1, sut.getTRegularSale());
        Assert.assertEquals(1, sut.getBOGODOSale());
        Assert.assertEquals(4, sut.getQuantity());
    }
}
