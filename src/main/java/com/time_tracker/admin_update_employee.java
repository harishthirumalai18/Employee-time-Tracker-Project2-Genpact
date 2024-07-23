package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.time_tracker.DBConnection;

@WebServlet("/admin_update_employee")
public class admin_update_employee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emp_id = request.getParameter("emp_id");
        String new_name = request.getParameter("new_name");
        String new_age = request.getParameter("new_age");
        String new_role = request.getParameter("new_role");
        String new_phone = request.getParameter("new_phone");
        String new_email = request.getParameter("new_email");

        if (emp_id == null || emp_id.isEmpty()) {
            request.setAttribute("message", "Employee ID is required.");
            request.getRequestDispatcher("admin_edit_employee.jsp").forward(request, response);
            return;
        }

        Connection con = null;
        PreparedStatement pst = null;

        StringBuilder query = new StringBuilder("UPDATE employee_table SET ");
        
        boolean first = true;

        if (new_name != null && !new_name.isEmpty()) {
            query.append("emp_name = ?");
            first = false;
        }
        if (new_age != null && !new_age.isEmpty()) {
            if (!first) query.append(", ");
            query.append("age = ?");
            first = false;
        }
        if (new_role != null && !new_role.isEmpty()) {
            if (!first) query.append(", ");
            query.append("role = ?");
            first = false;
        }
        if (new_phone != null && !new_phone.isEmpty()) {
            if (!first) query.append(", ");
            query.append("phone_number = ?");
            first = false;
        }
        if (new_email != null && !new_email.isEmpty()) {
            if (!first) query.append(", ");
            query.append("email = ?");
        }

        query.append(" WHERE emp_id = ?");

        try {
            con = DBConnection.getConnection();
            pst = con.prepareStatement(query.toString());

            int index = 1;
            if (new_name != null && !new_name.isEmpty()) {
                pst.setString(index++, new_name);
            }
            if (new_age != null && !new_age.isEmpty()) {
                pst.setString(index++, new_age);
            }
            if (new_role != null && !new_role.isEmpty()) {
                pst.setString(index++, new_role);
            }
            if (new_phone != null && !new_phone.isEmpty()) {
                pst.setString(index++, new_phone);
            }
            if (new_email != null && !new_email.isEmpty()) {
                pst.setString(index++, new_email);
            }
            pst.setString(index, emp_id);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("admin_edit_employee.jsp"); // Redirect after successful update
            } else {
                request.setAttribute("message", "Failed to update employee details.");
                request.getRequestDispatcher("admin_edit_employee.jsp").forward(request, response); // Forward on failure
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Error: " + ex.getMessage());
            request.getRequestDispatcher("admin_edit_employee.jsp").forward(request, response); // Forward on error
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}