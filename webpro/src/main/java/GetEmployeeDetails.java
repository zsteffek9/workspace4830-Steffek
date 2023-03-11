import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/GetEmployeeDetails")
public class GetEmployeeDetails extends HttpServlet {
    private static final long serialVersionUID = 1 ;

    String dns = "ec2-3-144-125-93.us-east-2.compute.amazonaws.com";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmployeeDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Fetch Employee Details";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor = \"##CCCCFF\">\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/myDB", "steffekremote", "password");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * FROM myTable WHERE MYUSER=?";
        try {

            statement1 = connection.prepareStatement(sql);
            String theUserName = keyword;
            statement1.setString(1, theUserName);
     
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        try {

            rs = statement1.executeQuery();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("<table border=1 width=50% height=30%>");
        out.println("<tr><th>EmpId</th><th>EmpName</th><th>Email</th><th>Phone Number</th>");
        try {
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("ID");
                String username = rs.getString("MYUSER");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE");
                out.println("<tr><td>" + id + "</td><td>" + username + "</td><td>" + email + "</td><td>" + phone + "</td></tr>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}