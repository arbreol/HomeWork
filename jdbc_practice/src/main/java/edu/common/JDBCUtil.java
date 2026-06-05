package edu.common;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCUtil {
    @Getter
    private static Connection connection;

    static {
        try {
            Properties properties = new Properties();
            properties.load(JDBCUtil.class.getResourceAsStream("/application.properties"));

            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String id = properties.getProperty("id");
            String password = properties.getProperty("password");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, id, password);
            connection.setAutoCommit(false);

        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
