package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private List<VendingItem> items = new ArrayList<>();
    private double balance = 0.0;
    private double sales = 0.0;

    public double getBalance() {
        return balance;
    }

    public List<VendingItem> getItems() {
        return items;
    }

//    public VendingMachine() {
//        try {
//            initializeInventory();
//        } catch (FileNotFoundException e) {
//            System.out.println("Error: Vending machine inventory file not found.");
//        }
//    }

    //Method to initialize vending machine inventory from CSV file
    public void initializeInventory() {
        File inputFile = new File("vendingmachine.csv");
        List<VendingItem> machineSupply = new ArrayList<>();

        try {
            Scanner sc = new Scanner(inputFile);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] itemData = line.split("\\|");

                String slot = itemData[0];
                String name = itemData[1];
                double price = Double.parseDouble(itemData[2]);
                String category = itemData[3];

                if (category.equals("Chip")) {
                    Chip newChip = new Chip(name, price, slot);
                    machineSupply.add(newChip);
                } else if (category.equals("Candy")) {
                    Candy newCandy = new Candy(name, price, slot);
                    machineSupply.add(newCandy);
                } else if (category.equals("Gum")) {
                    Gum newGum = new Gum(name, price, slot);
                    machineSupply.add(newGum);
                } else if (category.equals("Drink")) {
                    Drink newDrink = new Drink(name, price, slot);
                    machineSupply.add(newDrink);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        items = machineSupply;
    }

    //Method to return inventory of entire vending machine
    public void getMachineInventory() throws FileNotFoundException {
        for (VendingItem item : items) {
            if (item.getInventory() > 0) {
                System.out.printf("%-4s %-20s %1s %-4.2f %-10s %-2s \n", item.getSlot(), item.getName(), "$", item.getPrice(), "Available: ", item.getInventory());
            } else if (item.getInventory() == 0) {
                System.out.printf("%-4s %-20s %1s %-4.2f %-10s \n", item.getSlot(), item.getName(), "$", item.getPrice(), "SOLD OUT");
            }
        }
    }

    //Method to add money to user's balance
    public void depositMoney() {
        System.out.println();
        System.out.println("How much would you like to deposit? (Enter amount in whole dollars, please)");
        Scanner userInput = new Scanner(System.in);
        double amount = 0;
        try {
            amount = Double.parseDouble(userInput.nextLine());
            balance = balance + amount;
            logTransaction("FEED MONEY", amount, balance);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    //Method to exchange money for item; select item slot, deduct one item from
    //item's inventory, deduct money from user's balance, add money to sales total
    public void makePurchase() {
        System.out.println();
        System.out.print("Please enter the slot of the desired item >>> ");
        Scanner userInput = new Scanner(System.in);
        String slot = userInput.nextLine();
        int numberOfIncorrectSlots = 0;
        for (VendingItem item : items) {
            if (item.getSlot().equals(slot) && item.getInventory() > 0 && balance >= item.getPrice()) {
                balance = balance - item.getPrice();
                item.decrementQuantity();
                sales = sales + item.getPrice();
                System.out.printf("%-19s %-10s %-7s %-1.2f %-1s", "\nYou purchased ", item.getName(), " for $", item.getPrice(), ".");
                item.displayMessage();
                System.out.println();
                System.out.printf("%22s %-2.2f", "Your new balance is: $", balance);
                logTransaction(item.getName() + " " + item.getSlot(), item.getPrice(), balance);
            } else if (item.getSlot().equals(slot) && item.getInventory() == 0) {
                System.out.println("Sorry, " + item.getName() + " is currently out of stock.");
            } else if (item.getSlot().equals(slot) && balance < item.getPrice()) {
                System.out.println("Sorry, you do not have enough funds to purchase " + item.getName() + " .");
            } else {
                numberOfIncorrectSlots = numberOfIncorrectSlots + 1;
            }
            if (numberOfIncorrectSlots == 16) {
                System.out.println("Sorry, you have entered an invalid slot number.");
            }
        }

    }

    //Method for ending customer transaction; returns change in quarters, nickels and dimes
    //using smallest possible number of each.  Machine's current balance returns to 0
    public void endTransaction() {
        double changeAmount = balance;
        BigDecimal decimalBalance = new BigDecimal(String.valueOf(BigDecimal.valueOf(balance)));

        int quarters = decimalBalance.divide(new BigDecimal("0.25"), 0, BigDecimal.ROUND_DOWN).intValue();
        decimalBalance = decimalBalance.remainder(new BigDecimal("0.25"));

        int dimes = decimalBalance.divide(new BigDecimal("0.10"), 0, BigDecimal.ROUND_DOWN).intValue();
        decimalBalance = decimalBalance.remainder(new BigDecimal("0.10"));

        int nickels = decimalBalance.divide(new BigDecimal("0.05"), 0, BigDecimal.ROUND_DOWN).intValue();

        String changeMessage = "Change returned: ";
        if (quarters > 0) {
            changeMessage += quarters + (quarters > 1 ? " quarters" : " quarter");
        }

        if (dimes > 0) {
            changeMessage += (quarters > 0 ? ", " : "") + dimes + (dimes > 1 ? " dimes" : " dime");
        }

        if (nickels > 0) {
            changeMessage += ((quarters > 0 || dimes > 0) ? ", " : "") + nickels + (nickels > 1 ? " nickels" : " nickel");
        }

        System.out.println(changeMessage);
        balance = 0;
        logTransaction("GIVE CHANGE", changeAmount, balance);
    }

    //Method for logging all machine transactions
    public void logTransaction(String action, double moneyExchanged, double newBalance) {
        String logFile = "Log.txt";
        File log = new File(logFile);
        try (FileWriter fw = new FileWriter(log, true)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            String timestamp = sdf.format(new Date());
            String formattedMoneyExchanged = String.format("%.2f", moneyExchanged);
            String formattedNewBalance = String.format("%.2f", newBalance);

            fw.write(timestamp + " " + action + ": $" + formattedMoneyExchanged + " $" + formattedNewBalance + "\n");

        } catch (IOException e) {
            System.out.println("Error: Unable to log transaction.");
        }
    }
}


