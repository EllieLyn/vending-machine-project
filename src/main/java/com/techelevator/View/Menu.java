package com.techelevator.View;
import com.techelevator.Controller.ItemInventory;

import java.io.File;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Retrieve user input and convert into integer and handle exceptions
     * @return the user input in integer
     */
    public int promptForMenuSelection() {
        int menuSelection;
        System.out.println();
        System.out.print("Please choose an option: ");
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    /**
     * Retrieve and print out welcome banner and main menu according to the input array of strings
     * @param menu the array of strings
     * @return void
     */
    public void printMainMenu(String[] menu) {
        System.out.println();
        System.out.println("----Star Vending Machine Purchase Menu----");
        for(String menuOption: menu){
            System.out.println(menuOption);
        }
    }

    /**
     * Retrieve and print out purchase menu according to the input array of strings
     * @param menu the array of strings
     * @return void
     */
    public void printPurchaseMenu(String[] menu) {
        System.out.println();
        for(String menuOption: menu){
            System.out.println(menuOption);
        }
        System.out.println();
    }

}
