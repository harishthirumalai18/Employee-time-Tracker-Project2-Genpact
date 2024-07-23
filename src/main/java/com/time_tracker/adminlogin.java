package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.time_tracker.DBConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/adminlogin")
public class adminlogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("admin_name");
		String password= request.getParameter("admin_password");
		HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        
        String query = "SELECT * FROM admin_details WHERE admin_name = ? AND admin_password = ?";
        	try (Connection con = DBConnection.getConnection();
   	             PreparedStatement pst = con.prepareStatement(query)){
        	
        	
        	pst.setString(1, username);
        	pst.setString(2, password);
        	
        	ResultSet rs=pst.executeQuery();
        	if(rs.next()) {
        		session.setAttribute("user_name", rs.getString("admin_name"));
        		dispatcher=request.getRequestDispatcher("admin_home.jsp");
        	}
        	else {
        		request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("admin_login.jsp");
        	}
        	dispatcher.forward(request, response);
        	rs.close();
        	pst.close();
        	con.close();
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
		
		
		
	}
}
