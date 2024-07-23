package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.time_tracker.DBConnection;

@WebServlet("/add-task")
public class Add_task extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String employee_id = (String) session.getAttribute("emp_id");
        String employee_name = (String) session.getAttribute("emp_name");
        int task_id = generate_task_id();
        String project_name = request.getParameter("project-name");
        String role = request.getParameter("task-role");
        String taskDate = request.getParameter("task-date");
        String start_time = request.getParameter("task-start-time");
        String end_time = request.getParameter("task-end-time");
        String task_category = request.getParameter("task-category");
        String task_description = request.getParameter("task-description");

        // Calculate duration
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long durationInSeconds = 0;

        try {
            // Ensure time format is correct
            if (!start_time.contains(":")) {
                start_time += ":00";
            }
            if (!end_time.contains(":")) {
                end_time += ":00";
            }

            Date start = sdf.parse(start_time);
            Date end = sdf.parse(end_time);
            durationInSeconds = (end.getTime() - start.getTime()) / 1000; // Duration in seconds
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("status", "error");
            request.setAttribute("message", "Time parsing error.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Update SQL statement to include duration
        String query = "INSERT INTO task_table (emp_id, emp_name, task_id, project_name, task_role, task_date, start_time, end_time, duration, task_category, task_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, employee_id);
            ps.setString(2, employee_name);
            ps.setInt(3, task_id);
            ps.setString(4, project_name);
            ps.setString(5, role);
            ps.setString(6, taskDate);
            ps.setString(7, start_time);
            ps.setString(8, end_time);
            ps.setLong(9, durationInSeconds); // Set duration in seconds
            ps.setString(10, task_category);
            ps.setString(11, task_description);

            int rowcount = ps.executeUpdate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            if (rowcount > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("status", "error");
            request.setAttribute("message", "Database error.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request, response);
        }
    }

    private int generate_task_id() {
        return new Random().nextInt(99999999);
    }
}
