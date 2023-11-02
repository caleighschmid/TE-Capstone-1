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

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] itemData = line.split("\\|");

            String slot = itemData[0];
            String name = itemData[1];
            double price = Double.parseDouble(itemData[2]);
            int inventory = 5;

        }
    }

    //Method to return inventory of entire vending machine
    public void getMachineInventory() {
        File inputFile = new File("vendingmachine.csv");
        Scanner sc = new Scanner(inputFile);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] items = line.split("\\|");

            double price = Double.parseDouble(items[2]);

            System.out.printf("%-4s %-20s %1s %-10.2f \n", items[0], items[1], "$", price);
        }
    }

    //Method to add money to user's balance
    public void depositMoney(double amount) {
        balance += amount;
    }

    //Method to exchange money for item; select item slot, deduct one item from
    //item's inventory, deduct money from user's balance, add money to sales total
    //Should we make these methods separate from each other? ^
    public void makePurchase(String slot) {
        for (VendingItem item : items) {
            if (item.getSlot().equals(slot) && item.getInventory() > 0 && balance >= item.getPrice()) {
                balance -= item.getPrice();
                item.decrementQuantity();
                sales += item.getPrice();
                System.out.println("You purchased " + item.getName() + " for $" + item.getPrice());
                return;
            }
        }


    }
}

