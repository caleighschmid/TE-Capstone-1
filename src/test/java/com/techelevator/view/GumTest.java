package com.techelevator.view;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GumTest {
    @Test
    public void testDisplayMessage() {
        Gum gum = new Gum("Test Gum", 0.85, "D1");
        String expectedMessage = "\nChew Chew, Pop!";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        gum.displayMessage();
        System.setOut(System.out);

        String actualMessage = outputStream.toString().trim();
        assertEquals(expectedMessage.trim(), actualMessage);
    }
}
