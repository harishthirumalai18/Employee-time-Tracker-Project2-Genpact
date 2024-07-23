<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import= "com.util.time_tracker.DBConnection"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete a Task</title>
    <link rel="stylesheet" type="text/css" href="CSS/delete-task.css">
</head>
<body>
    <div class="container">
        <h2>Delete a Task</h2>
        <form action="delete-task.jsp" method="post">
            <input type="submit" name="getTasks" value="Get Today's Tasks"/>
        </form>

        <% 
        if (request.getParameter("getTasks") != null) {
        	String query = "SELECT * FROM task_table WHERE task_date = ?";
        	try(Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(query)){
        		String currentDate = java.time.LocalDate.now().toString();
                pst.setString(1, currentDate);
                ResultSet rs = pst.executeQuery();
        %>

        <table>
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Employee Name</th>
                    <th>Task ID</th>
                    <th>Project Name</th>
                    <th>Task Role</th>
                    <th>Task Date</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Task Category</th>
                    <th>Task Description</th>
                </tr>
            </thead>
            <tbody>
                <% while (rs.next()) { %>
                <tr>
                    <td><%= rs.getString("emp_id") %></td>
                    <td><%= rs.getString("emp_name") %></td>
                    <td><%= rs.getInt("task_id") %></td>
                    <td><%= rs.getString("project_name") %></td>
                    <td><%= rs.getString("task_role") %></td>
                    <td><%= rs.getString("task_date") %></td>
                    <td><%= rs.getString("start_time") %></td>
                    <td><%= rs.getString("end_time") %></td>
                    <td><%= rs.getString("task_category") %></td>
                    <td><%= rs.getString("task_description") %></td>
                </tr>
                <% } %>
            </tbody>
        </table>

        <% 
                rs.close();
                pst.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p>Error occurred: " + e.getMessage() + "</p>");
            }
        }
        %>

        <!-- Form to delete specific task by task_id -->
        <h2>Delete Specific Task</h2>
        <form action="delete-task.jsp" method="post">
            <label for="task_id">Enter Task ID:</label>
            <input type="text" id="task_id" name="task_id" required/>
            <input type="submit" name="deleteTaskById" value="Get Task"/>
        </form>

        <% 
        if (request.getParameter("deleteTaskById") != null) {
            String task_id = request.getParameter("task_id");
            if (task_id != null && !task_id.isEmpty()) {
            	String query ="SELECT * FROM task_table WHERE task_id = ? AND task_date = CURDATE()";
                try (Connection con = DBConnection.getConnection();
                    PreparedStatement pst = con.prepareStatement(query)){
                    pst.setString(1, task_id);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
        %>

        <table>
            <tr>
                <th>Employee ID</th>
                <th>Employee Name</th>
                <th>Task ID</th>
                <th>Project Name</th>
                <th>Task Role</th>
                <th>Task Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Task Category</th>
                <th>Task Description</th>
            </tr>
            <tr>
                <td><%= rs.getString("emp_id") %></td>
                <td><%= rs.getString("emp_name") %></td>
                <td><%= rs.getInt("task_id") %></td>
                <td><%= rs.getString("project_name") %></td>
                <td><%= rs.getString("task_role") %></td>
                <td><%= rs.getString("task_date") %></td>
                <td><%= rs.getString("start_time") %></td>
                <td><%= rs.getString("end_time") %></td>
                <td><%= rs.getString("task_category") %></td>
                <td><%= rs.getString("task_description") %></td>
            </tr>
        </table>
        <br>
        <div class="delete-section">
            <form action="delete-task" method="post">
                <input type="hidden" name="task_id" value="<%= task_id %>"/>
                <input type="submit" value="Delete Task"/>
            </form>
        </div>

        <% 
                    } else {
                        out.println("<br>No task found with the provided task ID for today.");
                    }
                    rs.close();
                    pst.close();
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<p>Error occurred: " + e.getMessage() + "</p>");
                }
            }
        }
        %>
    </div>
</body>
</html>
