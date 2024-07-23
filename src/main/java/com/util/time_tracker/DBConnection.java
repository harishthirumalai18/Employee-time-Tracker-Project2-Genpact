package com.util.time_tracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	  private static final String URL = "jdbc:mysql://localhost:3306/tracker";
	    private static final String USER = "root";
	    private static final String PASSWORD = "2003"; 

	    
	    static {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to load MySQL JDBC driver.", e);
	        }
	    }

	   
	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
}
