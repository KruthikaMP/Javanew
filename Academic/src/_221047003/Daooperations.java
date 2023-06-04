package _221047003;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Daooperations {
    public static void addElectiveScore(Connection conn) {
        int studentId = promptInt("Enter student ID: ");
        int subjectId = promptInt("Enter subject ID: ");
        double score = promptDouble("Enter score: ");

        try {
            // Start the atomic transaction
            conn.setAutoCommit(false);

            // Insert score into StudentElectiveScore table
            insertIntoStudentElectiveScore(conn, studentId, subjectId, score);

            // Calculate and update elective score percentage in Base table
            updateElectiveScorePercentage(conn, studentId);

            // Commit the transaction
            conn.commit();

            System.out.println("Elective score added successfully!");
        } catch (SQLException e) {
            // Rollback the transaction in case of any error
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error rolling back the transaction: " + ex.getMessage());
            }

            System.out.println("Error adding elective score: " + e.getMessage());
        }
    }

    public static void viewStudentDetails(Connection conn) {
        int studentId = promptInt("Enter student ID: ");

        try {
            String sql = "SELECT * FROM Base WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Student Name: " + rs.getString("student_name"));
                System.out.println("Elective Score Percentage: " + rs.getDouble("elective_score_percentage"));
            } else {
                System.out.println("Student not found!");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving student details: " + e.getMessage());
        }
    }

    public static void insertIntoStudentElectiveScore(Connection conn, int studentId, int subjectId, double score) throws SQLException {
        String sql = "INSERT INTO StudentElectiveScore (student_id, subject_id, score) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, studentId);
        stmt.setInt(2, subjectId);
        stmt.setDouble(3, score);
        stmt.executeUpdate();
        stmt.close();
    }

    public static void updateElectiveScorePercentage(Connection conn, int studentId) throws SQLException {
        String sql = "SELECT AVG(score) AS avg_score FROM StudentElectiveScore WHERE student_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            double averageScore = rs.getDouble("avg_score");
            String updateSql = "UPDATE Base SET elective_score_percentage = ? WHERE student_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setDouble(1, averageScore);
            updateStmt.setInt(2, studentId);
            updateStmt.executeUpdate();
            updateStmt.close();
        }

        rs.close();
        stmt.close();
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
