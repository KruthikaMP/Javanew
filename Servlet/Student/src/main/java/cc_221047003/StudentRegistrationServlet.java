package cc_221047003;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentRegistrationServlet")
public class StudentRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String REG_NO_PREFIX = "2022CLOUD";
    private int regNoCounter = 1;

    @Override
    public void init() throws ServletException {
        // Initialize the registration number counter from the database
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            
            //Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Connection connection = null;
			String connectionUrl = "jdbc:sqlserver://172.16.51.44;" +
		            "databaseName=221047003;trustServerCertificate=true;integratedSecurity=false;user=KRUTHIKA;password=kruthi@41";
		    connection = DriverManager.getConnection(connectionUrl);
            String sql = "SELECT MAX(reg_no) FROM students WHERE reg_no LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, REG_NO_PREFIX + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String maxRegNo = rs.getString(1);
                if (maxRegNo != null) {
                    regNoCounter = Integer.parseInt(maxRegNo.substring(REG_NO_PREFIX.length())) + 1;
                }
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch ( SQLException e) {
            throw new ServletException("Error initializing registration number counter", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || dob.isEmpty()) {
            out.println("<p style='color:red'>Please fill all fields!</p>");
        } else {
            String regNo = REG_NO_PREFIX + String.format("%06d", regNoCounter);

            try {
                //Class.forName("com.mysql.cj.jdbc.Driver");
                //Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            	Connection connection = null;
    			String connectionUrl = "jdbc:sqlserver://172.16.51.44;" +
    		            "databaseName=221047003;trustServerCertificate=true;integratedSecurity=false;user=KRUTHIKA;password=kruthi@41";
    		    connection = DriverManager.getConnection(connectionUrl);
            	String sql = "INSERT INTO stud_master (name, address, phone, email, dob, reg_no) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, address);
                stmt.setString(3, phone);
                stmt.setString(4, email);
                stmt.setString(5, dob);
                stmt.setString(6, regNo);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    out.println("<h2>Student Registration Successful!</h2>");
                    out.println("<p>Registration Number: " + regNo + "</p>");
                    regNoCounter++;
                } else {
                    out.println("<p style='color:red'>Failed to register student!</p>");
                }
                stmt.close();
                connection.close();
            } catch ( SQLException e) {
                out.println("<p style='color:red'>" + e.getMessage() + "</p>");
            }
        }
    }
}
