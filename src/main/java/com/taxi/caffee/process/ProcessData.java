package com.taxi.caffee.process;

import java.util.List;

import com.taxi.caffee.access.DBTableColumn;

public class ProcessData {

	private String rootPath;

	// private String projectName;

	private String tableName;

	private String tableRemarks;
	
	private String primaryKey;

	private List<DBTableColumn> columns;

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public List<DBTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DBTableColumn> columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableRemarks() {
		return tableRemarks;
	}

	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

}
