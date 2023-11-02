package com.techelevator.view;

public class Drink extends VendingItem{

    public Drink(String name, double price, String slot) {
        super(name, price, slot);
    }

    @Override
    public void displayMessage() {
        System.out.println("\nGlug Glug, Chug Chug!");
    }

}

