package com.techelevator.Controller;
import com.techelevator.Model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class ItemInventory {
    private static List<VendingItem> vendingItemList = new ArrayList<>();
    public static List<VendingItem> getVendingItemList() {
        return vendingItemList;
    }

    /**
     * read datafile and load information into vendingItemList, returns void.
     *@param dataFile the datafile to obtain information
     * @return void
     */
    public void loadFileToInventory(File dataFile){
        try(Scanner fileInput = new Scanner(dataFile)){
            while(fileInput.hasNextLine()){
                String currentLine = fileInput.nextLine();
                String[] itemInfo = currentLine.split(",");

                BigDecimal bigDecimalPrice = new BigDecimal(itemInfo[2]);
                VendingItem currentSnack;
                if(itemInfo[3].equals("Gum")){
                    currentSnack = new Gum(itemInfo[0], itemInfo[1], bigDecimalPrice, itemInfo[3]);
                } else if(itemInfo[3].equals("Candy")){
                    currentSnack = new Candy(itemInfo[0], itemInfo[1], bigDecimalPrice, itemInfo[3]);
                } else if(itemInfo[3].equals("Munchy")){
                    currentSnack = new Munchy(itemInfo[0], itemInfo[1], bigDecimalPrice, itemInfo[3]);
                } else {
                    currentSnack = new Beverages(itemInfo[0], itemInfo[1], bigDecimalPrice, itemInfo[3]);
                }
                vendingItemList.add(currentSnack);
            }
        } catch (FileNotFoundException e){
            System.out.println("The file was not found: " + dataFile.getAbsolutePath());
        }
    }

    /**
     * Retrieve and print out items on vendingItemList, includes slot location, product name, price, quantity.
     * @return void
     */
    public void getItemListDetail(){
        for(VendingItem item: vendingItemList){
            if(item.getQuantity() > 0){
                System.out.println(item.getSlotLocation() + " " + item.getProductName() + ": price $" + item.getPrice() + " (Item Inventory: " + item.getQuantity() + ")");
            } else {
                System.out.println("This item is out of stock! " + item.getSlotLocation() + " " + item.getProductName() + ": price $" + item.getPrice());
            }
        }
    }

    /**
     * Retrieve and list items by number order on vendingItemList, includes name and price.
     * @return void
     */
    public void getProductInformation(){
        int count = 1;
        for(VendingItem item: this.vendingItemList){
            item.setItemCode(count);
            System.out.println(count + ": " + item.getProductName() + ": price $" + item.getPrice());
            count++;
        }
    }

    /**
     * make purchase according to item number and current wallet balance.
     * @param stringOfNumber the unique item number of a vending item
     * @param currentMoney the current wallet balance of user
     * @return current wallet balance whether the purchase was able to be made in BigDecimal
     */
    public BigDecimal selectAndPurchase(String stringOfNumber, BigDecimal currentMoney){
        BigDecimal moneyLeft = currentMoney;
        if(stringOfNumber==null){
            System.out.println("You have to make an item selection!");
        } else {
            try{
                int number = Integer.parseInt(stringOfNumber);
                for(VendingItem item: this.vendingItemList){
                    if(item.getItemCode() == number){
                        if(item.getPrice().compareTo(currentMoney) <= 0){
                            moneyLeft = moneyLeft.subtract(item.getPrice());
                            item.reduceQuantity();;
                            item.deliverMessage();
                        } else {
                            System.out.println("Insufficient funds. Please recharge.");
                        }
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid item selection, please try again.");
            }
        }
        return moneyLeft;
    }

    /**
     * Retrieve item by the given item code.
     * @param code the item code of the item to retrieve
     * @return the specific item if exists, otherwise return null
     */
    public String getItemByItemCode(int code){
        for(VendingItem item: this.vendingItemList){
            if(item.getItemCode() == code){
                return item.getProductName();
            }
        }
        return null;
    }

    /**
     * Retrieve item price by the given item code.
     * @param code the item code of the item to retrieve
     * @return the specific item price if exists, otherwise return null
     */
    public BigDecimal getItemPriceByItemCode(int code){
        for(VendingItem item: vendingItemList){
            if(item.getItemCode() == code){
                return item.getPrice();
            }
        }
        return null;
    }

    /**
     * Retrieve item slot number by the given item code.
     * @param code the item code of the item to retrieve
     * @return the specific item slot number if exists, otherwise return null
     */
    public String getItemSlotNumberByItemCode(int code){
        for(VendingItem item: vendingItemList){
            if(item.getItemCode() == code){
                return item.getSlotLocation();
            }
        }
        return null;
    }

}
