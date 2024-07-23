<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>
<link rel="stylesheet" type="text/css" href="CSS/admin_login.css">
</head>
<body>
    <div class="login-container">
        <h2>Admin Login</h2>
        <form method="post" action="adminlogin">
            <label>Username</label>
            <input type="text" name="admin_name"/><br><br>
            
            <label>Password</label>
            <input type="password" name="admin_password"/><br><br>
            
            <input type="submit">
        </form>
    </div>
</body>
</html>
