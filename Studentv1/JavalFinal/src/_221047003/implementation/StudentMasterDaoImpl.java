package _221047003.implementation;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import _221047003.exceptions.StudentMasterException;
import _221047003.operations.StudentMasterDao;
import _221047003.valueobject.StudentMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
//import java.util.logging.Logger;
public class StudentMasterDaoImpl implements StudentMasterDao {
	//private static final Logger LOGGER = Logger.getLogger(StudentMasterDaoImpl.class.getName());
	//Logger logger = (Logger) LogManager.getRootLogger();
	//private static final Logger logger = LogManager.getLogger("StudentMasterDaoImpl.class.getName()");
	private static final Logger logger = LogManager.getLogger("StudentLogFile.log");
	private static void configureRootLogger() {
	  try {
	  FileHandler fh = new FileHandler  ("StudentLogFile.log");
	  fh.setFormatter(new SimpleFormatter());
	  LogManager.getLogger(fh);
	  } catch (IOException e) {
	  logger.warn("Could not add handler to log to file");
	  }
	  }
  private static final String PROPERTIES_FILE = "database.properties";
  Properties properties = new Properties();
  Connection connection = null;
@SuppressWarnings("resource")
public void insertStudent(StudentMaster studentMaster) throws StudentMasterException {   
    try {
    	configureRootLogger();
      // Load the properties file
    	 logger.debug("Inside the add student method");
    	properties.load(new FileInputStream(PROPERTIES_FILE));
      
      // Connect to the database
      connection = DriverManager.getConnection("jdbc:sqlserver://172.16.51.44;", properties);
      //LOGGER.log(Level.INFO, "Connected to the database.");
      logger.info("Connect to db");
      // Read the student data from the user
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter the roll number: ");
      int rollNumber = scanner.nextInt();
      System.out.print("Enter the name: ");
      String name = scanner.next();
      System.out.print("Enter the address: ");
      String address = scanner.next();
      System.out.print("Enter the BE branch: ");
      String beBranch = scanner.next();
      // Insert the student record into the database
      PreparedStatement statement = connection.prepareStatement("INSERT INTO student_master (roll_number, name, address, address) VALUES (?, ?, ?, ?)");
      statement.setInt(1, rollNumber);
      statement.setString(2, name);
      statement.setString(3, address);
      statement.setString(4, beBranch);
      statement.executeUpdate();
      	FileWriter myWritter = new FileWriter("C:\\Users\\ksand\\OneDrive\\Desktop\\Java2\\JavalFinal\\studentdetails.txt",true);
		myWritter.write(rollNumber+ "\t "  +name+ " \t            " +address+ "\t " +beBranch+"\n");
	    logger.debug(rollNumber+ "  Student data added successfully.");
		myWritter.close();
      logger.info("Details added for the ID ="+((StudentMaster) statement).getRollNumber());
    }
	catch (IOException e) {
		logger.error(e.getMessage());
		//System.out.println("will this check for property file-Yes");		
		//For error in reading property file
    }
    catch (SQLException e) {
      if (e.getErrorCode() == 19) {
    	  //for PK voilation
    	  System.out.println("will this check for PK voilation=yes");
    	  logger.error(e.getMessage());
      } else {
    	  //Failed to coonect to database
    	  //logger.print("Unable to connect");
    	  logger.error(e.getMessage());
    	 // System.out.println("will this check for connectivity=yes");
      }
    } finally 
    //finally to close the connection
    {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
    	  logger.error(e.getMessage());
      }
    }
}

@SuppressWarnings("resource")
public List<StudentMaster> getAllStudents() throws StudentMasterException {
    try 
    {
    	configureRootLogger();
    	properties.load(new FileInputStream(PROPERTIES_FILE));

        // Connect to the database
        connection = DriverManager.getConnection("jdbc:sqlserver://172.16.51.44;", properties);
        logger.info("Connected to the database.");
    	
    PreparedStatement statement = connection.prepareStatement("SELECT * FROM student_master where rollNumber=?");
    System.out.println("Enter rollnumber to view Student");
	Scanner sc=new Scanner(System.in);
	int rn=sc.nextInt();
	statement.setInt(1,rn);
    int n=statement.executeUpdate();
    if(n==1)
    {
      ResultSet resultSet = statement.executeQuery();
      List<StudentMaster> students = new ArrayList<>();
      while (resultSet.next()) {
        int rollNumber = resultSet.getInt("rollNumber");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String beBranch = resultSet.getString("BE_branch");
        students.add(new StudentMaster(rollNumber, name, address, beBranch));
      }
      return students;
    } 
    }catch (SQLException e) {
        if (e.getErrorCode() == 19) {
          //LOGGER.log(Level.SEVERE, "Error: primary key violation");
        	logger.error(e.getMessage());
        } else {
         // LOGGER.log(Level.SEVERE, "Error connecting to the database or executing SQL: {0}", e.getMessage());
        	logger.error(e.getMessage());}
      }  catch (IOException e) {
		logger.error(e.getMessage());  
		//LOGGER.log(Level.SEVERE, "Error reading properties file: {0}", e.getMessage());
	    } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException e) {
        	logger.error(e.getMessage());
        	//LOGGER.log(Level.SEVERE, "Error closing the database connection: {0}", e.getMessage());
        }
      }
	return null;
  }
}

