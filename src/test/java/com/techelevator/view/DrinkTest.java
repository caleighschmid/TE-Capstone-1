package com.techelevator.view;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
public class DrinkTest {
    @Test
    public void testDisplayMessage() {
        Drink drink = new Drink("Test Drink", 1.25, "C1");
        String expectedMessage = "\nGlug Glug, Chug Chug!";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        drink.displayMessage();
        System.setOut(System.out);

        String actualMessage = outputStream.toString().trim();
        assertEquals(expectedMessage, actualMessage);

    }
}
