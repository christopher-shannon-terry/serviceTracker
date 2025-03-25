
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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

public class createAccount {
    private static final String url = "jdbc:mysql://localhost:3306/studentDatabase";
    private static final String user = "root@localhost";;
    private static final String password = "";

    public boolean createNewAccount(String email, String password, int studentID, String firstName, String lastName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String insertData = "INSERT INTO studentInfo (email, password, studentID, firstName, lastName) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insertData);
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setInt(3, studentID);
                statement.setString(4, firstName);
                statement.setString(5, lastName);
    
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
            catch (Exception e) {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public boolean isAccountCreated(String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String checkData = "SELECT * FROM studentInfo WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(checkData);
                statement.setString(1, email);

                return statement.executeQuery().next();
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        createAccount newAccount = new createAccount();
        boolean accountCreated = newAccount.createNewAccount("test@example.com", "password123", 12345, "John", "Doe");
        System.out.println("Account created " + accountCreated);

        boolean accountExists = newAccount.isAccountCreated("test@example.com");
        System.out.println("Account exists " + accountExists);
    }
}
