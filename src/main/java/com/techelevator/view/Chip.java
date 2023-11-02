package com.techelevator.view;

public class Chip extends VendingItem{

    public Chip(String name, double price, String slot) {
        super(name, price, slot);
    }

    @Override
    public void displayMessage() {
        System.out.println("\nCrunch Crunch, It's Yummy!");
    }

}
