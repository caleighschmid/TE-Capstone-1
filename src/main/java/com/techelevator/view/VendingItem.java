package com.techelevator.view;

public abstract class VendingItem {

    private String name;
    private double price;
    private String slot;
    private int inventory;
    private int amountSold;

    public VendingItem(String name, double price, String slot) {
        this.name = name;
        this.price = price;
        this.slot = slot;
        this.inventory = 5;
        this.amountSold = 0;
    }

    //For testing purposes
    public void setInventory(int inventory) {
        this.inventory = inventory;
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

    public int getAmountSold() {
        return amountSold;
    }

    //Method to increase count of how many items of a kind sold
    public void increaseAmountSold() {
        amountSold = amountSold + 1;
    }

  //Method to decrement the item's quantity when purchased
    public void decrementQuantity() {
        if (inventory > 0) {
            inventory--;
        } else if (inventory == 0) {
            System.out.println("Sorry, this item is out of stock.");
        }
    }

    //Method overridden by subclasses to display a message
    //Empty method overridden by subclasses to return "munch munch" etc
    public abstract void displayMessage();

    }
