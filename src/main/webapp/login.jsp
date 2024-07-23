<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="CSS/login.css">

</head>
<body>
	<div class="header">
	<h2><a href="employee.jsp">Sign Up</a></h2>
    <h2>Login</h2>
	</div>
    <div class="form-container get_emp_id">
        <h2>Get Employee ID</h2>
        <form action="get_employee_detail" method="post">
            <label for="phone_number">Phone Number</label>
            <input type="text" name="phone_number" required/><br>
            
            <label for="password">Password</label>
            <input type="password" name="password" required/><br>
            
            <input type="submit" value="Get"/>
        </form>
        <%
            String emp_id = (String) request.getAttribute("emp_id");
            String emp_password = (String) request.getAttribute("emp_password");
            if (emp_id != null && emp_password != null) {
                out.println("<p>Your Employee ID: " + emp_id + "</p>");
                out.println("<p>Your Employee Password: " + emp_password + "</p>");
            }
        %>
    </div>
    <div class="form-container account-login">
        <h2>Account Login</h2>
        <form action="login" method="post">
            <label for="employee-id">Enter your Employee ID</label>
            <input type="text"name="employee-id" required/><br>       
            
            <label for="employee-password">Enter your Account Password</label>
            <input type="password" name="employee-password" required/><br>
            
            <input type="submit" value="Login"/>
        </form>
    </div>
</body>
</html>
