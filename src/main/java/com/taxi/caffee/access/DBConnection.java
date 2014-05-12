package com.taxi.caffee.access;

import java.sql.Connection;
import java.util.List;

public class DBConnection {
    
    private Connection connection;
    
    public DBConnection(String url) {
    }
    
    public List<DBTable> getTables() {
	return null;
    }
    
    public DBTable getTable(String tableName) {
	return null;
    }
    
    public List<DBTableColumn> getColumns() {
	return null;
    }
    
    public DBTableColumn getColumn(String columnName) {
	return null;
    }

    public Connection getConnection() {
        return connection;
    }

}
