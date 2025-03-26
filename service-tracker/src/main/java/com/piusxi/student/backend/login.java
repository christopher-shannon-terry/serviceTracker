package com.piusxi.student.backend;

// import com.piusxi.student.frontend.studentLogin;

public class login {
    public static void main(String[] args) {
        
        /*
         * Will verify all values from database before student is able to login
         * 
         * Using student ID or email as primary identifier for accounts since those should be unique
         * 
         * Method 1 -> Decrypt the password in database and compare to password entered in studentLogin.java
         * 
         * Method 2 -> Encrypt the password in the password field and make sure the "new" encrypted version is the same as the encrpyted version in the database, because the same password should produce the same encryption
         * 
         * Method 1 seems the easiest because we can use RSA encrpytion for it since thats two way
         * Method 2 seems more secure but more room for errors especially if the same password doesnt return the same encryption
         * 
         */
    }

    public static void authenticateLogin() {
    }
}
