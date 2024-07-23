<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign up</title>
<link rel="stylesheet" type="text/css" href="CSS/employee.css">
</head>
<body>
	<div class="header">
	<h2>Sign Up</h2>
	<h2><a href="login.jsp">Login</a></h2>
	</div>
	<form action="register" method="post">
		<label>Name</label>
		<input type="text" name="name"/><br><br>
		
		<label>Age</label>
		<input type="text" name="age"/><br><br>
		
		<label>Role</label>
		<input type="text" name="employee_role"><br><br>
		
		<label>Phone Number</label>
		<input type="text" name="phone_number"/><br><br>
		
		<label>Email</label>
		<input type="email" name="email"/><br><br>
		
		<label>Temporary Password</label>
		<input type="password" name="password"><br><br>
		
		<input type="submit" value="Register"/>
		
	</form>
</body>
</html>