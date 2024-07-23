<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import= "com.util.time_tracker.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete an Employee Record</title>
   <link rel="stylesheet" type="text/css" href="CSS/admin_delete_employee.css">
</head>
<body>
    <h2>Delete an Employee Record</h2>
    <form class="get-employee" action="" method="post">
        <label>Enter the Employee ID:</label>
        <input type="text" name="emp_id" required/>
        <input type="submit" value="Get"/>
    </form>

    <%
    String emp_id = request.getParameter("emp_id");
    
    if (emp_id != null && !emp_id.isEmpty()) {
    	String query = "SELECT * FROM employee_table WHERE emp_id = ?";
    	try(Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(query)){
            pst.setString(1, emp_id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                %>
                <table>
                    <tr>
                        <th>Employee ID</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Role</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                    </tr>
                    <tr>
                        <td><%= rs.getString("emp_id") %></td>
                        <td><%= rs.getString("emp_name") %></td>
                        <td><%= rs.getString("age") %></td>
                        <td><%= rs.getString("role") %></td>
                        <td><%= rs.getString("phone_number") %></td>
                        <td><%= rs.getString("email") %></td>
                    </tr>
                </table><br>
                <div class="delete-section">
                    <form action="admin_delete_employee" method="post">
                        <input type="hidden" name="emp_id" value="<%= emp_id %>"/>
                        <input type="submit" value="Delete Employee"/>
                    </form>
                </div>
                <%
            } else {
                out.println("<br>No employee found with the provided Employee ID.");
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred: " + e.getMessage() + "</p>");
        }
    }
    %>
</body>
</html>
