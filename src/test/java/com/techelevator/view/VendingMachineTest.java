package com.techelevator.view;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineTest {
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void testInitializeInventory() {
        vendingMachine.initializeInventory();
        assertEquals(16, vendingMachine.getItems().size());
    }

    @Test
    public void testDepositMoney(){
        vendingMachine.depositMoney(5.0);
        assertEquals(5.0, vendingMachine.getBalance(), 0.001);
    }

    @Test
    public void testMakePurchase() {
        VendingItem testItem = new Chip("Test Chips", 1.0, "A1");
        vendingMachine.getItems().add(testItem);
        vendingMachine.depositMoney(2.0);
        vendingMachine.makePurchase("A1");
        assertEquals(1.0, vendingMachine.getBalance(), 0.001);
        assertEquals(4, testItem.getInventory());
    }

    @Test
    public void testEndTransaction() {
        vendingMachine.depositMoney(3.0);
        vendingMachine.endTransaction();
        assertEquals(0.0, vendingMachine.getBalance(), 0.001);
    }
    @Test
    public void testLogTransaction() {
        vendingMachine.logTransaction("TEST ACTION", 2.0, 5.0);
    }
}
