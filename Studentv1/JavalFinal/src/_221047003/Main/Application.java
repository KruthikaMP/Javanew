package _221047003.Main;
import java.util.Scanner;
import _221047003.implementation.StudentMasterDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import _221047003.valueobject.StudentMaster;
public class Application {
	static Logger logger = LogManager.getRootLogger();	
	 @SuppressWarnings("resource")
	public static void main(String[] args)throws Exception {
		  StudentMasterDaoImpl stud=new StudentMasterDaoImpl();
		  StudentMaster studentMaster=new StudentMaster();
		  Scanner sc=new Scanner(System.in);
		  System.out.println("Enter your choice 1.insert 2.View");
		 int n=sc.nextInt();
		  if(n==1) {
			  logger.debug("Option selected = "+n);
		  stud.insertStudent(studentMaster);
		 }
		else if(n==2) {
			stud.getAllStudents();
		}
	 }
}
