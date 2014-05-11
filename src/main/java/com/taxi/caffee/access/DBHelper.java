package com.taxi.caffee.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class DBHelper {

	private String url;
	
	private String userName;
	
	private String password;
	
	public DBHelper(String url, String userName, String password) throws SQLException {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public List<String> getTables() throws ClassNotFoundException {
		return DBUtil.getTables(url, userName, password);
	}

	public List<DBTableColumn> getColumns(String tableName) throws ClassNotFoundException {
		if(StringUtils.isBlank(tableName)){
			return new ArrayList<DBTableColumn>(0);
		}
		return DBUtil.getColumns(url, userName, password, tableName);
	}
	
	public DBTable getTable(String tableName) throws ClassNotFoundException {
		if(StringUtils.isBlank(tableName)){
			return null;
		}
		return DBUtil.getTable(url, userName, password, tableName);
	}
	

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		DBHelper helper = new DBHelper("jdbc:mysql://localhost:3306/sakila", "root", "admin");
//		System.out.println(ArrayUtils.toString(helper.getTables().toArray()));
		System.out.println(ArrayUtils.toString(helper.getColumns("actor").toArray()));
	}
}
