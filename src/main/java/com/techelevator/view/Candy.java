package com.techelevator.view;

public class Candy extends VendingItem{

    public Candy(String name, double price, String slot) {
        super(name, price, slot);
    }

    @Override
    public void displayMessage() {
        System.out.println("Munch Munch, Mmm Mmm Good!");
    }

}
