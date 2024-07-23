package com.time_tracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import com.util.time_tracker.DBConnection;

@WebServlet("/GetEmployeeDurationsServlet")
public class GetEmployeeDurationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray labels = new JSONArray();
        JSONArray durations = new JSONArray();

        String query = "SELECT emp_name, SUM(IFNULL(TIME_TO_SEC(duration), 0) / 3600) AS total_duration " +
                       "FROM task_table " +
                       "WHERE duration IS NOT NULL " +
                       "GROUP BY emp_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {         

            while (rs.next()) {
                String empName = rs.getString("emp_name");
                double totalDuration = rs.getDouble("total_duration");

                labels.put(empName);
                durations.put(totalDuration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        JSONObject json = new JSONObject();
        json.put("labels", labels);
        json.put("durations", durations);

        response.getWriter().print(json.toString());
    }
}
