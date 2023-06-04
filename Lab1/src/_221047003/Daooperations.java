package _221047003;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//import java.util.Properties;
public class Daooperations {
	 public static void createBook(Connection conn, Scanner scanner) {
	        
		 try {
	            System.out.print("Enter book title: ");
	            String title = scanner.nextLine();

	            System.out.print("Enter book author: ");
	            String author = scanner.nextLine();

	            System.out.print("Enter book price: ");
	            double price = scanner.nextDouble();
	            scanner.nextLine(); // Consume the newline character

	            String sql = "INSERT INTO Book (title, author, price) VALUES (?, ?, ?)";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setString(1, title);
	                stmt.setString(2, author);
	                stmt.setDouble(3, price);

	                stmt.executeUpdate();

	                System.out.println("Book created successfully!");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error creating book: " + e.getMessage());
	        }
	    }

	    public static void createCustomer(Connection conn, Scanner scanner) {
	        try {
	            System.out.print("Enter customer name: ");
	            String name = scanner.nextLine();

	            System.out.print("Enter customer address: ");
	            String address = scanner.nextLine();

	            System.out.print("Enter customer email: ");
	            String email = scanner.nextLine();

	            String sql = "INSERT INTO Customer (name, address, email) VALUES (?, ?, ?)";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setString(1, name);
	                stmt.setString(2, address);
	                stmt.setString(3, email);

	                stmt.executeUpdate();

	                System.out.println("Customer created successfully!");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error creating customer: " + e.getMessage());
	        }
	    }

	    public static void placeOrder(Connection conn, Scanner scanner) {
	        try {
	            System.out.print("Enter book ID: ");
	            int bookId = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            System.out.print("Enter customer ID: ");
	            int customerId = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character

	            String sql = "INSERT INTO BookOrder (book_id, customer_id) VALUES (?, ?)";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setInt(1, bookId);
	                stmt.setInt(2, customerId);

	                stmt.executeUpdate();

	                System.out.println("Order placed successfully!");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error placing order: " + e.getMessage());
	        }
	    }
	}
/*CREATE TABLE Book (
    book_id INT IDENTITY(1,1) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Customer (
    customer_id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE Order (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    book_id INT NOT NULL,
    customer_id INT NOT NULL,
    CONSTRAINT FK_Order_Book FOREIGN KEY (book_id) REFERENCES Book (book_id),
    CONSTRAINT FK_Order_Customer FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);
*/
