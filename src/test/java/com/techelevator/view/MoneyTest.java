package com.techelevator.view;

import com.techelevator.Controller.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTest {
    Money sut;

    @Before
    public void setUp(){
        sut = new Money();
    }

    @Test
    public void getCurrentMoneyTest(){
        BigDecimal result = sut.getCurrentMoney();
        Assert.assertEquals(new BigDecimal("0"), result);
    }

    @Test
    public void moneyInTest(){
        sut.moneyIn("5");
        BigDecimal newBalance = sut.getCurrentMoney();
        Assert.assertEquals(new BigDecimal("5"), newBalance);
    }

    @Test
    public void updateCurrentMoneyTest(){
        sut.updateCurrentMoney(new BigDecimal("20"));
        BigDecimal newBalance = sut.getCurrentMoney();
        Assert.assertEquals(new BigDecimal("20"), newBalance);
    }

}
