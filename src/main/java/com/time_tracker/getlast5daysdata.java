package com.time_tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.util.time_tracker.DBConnection;

@WebServlet("/GetLast5DaysData")
public class getlast5daysdata extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        HttpSession session = request.getSession(false);
        if (session != null) {
            String employeeName = (String) session.getAttribute("emp_name");

            if (employeeName != null && !employeeName.isEmpty()) {
                String query = "SELECT DATE(task_date) AS task_date, SUM(TIME_TO_SEC(duration)) AS total_duration "
                             + "FROM task_table "
                             + "WHERE task_date >= CURDATE() - INTERVAL 5 DAY "
                             + "AND emp_name = ? "
                             + "GROUP BY DATE(task_date) "
                             + "ORDER BY DATE(task_date) ASC";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement pst = con.prepareStatement(query)) {
                    
                    pst.setString(1, employeeName);
                    try (ResultSet rs = pst.executeQuery()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                        while (rs.next()) {
                            String taskDate = sdf.format(rs.getDate("task_date"));
                            int totalDurationInSeconds = rs.getInt("total_duration");
                            jsonResponse.put(taskDate, totalDurationInSeconds);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    jsonResponse.put("error", e.getMessage());
                }
            } else {
                jsonResponse.put("error", "Employee name not found in session");
            }
        } else {
            jsonResponse.put("error", "Session not found");
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
