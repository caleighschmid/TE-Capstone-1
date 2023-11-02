package com.techelevator.view;

public abstract class VendingItem {

    private String name;
    private double price;
    private String slot;
    private int inventory;

    public VendingItem(String name, double price, String slot) {
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.inventory = 5;
    }

    //Method for returning inventory of individual items - should be 5 every time app opens
    public int getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSlot() {
        return slot;
    }

    //Empty method overridden by subclasses to return "munch munch" or whatever
    public void displayMessage(){

    }

}
