package com.techelevator.view;

public class Chip extends VendingItem{

    public Chip(String name, double price, String slot) {
        super(name, price, slot);
    }

    @Override
    public void displayMessage() {
        System.out.println("Crunch Crunch, It's Yummy!");
    }

}
