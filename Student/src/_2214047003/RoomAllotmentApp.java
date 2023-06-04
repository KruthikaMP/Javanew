package _2214047003;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Properties;
public class RoomAllotmentApp {

	Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try {
        	 Properties properties = new Properties();
        	  Connection conn = DriverManager.getConnection("jdbc:sqlserver://172.16.51.44;", properties);
            // Establish connection to DB1
             //conn = DriverManager.getConnection(conn);
            // Establish connection to DB2
             //conn = DriverManager.getConnection(conn);

            boolean exit = false;

            while (!exit) {
                System.out.println("Menu:");
                System.out.println("1. Allot Room to Student");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = promptInt("");

                switch (choice) {
                    case 1:
                        allotRoomToStudent(conn, conn);
                        break;
                    case 2:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }

            // Close the connections
            conn.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void allotRoomToStudent(Connection connect, Connection conn) throws SQLException {
        // Prompt for student details
        int studentId = promptInt("Enter student ID: ");
        String name = promptString("Enter student name: ");
        int roomNumber = promptInt("Enter room number: ");

        // Start the two-phase commit process
        conn.setAutoCommit(false);
        conn.setAutoCommit(false);

        try {
            // Update DB1 - Student table
        	DaoOperations.updateStudentTable(conn, studentId, roomNumber);
            // Insert into DB2 - Student_RoomNumber_Map table
        	DaoOperations.insertIntoStudentRoomNumberMap(conn, studentId, roomNumber);

            // Commit the changes in both databases
        	conn.commit();
        	conn.commit();

            System.out.println("Room allotment successfully completed!");
        } catch (SQLException e) {
            // Rollback the changes in case of any error
        	conn.rollback();
        	conn.rollback();

            System.out.println("Error during room allotment: " + e.getMessage());
        } finally {
            // Reset auto-commit mode
        	conn.setAutoCommit(true);
        	conn.setAutoCommit(true);
        }
    }


    // Utility methods for user input

    public static int promptInt(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String promptString(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
/*CREATE TABLE Student (
  student_id INT PRIMARY KEY,
  name VARCHAR(255),
  room_number INT
);
CREATE TABLE Student_RoomNumber_Map (
  map_id INT PRIMARY KEY,
  student_id INT,
  room_number INT,
  FOREIGN KEY (student_id) REFERENCES DB1.Student(student_id)
);
*/
