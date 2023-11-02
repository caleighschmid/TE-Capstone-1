package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private List<VendingItem> items = new ArrayList<>();
    private double balance = 0.0;
    private double sales = 0.0;

    public double getBalance() {
        return balance;
    }

    public VendingMachine() {
        try {
            initializeInventory();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Vending machine inventory file not found.");
        }
    }

    //Method to initialize vending machine inventory from CSV file
    public void initializeInventory() throws FileNotFoundException {
        File inputFile = new File("vendingmachine.csv");
        Scanner sc = new Scanner(inputFile);
        List<VendingItem> machineSupply = new ArrayList<>();

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
        items = machineSupply;
    }

    //Method to return inventory of entire vending machine
    public void getMachineInventory() throws FileNotFoundException {
        for (VendingItem item : items) {
            System.out.printf("%-4s %-20s %1s %-4.2f %-10s %-2s \n", item.getSlot(), item.getName(), "$", item.getPrice(), "Available: ", item.getInventory());
        }
    }

    //Method to add money to user's balance
    public void depositMoney() {
        System.out.println("How much would you like to deposit?");
        Scanner userInput = new Scanner(System.in);
        double amount = Double.parseDouble(userInput.nextLine());
        balance = balance + amount;
        System.out.printf("%22s %-2.2f", "Your new balance is: $", balance);
    }

    //Method to exchange money for item; select item slot, deduct one item from
    //item's inventory, deduct money from user's balance, add money to sales total
    public void makePurchase() {
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
                System.out.printf("%22s %-2.2f", "Your new balance is: $", balance);
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
        int quarters = (int) (balance / 0.25);
        balance = balance - quarters * 0.25;

        int dimes = (int) (balance / 0.10);
        balance = balance - dimes * 0.10;

        int nickels = (int) (balance / 0.05);
        balance = balance - nickels * 0.05;

        if (quarters > 1 && nickels > 1 && dimes > 1) {
            System.out.println("Change returned: " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickels.");
        } else if (quarters == 1 && nickels > 1 && dimes > 1) {
            System.out.println("Change returned: " + quarters + " quarter, " + dimes + " dimes, and " + nickels + " nickels.");
        } else if (quarters > 1 && nickels == 1 && dimes > 1) {
            System.out.println("Change returned: " + quarters + " quarters, " + dimes + " dimes, and " + nickels + " nickel.");
        } else if (quarters > 1 && nickels > 1 && dimes == 1) {
            System.out.println("Change returned: " + quarters + " quarters, " + dimes + " dime, and " + nickels + " nickels.");
        } else if (quarters == 1 && nickels == 1 && dimes > 1) {
            System.out.println("Change returned: " + quarters + " quarter, " + dimes + " dimes, and " + nickels + " nickel.");
        } else if (quarters == 1 && nickels > 1 && dimes == 1) {
            System.out.println("Change returned: " + quarters + " quarter, " + dimes + " dime, and " + nickels + " nickels.");
        } else if (quarters > 1 && nickels == 1 && dimes == 1) {
            System.out.println("Change returned: " + quarters + " quarters, " + dimes + " dime, and " + nickels + " nickel.");
        } else if (quarters == 1 && nickels == 1 && dimes == 1) {
            System.out.println("Change returned: " + quarters + " quarter, " + dimes + " dime, and " + nickels + " nickel.");
        }

        balance = 0;
    }
    }

