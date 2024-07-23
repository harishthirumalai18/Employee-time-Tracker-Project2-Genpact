package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.time_tracker.DBConnection;

@WebServlet("/update_task")
public class update_task extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("task_id");
        String empId = request.getParameter("emp_id");
        String empName = request.getParameter("emp_name");
        String projectName = request.getParameter("project_name");
        String taskRole = request.getParameter("task_role");
        String taskDate = request.getParameter("task_date");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        String taskCategory = request.getParameter("task_category");
        String taskDescription = request.getParameter("task_description");

        // Calculate duration
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long durationInSeconds = 0;
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            durationInSeconds = (end.getTime() - start.getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect("edit-task.jsp?status=error&message=Time+parsing+error");
            return;
        }

        String sql = "UPDATE task_table SET emp_id = ?, emp_name = ?, project_name = ?, task_role = ?, task_date = ?, start_time = ?, end_time = ?, duration = SEC_TO_TIME(?), task_category = ?, task_description = ? WHERE task_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, empId);
            pst.setString(2, empName);
            pst.setString(3, projectName);
            pst.setString(4, taskRole);
            pst.setString(5, taskDate);
            pst.setString(6, startTime);
            pst.setString(7, endTime);
            pst.setLong(8, durationInSeconds); // Set duration in seconds
            pst.setString(9, taskCategory);
            pst.setString(10, taskDescription);
            pst.setString(11, taskId);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated > 0) {
                response.sendRedirect("edit-task.jsp?status=success");
            } else {
                response.sendRedirect("edit-task.jsp?status=error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("edit-task.jsp?status=error");
        }
    }
}
