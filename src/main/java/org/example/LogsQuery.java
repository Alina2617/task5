package org.example;

import java.sql.*;

public class LogsQuery {

    private static final String URL = "jdbc:postgresql://localhost:5432/logs_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static void main(String[] args) {
        String query = "SELECT DISTINCT num AS ConsecutiveNums " +
                "FROM ( " +
                "    SELECT num, " +
                "           LEAD(num, 1) OVER (ORDER BY id) AS next_num, " +
                "           LEAD(num, 2) OVER (ORDER BY id) AS next_num_2 " +
                "    FROM logs " +
                ") subquery " +
                "WHERE num = next_num AND num = next_num_2 " +
                "ORDER BY ConsecutiveNums DESC;";


        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("ConsecutiveNums:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ConsecutiveNums"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
