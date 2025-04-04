package com.piusxi.admin.backend;

import java.util.Random;

public class adminToken {

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            token.append(characters.charAt(index));
        }
        return token.toString();
    }

    public static void main(String[] args) {
        int length = 32;
        String token = generateRandomString(length);
        System.out.println("Random string of length " + length + ": " + token);
    }
}