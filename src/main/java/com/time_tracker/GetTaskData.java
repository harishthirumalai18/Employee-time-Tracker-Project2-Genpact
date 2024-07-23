package com.time_tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.util.time_tracker.DBConnection;

@WebServlet("/GetTaskData")
public class GetTaskData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String employeeId = (String) session.getAttribute("emp_id");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Map<String, Integer> taskData = new HashMap<>();

        String query = "SELECT task_category, SUM(TIME_TO_SEC(TIMEDIFF(end_time, start_time))) AS duration "
                     + "FROM task_table WHERE emp_id = ? GROUP BY task_category";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, employeeId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    taskData.put(rs.getString("task_category"), rs.getInt("duration"));
                }
            }
            
            String json = new Gson().toJson(taskData);
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}