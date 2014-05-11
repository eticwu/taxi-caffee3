package com.taxi.caffee.engine;

import java.util.List;

import com.taxi.caffee.access.DBHelper;
import com.taxi.caffee.access.DBTable;
import com.taxi.caffee.access.DBTableColumn;
import com.taxi.caffee.process.MainDataProcessor;
import com.taxi.caffee.process.ProcessData;

public class CaffeeEngine {
	
	public String userName;
	
	public String password;
	
	public String url;
	
	public CaffeeEngine(String url, String userName, String password){
		this.userName = userName;
		this.password = password;
		this.url = url;
	}
	
	public void bootstrap(String tableName, String rootPath) throws Exception {
		DBHelper helper = new DBHelper(url, userName, password);
		DBTable table = helper.getTable(tableName);
		List<DBTableColumn> columns = helper.getColumns(tableName);
		
		ProcessData data = new ProcessData();
		data.setColumns(columns);
		data.setTableRemarks(table.getRemarks());
		data.setTableName(table.getName());
		data.setRootPath(rootPath);
		data.setPrimaryKey(table.getPrimaryKey());
		
		MainDataProcessor processor = new MainDataProcessor();
		processor.init();
		processor.processAll(data);
	}
	
	public static void main(String[] args) throws Exception {
		CaffeeEngine engine = new CaffeeEngine("jdbc:mysql://localhost:3306/mini-web", "root", "admin");
		engine.bootstrap("tb_role", "com.texi.caffee3.entity");
		engine.bootstrap("tb_authority", "com.texi.caffee3.entity");
		engine.bootstrap("tb_customer", "com.texi.caffee3.entity");
		engine.bootstrap("tb_role_auth", "com.texi.caffee3.entity");
		engine.bootstrap("tb_user", "com.texi.caffee3.entity");
		engine.bootstrap("tb_user_role", "com.texi.caffee3.entity");
		
	}

}
