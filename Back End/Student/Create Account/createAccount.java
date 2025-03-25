

public class createAccount {
    /*
     * This file will implement a create account method (possibly use sign in with google)
     * When the student is creating their account, they will be prompted to enter their information
        * First name
        * Last name
        * Email
        * Student ID -> Could be useful because maybe we can link skyward and go from there, if not, we can just use the student ID as a unique identifier 
            * once the student ID is entered we can just use that as the username for login instead of email

    * Student will then be prompted to verify email

    * Stack hierachy to be a nerd
        * button *clicked* -> createAccount.java
            * -> verifyEmail.java
                * -> createAccount.java
                    * -> strongPasswordCheck.java
        
    * Essentially, createAccount.java will call verifyEmail.java
    * and verifyEmail.java will call back to createAccount.java 
    * which will then call strongPasswordCheck.java to verify password strength
    *
    * If all checks pass, the account will be created and the student will be prompted to login (or it will automatically log them in)
    * If any checks fail, the student will be prompted to fix the issue and try again
    */
}
