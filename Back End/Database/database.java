
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/*
 * Writing my own fucking database because SQL is not working and i dont have time to figure it out
 * I do have time
 * I dont however have the patience required
 * Therefore I dont have time
 * FUCK THAT LANGUAGE
 * 
 * Scratch above, gonna keep it cuz its funny
 */

public class database {
    public static void main(String[] args) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            Properties props = new Properties();
            props.load(new FileInputStream("database.properties"));
                
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            
            System.out.println("Testing connection to: " + url);
            
            // Open a connection
            Connection conn = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
