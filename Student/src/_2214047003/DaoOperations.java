package _2214047003;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DaoOperations {

    public static void updateStudentTable(Connection conn, int studentId, int roomNumber) throws SQLException {
        String sql = "UPDATE Student SET room_number = ? WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomNumber);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        }
    }

    public static void insertIntoStudentRoomNumberMap(Connection conn, int studentId, int roomNumber) throws SQLException {
        String sql = "INSERT INTO Student_RoomNumber_Map (student_id, room_number) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, roomNumber);
            stmt.executeUpdate();
        }
    }
}
