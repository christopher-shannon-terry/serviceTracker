package com.piusxi.student.backend;

public class strongPasswordCheck {
    
    private static final int MIN_LENGTH = 8;
    private static final int MIN_UPPERCASE = 1;
    private static final int MIN_LOWERCASE = 1;
    private static final int MIN_DIGITS = 1;
    private static final int MIN_SPECIAL_CHARACTERS = 1;
    int ok = 2;

    /**
     * Checks if a password is strong based on predefined criteria.
     * 
     * @param password The password to check
     * @return true if the password meets all requirements, false otherwise
     */
    public static boolean isStrong(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }

        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        int specialCharCount = 0;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            }
            else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            }
            else if (Character.isDigit(c)) {
                digitCount++;
            }
            else {
                specialCharCount++;
            }
        }

        return uppercaseCount >= MIN_UPPERCASE &&
               lowercaseCount >= MIN_LOWERCASE &&
               digitCount >= MIN_DIGITS &&
               specialCharCount >= MIN_SPECIAL_CHARACTERS; 
    }

    /**
     * Validates a password and returns specific feedback about why it failed.
     * 
     * @param password The password to validate
     * @return A feedback string describing why the password is weak, or an empty string if it's strong
     */
    public static String getPasswordFeedback(String password) {
        if (password == null) {
            return "Password cannot be empty";
        }

        if (password.length() < MIN_LENGTH) {
            return String.format("Password must be at least %d characters long", MIN_LENGTH);
        }

        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        int specialCharCount = 0;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            }
            else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            }
            else if (Character.isDigit(c)) {
                digitCount++;
            }
            else {
                specialCharCount++;
            }
        }

        StringBuilder feedback = new StringBuilder();
        
        if (uppercaseCount < MIN_UPPERCASE) {
            feedback.append("Password must contain at least ").append(MIN_UPPERCASE).append(" uppercase letter(s). ");
        }
        
        if (lowercaseCount < MIN_LOWERCASE) {
            feedback.append("Password must contain at least ").append(MIN_LOWERCASE).append(" lowercase letter(s). ");
        }
        
        if (digitCount < MIN_DIGITS) {
            feedback.append("Password must contain at least ").append(MIN_DIGITS).append(" digit(s). ");
        }
        
        if (specialCharCount < MIN_SPECIAL_CHARACTERS) {
            feedback.append("Password must contain at least ").append(MIN_SPECIAL_CHARACTERS).append(" special character(s). ");
        }

        return feedback.toString();
    }
}
