package com.techelevator;

import com.techelevator.Controller.ItemInventory;
import com.techelevator.Controller.Money;
import com.techelevator.Controller.TransactionLog;
import com.techelevator.Model.VendingItem;
import com.techelevator.View.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineCLI {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "1. Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "2. Purchase";
	private static final String EXIT_MESSAGE = "0. Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT_MESSAGE};
    private static final String CURRENT_MONEY_PROVIDED_MESSAGE = "Current Money Provided: ";
    private static final String FEED_MONEY_MESSAGE = "1. Feed Money";
    private static final String SELECT_PRODUCT_MESSAGE = "2. Select Product";
    private static final String FINISH_TRANSACTION_MESSAGE = "3. Finish Transaction";
	private static final String[] Money_MENU_OPTIONS = {FEED_MONEY_MESSAGE, SELECT_PRODUCT_MESSAGE, FINISH_TRANSACTION_MESSAGE, EXIT_MESSAGE};
	private static Menu menu;
	private ItemInventory Inventory = new ItemInventory();;

	// hidden menu Sales Report set up: option 4
	private List<VendingItem> salesReport = new ArrayList<>();
	private LocalDateTime today = LocalDateTime.now();
	private String currentTimeForSalesReport = today.getMonthValue() + "."
			+ today.getDayOfMonth() + "." + today.getYear() + "." + today.format(DateTimeFormatter.ofPattern("hh.mm.ssa"));
	private BigDecimal totalSalesForReport;
	private BigDecimal totalSales = new BigDecimal("0");
	private String currentTimeInFormat = today.getMonthValue() + "/" + today.getDayOfMonth() +
			"/" + today.getYear() + " " + today.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
	private NumberFormat currency = NumberFormat.getCurrencyInstance();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	public static void main(String[] args) {
		Menu menu = new Menu();
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public void run() {
		Inventory.loadFileToInventory(new File("alternate.csv"));
		TransactionLog currentLog = new TransactionLog();
		while (true) {
			Money myWallet = new Money();
			int itemSelection = 0, counter = 1;
			menu.printMainMenu(MAIN_MENU_OPTIONS);
			int choice = menu.promptForMenuSelection();
			if (choice==1) {
				Inventory.getItemListDetail();
			} else if (choice==2) {
				while(true){
					menu.printPurchaseMenu(Money_MENU_OPTIONS);
					System.out.println(CURRENT_MONEY_PROVIDED_MESSAGE + currency.format(myWallet.getCurrentMoney()));
					int moneyChoice = menu.promptForMenuSelection();
					if(moneyChoice==1){
						System.out.println("Please enter dollar bill in 1 or 5.");
						int moneyEntered = menu.promptForMenuSelection();
						if(moneyEntered==1){
							myWallet.moneyIn("1");
							// log money feed info to transactionLog
							String LogInformation = currentTimeInFormat + " FEED MONEY: $1.00 " + currency.format(myWallet.getCurrentMoney());
							currentLog.writingLog(LogInformation);
						} else if(moneyEntered==5){
							myWallet.moneyIn("5");
							// log money feed info to transactionLog
							String LogInformation = currentTimeInFormat + " FEED MONEY: $5.00 " + currency.format(myWallet.getCurrentMoney());
							currentLog.writingLog(LogInformation);
						} else {
							System.out.println("Please enter a valid money amount.");
							System.out.println();
						}
					} else if(moneyChoice==2){
						Inventory.getProductInformation();
						itemSelection = menu.promptForMenuSelection();
						boolean foundMatch = false;
						for (int i = 1; i <= Inventory.getVendingItemList().size(); i++){
							if(itemSelection == i){
								foundMatch = true;
								for(VendingItem item: Inventory.getVendingItemList()){
									if(item.getItemCode()==itemSelection && item.getQuantity()>0){
										System.out.println("You selected: item #" + itemSelection +": " + Inventory.getItemByItemCode(itemSelection) + " $" + Inventory.getItemPriceByItemCode(itemSelection));
										// handle 2nd purchase receive a discount of $1
										if(counter==2){
											BigDecimal remainingBalance = Inventory.selectAndPurchase(Integer.toString(itemSelection), myWallet.getCurrentMoney().add(new BigDecimal("1")));
											if(remainingBalance.compareTo(myWallet.getCurrentMoney().add(new BigDecimal("1"))) != 0){
												myWallet.updateCurrentMoney(remainingBalance);
												System.out.println(Inventory.getItemByItemCode(itemSelection) + ": $" + Inventory.getItemPriceByItemCode(itemSelection) + " purchased successfully!");
												System.out.println("Enjoy your " + Inventory.getItemByItemCode(itemSelection) + "! $1 off applied successfully! Happy BOGODO sale!");
												System.out.println("Here is your Balance: " + currency.format(myWallet.getCurrentMoney()));
												counter = 1;
												// log purchase info to transactionLog
												String LogInformation = currentTimeInFormat + " " + Inventory.getItemByItemCode(itemSelection) + " " + Inventory.getItemSlotNumberByItemCode(itemSelection) + " $" + Inventory.getItemPriceByItemCode(itemSelection) + " " + currency.format(myWallet.getCurrentMoney());
												currentLog.writingLog(LogInformation);
												// add purchase amount to the totalSales, update the sale amount information
												totalSales = totalSales.add(Inventory.getItemPriceByItemCode(itemSelection)).subtract(new BigDecimal("1"));
												Inventory.getVendingItemList().get(itemSelection-1).addBOGODOSale();
											}
										} else {
											// handle regular price purchase
											BigDecimal remainingBalance = Inventory.selectAndPurchase(Integer.toString(itemSelection), myWallet.getCurrentMoney());
											if(remainingBalance.compareTo(myWallet.getCurrentMoney()) != 0){
												myWallet.updateCurrentMoney(remainingBalance);
												System.out.println(Inventory.getItemByItemCode(itemSelection) + ": $" + Inventory.getItemPriceByItemCode(itemSelection) + " purchased successfully!");
												System.out.println("Enjoy your " + Inventory.getItemByItemCode(itemSelection) + "!");
												System.out.println("Here is your Balance: " + currency.format(myWallet.getCurrentMoney()));
												counter++;
												// log purchase info to transactionLog
												String LogInformation = currentTimeInFormat + " " + Inventory.getItemByItemCode(itemSelection) + " " + Inventory.getItemSlotNumberByItemCode(itemSelection) + " $" + Inventory.getItemPriceByItemCode(itemSelection) + " " + currency.format(myWallet.getCurrentMoney());
												currentLog.writingLog(LogInformation);
												// add purchase amount to the totalSales, update the sale amount information
												totalSales = totalSales.add(Inventory.getItemPriceByItemCode(itemSelection));
												Inventory.getVendingItemList().get(itemSelection-1).addRegularSale();
											}
										}
									} else if (item.getItemCode()==itemSelection && item.getQuantity()==0){
										// if the item is out, inform the customer
										System.out.println(itemSelection +": " + Inventory.getItemByItemCode(itemSelection) + " is sold out!");
										itemSelection = 0;
									}
								}
							}
						}
						if(!foundMatch){
							System.out.println("Invalid item selection, please try again!");
						}
						System.out.println();
					} else if(moneyChoice==3){
						double doubleAmount = myWallet.getCurrentMoney().doubleValue();
						myWallet.convertMoneyToCoins(doubleAmount);
						System.out.println("Thank you for Purchasing!");
						String LogInformation = currentTimeInFormat + " GIVE CHANGE: " + currency.format(myWallet.getCurrentMoney()) + " $0.00";
						currentLog.writingLog(LogInformation);
						System.out.println();
						break;
					} else {
						System.out.println("Please make a valid selection.");
					}
				}
			}
			else if (choice==3){
				System.out.println("Exiting the vending machine now.");
				break;
			} else if(choice==4){
				// hidden menu, shows total sales and write a log into an unique file
				printSalesReport();
				writingLog();
			} else if(choice==0) {
				break;
			} else {
					System.out.println("Invalid selection, please make a valid selection!");
					System.out.println();
			}
		}
	}

	/**
	 * write a unique sales report name and log required information into it
	 * for vending machine's current working cycle.
	 * @return void
	 */
	public void writingLog(){
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		File logFile = new File("TOTALSALES" + currentTimeForSalesReport + ".txt");
		try(PrintWriter log = new PrintWriter(logFile)){
			for(VendingItem item: salesReport){
				log.println(item.getProductName() + "|" + item.getTRegularSale() + "|" + item.getBOGODOSale());
			}
			log.println();
			log.println("***TOTAL SALES*** " + currency.format(totalSalesForReport));
		} catch (FileNotFoundException e){
				System.out.println("Unable to open a log file: " + logFile.getAbsolutePath());
		}
	}

	/**
	 * print a unique sales report for vending machine's current working cycle.
	 * @return void
	 */
	private void printSalesReport(){
		totalSalesForReport = totalSales;
		salesReport = Inventory.getVendingItemList();
		System.out.println("***Sales Report Generated*** " + currentTimeInFormat + ": ");
		for(VendingItem item: Inventory.getVendingItemList()){
			System.out.println(item.getProductName() + "|" + item.getTRegularSale() + "|" + item.getBOGODOSale());
		}
		System.out.println();
		System.out.println("***TOTAL SALES*** " + currency.format(totalSales));
	}

}
