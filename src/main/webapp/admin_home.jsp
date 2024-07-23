<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.IOException" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import= "com.util.time_tracker.DBConnection"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Employee</title>
<link rel="stylesheet" type="text/css" href="CSS/admin_home.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h2>Welcome Admin</h2>
<hr>
<div class="container">
    <h2>Add New Employee</h2>
    <form action="admin_add_emp" method="post">
        <div>
            <label for="name">Employee Name</label>
            <input type="text" name="name" placeholder="Enter employee name" required/>
        </div>
        <div>
            <label for="age">Age</label>
            <input type="number" name="age" placeholder="Enter employee age" required/>
        </div>
        <div>
            <label for="role">Role</label>
            <input type="text" name="employee_role" placeholder="Enter employee role" required/>
        </div>
        <div>
            <label for="phone">Phone Number</label>
            <input type="text" name="phone_number" placeholder="Enter phone number" required/>
        </div>
        <div>
            <label for="email">Email</label>
            <input type="email" name="email" placeholder="Enter email" required/>
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" name="password" placeholder="Enter password" required/>
        </div>
        <input type="submit" value="Add Employee"/>
    </form>
</div><br/><br/>
<div class="operation">
	<a href="admin_edit_employee.jsp">Edit Employee Details</a>
	<a href="admin_delete_employee.jsp">Delete Employee Details</a>
</div>
<h2>List of Employees</h2>
    <div class="list-employees">
        <table>
            <tr>
                <th>Employee ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Role</th>
                <th>Phone Number</th>
                <th>Email</th>
            </tr>
            <%
            String query = "SELECT emp_id, emp_name, age, role, phone_number, email FROM employee_table";
            try (Connection con = DBConnection.getConnection();
                PreparedStatement pst= con.prepareStatement(query);
                ResultSet rs=pst.executeQuery();) {
                
                while(rs.next()){
                    %>
                    <tr>
                        <td><%=rs.getString("emp_id") %></td>
                        <td><%=rs.getString("emp_name") %></td>
                        <td><%=rs.getString("age") %></td>
                        <td><%=rs.getString("role") %></td>
                        <td><%=rs.getString("phone_number") %></td>
                        <td><%=rs.getString("email") %></td>
                    </tr>
                    <%
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
                    %>
        </table>
    </div>

<h2>Employee Work Duration Pie Chart</h2>
<canvas id="myPieChart" width="400" height="400"></canvas>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        fetch('GetEmployeeDurationsServlet')
            .then(response => response.json())
            .then(data => {
                const ctx = document.getElementById('myPieChart').getContext('2d');
                new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: data.labels,
                        datasets: [{
                            label: 'Work Duration (hours)',
                            data: data.durations,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                position: 'top',
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        return tooltipItem.label + ': ' + tooltipItem.raw + ' hours';
                                    }
                                }
                            }
                        }
                    }
                });
            })
            .catch(error => console.error('Error fetching data:', error));
    });
</script>
</body>
</html>