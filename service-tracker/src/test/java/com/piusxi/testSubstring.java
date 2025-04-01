package com.piusxi;

import java.util.Scanner;

public class testSubstring {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID: ");
        String studentID = scanner.nextLine();

        int gradeYear = Integer.parseInt(studentID.substring(0, 2));
        System.out.println(gradeYear);
        
    }
}
