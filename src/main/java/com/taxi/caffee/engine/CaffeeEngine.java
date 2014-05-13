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

    public CaffeeEngine(String url, String userName, String password) {
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
	//jdbcurl, root用户名, 密码
	CaffeeEngine engine = new CaffeeEngine("jdbc:mysql://192.168.3.161:36360/dfcar", "newtaxi",
		"pNPDYLCRjcFwxnG");
	//包路径, 之下分为dao,entity
	String rootPath = "com.newtaxi.dfcar.user";
	//表名, 包路径
	engine.bootstrap("DF_CreditCardAuthConfig", rootPath);
	// engine.bootstrap("address", rootPath);
	// engine.bootstrap("customer", rootPath);
	// engine.bootstrap("film_actor", rootPath);
	// engine.bootstrap("film_text", rootPath);
    }

}
