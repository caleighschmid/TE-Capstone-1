package com.techelevator.view;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ChipTest {
    @Test
    public void testDisplayMessage() {
        Chip chip = new Chip("Test Chip", 3.05, "A1");
        String expectedMessage = "\nCrunch Crunch, It's Yummy!";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        chip.displayMessage();
        System.setOut(System.out);

        String actualMessage = outputStream.toString().trim();
        assertEquals(expectedMessage, actualMessage);

    }
}
