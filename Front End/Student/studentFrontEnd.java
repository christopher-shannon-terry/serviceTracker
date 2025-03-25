
import javax.swing.*;

public class studentFrontEnd extends JFrame {

    public studentFrontEnd() {
        setTitle("Service Submission");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        app();
    }

    // Default name for all initial renders is going to be "app" //
    private void app() {
        /*
         * This is where the homepage GUI (login page) for the student will go
         * 
         * Implementations:
         *  - Title -> "Service Submission"
         *  - Login message -> "Login or create an account to continue"
         *     - Link a database or something to store login info and check if the user is 1. a student and 2. doesnt already have an account
         * - Login button -> "Login"
         * - Verification code input for accounts already made
         *     - Possible remember me feature
         * - Create account button -> "Create Account"
         *     - Link to a new page with a form to fill out for account creation
         *     - Verify email and password (strong password checker)
         * - Forgot password button -> "Forgot Password"
         *     - Link to a new page with a form to fill out for password recovery
         *     - Verify email and send a recovery password and then prompt the user to change their password
         * 
         */
    }

    public static void main(String[] args) {
        studentFrontEnd student = new studentFrontEnd();
        student.setVisible(true);
    }    
}
