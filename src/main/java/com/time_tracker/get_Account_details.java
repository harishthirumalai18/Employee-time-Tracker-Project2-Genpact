package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.time_tracker.DBConnection;


@WebServlet("/get_employee_detail")
public class get_Account_details extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone_number = request.getParameter("phone_number");
        String password = request.getParameter("password");
        RequestDispatcher dispatcher = null;

            String query = ("SELECT emp_id, emp_password FROM employee_table WHERE phone_number = ? AND password = ?");
        	try (Connection con = DBConnection.getConnection();
   	             PreparedStatement pst = con.prepareStatement(query)){
            pst.setString(1, phone_number);
            pst.setString(2, password);
        	
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String emp_id = rs.getString("emp_id");
                String emp_password = rs.getString("emp_password");
                request.setAttribute("emp_id", emp_id);
                request.setAttribute("emp_password", emp_password);
                dispatcher = request.getRequestDispatcher("login.jsp");
            } else {
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database access error", e);
        }
    }
}
