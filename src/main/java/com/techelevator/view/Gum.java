package com.techelevator.view;

public class Gum extends VendingItem{

    public Gum(String name, double price, String slot) {
        super(name, price, slot);
    }

    @Override
    public void displayMessage() {
        System.out.println("Chew Chew, Pop!");
    }

}
