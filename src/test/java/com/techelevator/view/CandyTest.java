package com.techelevator.view;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CandyTest {
    @Test
    public void testDisplayMessage() {
        Candy candy = new Candy("Test Candy", 1.80, "B1");
        String expectedMessage = "\nMunch Munch, Mmm Mmm Good!";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        candy.displayMessage();
        System.setOut(System.out);

        String actualMessage = outputStream.toString().trim();
        assertEquals(expectedMessage.trim(), actualMessage);
    }
}
