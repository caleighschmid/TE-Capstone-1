package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void initializeInventoryProducesCorrectNumberOfItems() {
        vendingMachine.initializeInventory();
        assertEquals(16, vendingMachine.getItems().size());
    }

    @Test
    public void depositMoneyReturnsCorrectBalanceAndPrintsMessage(){
        String simulatedInput = "20\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        vendingMachine.depositMoney();

        System.setIn(System.in);
        System.setOut(System.out);

        assertEquals(20.0, vendingMachine.getBalance(), 0.001);

        String consoleOutput = outputStream.toString();
        Assert.assertTrue(consoleOutput.contains("How much would you like to deposit? (Enter amount in whole dollars, please)"));
    }


    @Test
    public void makePurchaseReducesInventoryAndSubtractsFunds() {
        VendingItem testItem = new Chip("Test Chips", 1.0, "A1");
        vendingMachine.getItems().add(testItem);

        String simulatedInput = "A1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        vendingMachine.setBalance(5.0);
        vendingMachine.makePurchase();

        System.setIn(System.in);
        System.setOut(System.out);


        String consoleOutput = outputStream.toString();

        Assert.assertTrue(consoleOutput.contains("You purchased"));
        assertEquals(4.0, vendingMachine.getBalance(), 0.001);
        assertEquals(4, testItem.getInventory());
    }

    @Test
    public void makePurchaseWithoutFundsReturnsErrorMessage() {
        VendingItem testItem = new Chip("Test Chips", 1.0, "A1");
        vendingMachine.getItems().add(testItem);

        String simulatedInput = "A1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        vendingMachine.setBalance(0);
        vendingMachine.makePurchase();

        System.setIn(System.in);
        System.setOut(System.out);

        String consoleOutput = outputStream.toString();

        Assert.assertTrue(consoleOutput.contains("Sorry, you do not have enough funds to purchase " + testItem.getName() + " ."));
        assertEquals(0, vendingMachine.getBalance(), 0.001);
        assertEquals(5, testItem.getInventory());
    }

    @Test
    public void makePurchaseOfSoldOutItemReturnsErrorMessage() {
        VendingItem testItem = new Chip("Test Chips", 1.0, "A1");
        vendingMachine.getItems().add(testItem);

        String simulatedInput = "A1\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        testItem.setInventory(0);
        vendingMachine.setBalance(5);
        vendingMachine.makePurchase();

        System.setIn(System.in);
        System.setOut(System.out);

        String consoleOutput = outputStream.toString();

        Assert.assertTrue(consoleOutput.contains("Sorry, " + testItem.getName() + " is currently out of stock."));
        assertEquals(5, vendingMachine.getBalance(), 0.001);
        assertEquals(0, testItem.getInventory());
    }

    @Test
    public void endTransactionReturnsBalanceToZero() {
        vendingMachine.setBalance(10);
        vendingMachine.endTransaction();
        assertEquals(0.0, vendingMachine.getBalance(), 0.001);
    }

    @Test
    public void endTransactionReturnsCorrectAmountOfCoins() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        vendingMachine.setBalance(0.15);
        vendingMachine.endTransaction();

        System.setIn(System.in);
        System.setOut(System.out);

        String consoleOutput = outputStream.toString();

        Assert.assertTrue(consoleOutput.contains("Change returned: 1 dime, 1 nickel"));
    }

}
