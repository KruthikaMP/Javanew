package _221047003;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Properties;
//import _221047003.Daooperations;

public class BookStoreApp {
    
    public static void main(String[] args) {
        // Create database tables
    	//private static final String PROPERTIES_FILE = "database.properties";
  	  Properties properties = new Properties();
  	  Connection conn = null;
  	Scanner scanner = new Scanner(System.in);

    boolean exit = false;

    while (!exit) {
        System.out.println("Menu:");
        System.out.println("1. Create Book");
        System.out.println("2. Create Customer");
        System.out.println("3. Place Order");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
            	Daooperations.createBook(conn, scanner);
                break;
            case 2:
            	Daooperations.createCustomer(conn, scanner);
                break;
            case 3:
            	Daooperations.placeOrder(conn, scanner);
                break;
            case 4:
                exit = true;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
        // Establish the database connection
        try  {
        	conn = DriverManager.getConnection("jdbc:sqlserver://172.16.51.44;", properties);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

    
   
