package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private List<VendingItem> items = new ArrayList<>();
    private double balance;
    private double sales;

//    public VendingMachine() {
//        items.add(new Chip("Potato Crisps", 3.05, "A1"));
//        items.add(new Chip("Stackers", 1.45,"A2"));
//        items.add(new Chip("Grain Waves", 2.75, "A3"));
//        items.add(new Chip("Cloud Popcorn", 3.65, "A4"));
//        items.add(new Candy("Moonpie", 1.80,"B1"));
//        items.add(new Candy("Cowtales", 1.50, "B2"));
//        items.add(new Candy("Wonka Bar", 1.50,"B3"));
//        items.add(new Candy("Crunchie", 1.75, "B4"));
//        items.add(new Drink("Cola", 1.25,"C1"));
//        items.add(new Drink("Dr. Salt", 1.50, "C2"));
//        items.add(new Drink("Mountain Melter", 1.50,"C3"));
//        items.add(new Drink("Heavy", 1.50, "C4"));
//        items.add(new Gum("U-Chews", 0.85,"D1"));
//        items.add(new Gum("Little League Chew", 0.95, "D2"));
//        items.add(new Gum("Chiclets", 0.75,"D3"));
//        items.add(new Gum("Triplemint", 0.75, "D4"));
//    }


    //Method to return inventory of entire vending machine
    public void getMachineInventory() throws FileNotFoundException {
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
    public void makePurchase(String slot){
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
