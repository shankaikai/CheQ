package com.example.cheq.Login;

import org.junit.Test;

import static org.junit.Assert.*;

public class InputValidationTest {

    InputValidation inputValidation = new InputValidation();

    @Test
    public void isValidNumberTest() {
        String validNumber = "88888888";
        Boolean actual = inputValidation.isValidNumber(validNumber);
        assertEquals(true, actual);
    }
    @Test
    public void isValidNumberTestFalse() {
        String invalidNumber1 = "88888888a";
        String invalidNumber2 = "";
        String invalidNumber3 = "asdjfaljas";
        String invalidNumber4 = "12345678";
        String invalidNumber5 = "888888881";
        Boolean actual1 = inputValidation.isValidNumber(invalidNumber1);
        Boolean actual2 = inputValidation.isValidNumber(invalidNumber2);
        Boolean actual3 = inputValidation.isValidNumber(invalidNumber3);
        Boolean actual4 = inputValidation.isValidNumber(invalidNumber4);
        Boolean actual5 = inputValidation.isValidNumber(invalidNumber5);
        assertEquals(false, actual1);
        assertEquals(false, actual2);
        assertEquals(false, actual3);
        assertEquals(false, actual4);
        assertEquals(false, actual5);
    }
    @Test (expected= NumberFormatException.class)
    public void isValidNumberTestException( ) {
        Integer.parseInt("abccdef");
    }

    @Test
    public void isValidEmail() {
        String validEmail = "abc@gmail.com";
        assertEquals(true, inputValidation.isValidEmail(validEmail));
        String validEmail1 = "abc@gmail.sg";
        assertEquals(true, inputValidation.isValidEmail(validEmail1));
    }
    @Test
    public void isValidEmailFalse() {
        String invalidEmail = "abcgmail.com";
        assertEquals(false, inputValidation.isValidEmail(invalidEmail));
        String invalidEmail2 = "";
        assertEquals(false, inputValidation.isValidEmail(invalidEmail2));
    }

    @Test
    public void isValidPassword() {
        assertEquals(true, inputValidation.isValidPassword("password"));
        assertEquals(true, inputValidation.isValidPassword("138507894723"));
    }
    @Test
    public void isValidPasswordFalse() {
        assertEquals(false, inputValidation.isValidPassword("pas"));
        assertEquals(false, inputValidation.isValidPassword(""));
    }
}