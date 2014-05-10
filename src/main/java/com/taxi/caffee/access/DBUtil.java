package com.taxi.caffee.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getConnection(String url) {
	Connection connection = null;
	try {
	    connection = DriverManager.getConnection(url);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return connection;
    }
}
