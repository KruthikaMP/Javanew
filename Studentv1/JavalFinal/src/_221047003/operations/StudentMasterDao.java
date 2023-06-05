package _221047003.operations;
import java.sql.SQLException;
import java.util.List;
import _221047003.exceptions.StudentMasterException;
import _221047003.valueobject.StudentMaster;
public interface StudentMasterDao {
	  void insertStudent(StudentMaster studentMaster) throws SQLException, StudentMasterException;
	  List<StudentMaster> getAllStudents() throws SQLException, StudentMasterException;
	}
