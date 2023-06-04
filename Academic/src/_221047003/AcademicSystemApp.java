package _221047003;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AcademicSystemApp {


    public static void main(String[] args) {

        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Add Elective Score");
            System.out.println("2. View Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
        
        try {
        	 Properties properties = new Properties();
       	  Connection conn = DriverManager.getConnection("jdbc:sqlserver://172.16.51.44;", properties);
            
       	  		int choice = promptInt("");

                switch (choice) {
                    case 1:
                    	Daooperations.addElectiveScore(conn);
                        break;
                    case 2:
                    	Daooperations.viewStudentDetails(conn);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        }
    }
    public static int promptInt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextInt();
    }

    public static double promptDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextDouble();
    }
}
/*CREATE TABLE Base (
  student_id INT PRIMARY KEY,
  student_name VARCHAR(50),
  elective_score_percentage DECIMAL(5, 2)
);

-- Create the ElectiveSubject table
CREATE TABLE ElectiveSubject (
  subject_id INT PRIMARY KEY,
  subject_name VARCHAR(50)
);

-- Create the StudentElectiveScore table
CREATE TABLE StudentElectiveScore (
  score_id INT PRIMARY KEY,
  student_id INT,
  subject_id INT,
  score DECIMAL(5, 2),
  FOREIGN KEY (student_id) REFERENCES Base(student_id),
  FOREIGN KEY (subject_id) REFERENCES ElectiveSubject(subject_id)
);





*/

