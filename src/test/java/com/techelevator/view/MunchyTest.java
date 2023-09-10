package com.techelevator.view;

import com.techelevator.Model.Candy;
import com.techelevator.Model.Munchy;
import com.techelevator.Model.VendingItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class MunchyTest {
    VendingItem sut;

    @Before
    public void setUp(){
        sut = new Munchy("A4", "Chippos", new BigDecimal(3.85), "Munchy");
    }

    @Test
    public void test_for_constructor(){
        int result = sut.getQuantity();
        String slotNumber = "A4";
        String productName = "Chippos";
        BigDecimal price = new BigDecimal(3.85);
        String productType = "Munchy";

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
