package com.taxi.caffee.access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

	public static Connection getConnection(String url) throws SQLException {
		return DriverManager.getConnection(url);
	}

	public static Connection getConnection(String url, String userName,
			String password) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").getInterfaces();
		return DriverManager.getConnection(url, userName, password);
	}

	public static List<String> getTables(String url, String userName,
			String password) throws ClassNotFoundException {
		List<String> results = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection(url, userName, password);
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
			while (rs.next()) {
				results.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(conn, ps, rs);
		}
		return results;
	}

	public static List<DBTableColumn> getColumns(String url, String userName,
			String password, String tableName) throws ClassNotFoundException {
		List<DBTableColumn> results = new ArrayList<DBTableColumn>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection(url, userName, password);
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, "%", tableName, "%");
			while (rs.next()) {
				DBTableColumn column = new DBTableColumn();
				column.setName(rs.getString("COLUMN_NAME"));
				column.setType(rs.getInt("DATA_TYPE"));
				column.setRemarks(rs.getString("REMARKS"));
				results.add(column);
			}

			rs = dbmd.getPrimaryKeys(null, null, tableName);
			String pk = null;
			while (rs.next()) {
				pk = rs.getString("COLUMN_NAME");
			}
			for (int i = 0; i < results.size(); i++) {
				if (results.get(i).getName().equals(pk)) {
					results.get(i).setIsPK(true);
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(conn, ps, rs);
		}
		return results;
	}

	@SuppressWarnings("resource")
	public static DBTable getTable(String url, String userName,
			String password, String tableName) throws ClassNotFoundException {
		DBTable table = new DBTable();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select TABLE_NAME, TABLE_COMMENT from INFORMATION_SCHEMA.TABLES where TABLE_NAME = \""+ tableName +"\"";
		try {
			conn = getConnection(url, userName, password);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				table.setName(rs.getString("TABLE_NAME"));
				table.setRemarks(rs.getString("TABLE_COMMENT"));
			}
			
			rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
			String pk = null;
			while (rs.next()) {
				pk = rs.getString("COLUMN_NAME");
			}
			table.setPrimaryKey(pk);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(conn, ps, rs);
		}
		return table;
	}

	private static void close(Connection conn, PreparedStatement ps,
			ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
