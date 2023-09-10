package com.techelevator.Controller;

import java.math.BigDecimal;

public class Money {
    private BigDecimal currentMoney = new BigDecimal("0");

    public BigDecimal getCurrentMoney() {
        return currentMoney;
    }

    public void moneyIn(String amountInString){
        currentMoney = currentMoney.add(new BigDecimal(amountInString));
    }

    public void updateCurrentMoney(BigDecimal amount){
        this.currentMoney = amount;
    }

    /**
     * convert money into collection of coins in their largest combination (least amount of coins),
     * print out the specific prompt message for the coin combination.
     * @param amount the amount of money for converting
     * @return void
     */
    public static void convertMoneyToCoins(double amount){
        int quarter = 25;
        int dime = 10;
        int nickel = 5;
        int penny = 1;

        int quarterAmount = (int) (amount * 100 / quarter);
        double remainderFromQuarter = amount * 100 % quarter;

        int dimeAmount = (int) (remainderFromQuarter / dime);
        double remainderFromDime = remainderFromQuarter % dime;

        int nickelAmount = (int) (remainderFromDime / nickel);
        double remainderFromNickel = remainderFromDime % nickel;

        int pennyAmount = (int) (remainderFromNickel / penny);

        System.out.println("Your change in coins: " + quarterAmount + " quarter(s), " + dimeAmount + " dime(s), " + nickelAmount + " nickel(s), " + pennyAmount + " penny(ies).");
    }

}
