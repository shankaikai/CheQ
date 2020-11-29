package com.example.cheq.Login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    /**
     * This method checks if the input string is a valid Singaporean number and returns true/false.
     * @param s a String object
     * @return a boolean value
     */
    public static boolean isValidNumber(String s) {
        if (s.length() == 8 && (s.charAt(0) == '6' | s.charAt(0) == '8' | s.charAt(0) == '9')) {
            try {
                int num = Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Number!");
            }
        }
        return false;
    }

    /**
     * This method checks if the input string is a valid email address and returns true/false.
     * @param userEmail a String object
     * @return a boolean value
     */
    public static boolean isValidEmail(String userEmail) {
        // Email regex pattern
        String regex = "^(.+)@(.+)$";

        // Initialize the Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Initialize the Matcher object
        Matcher matcher = pattern.matcher(userEmail);

        // Return if email matches the regex pattern
        return matcher.matches();
    }

    /**
     * This method checks if the input string is a valid password and returns true/false.
     * @param password a String object
     * @return a boolean value
     */
    public static boolean isValidPassword(String password) {
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }

}