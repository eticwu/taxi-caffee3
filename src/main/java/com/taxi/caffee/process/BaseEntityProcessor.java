package com.taxi.caffee.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.taxi.caffee.access.DBTableColumn;

public class BaseEntityProcessor implements DataProcessor {

    private static BaseEntityProcessor processor = new BaseEntityProcessor();

    @Override
    public Map<String, Object> process(Map<String, Object> params) throws IOException {
	VelocityEngine engine = new VelocityEngine();
	Template temp = null;
	try {
	    Properties properties = new Properties();
	    properties.put("file.resource.loader.class",
		    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    engine.init(properties);
	    temp = engine.getTemplate("./templates/baseEntityTemplate.vm");
	} catch (Exception e) {
	    e.printStackTrace();
	}

	VelocityContext context = new VelocityContext();
	for(String key : params.keySet()){
	    context.put(key, params.get(key));
	}
	String className = (String)params.get("className");
	BufferedWriter writer = new BufferedWriter(new FileWriter(className + ".java"));
	temp.merge(context, writer);
	writer.flush();
	writer.close();
	return null;
    }

    @Override
    public Integer supportType() {
	return DataTypeConstant.ENTITY;
    }

    private BaseEntityProcessor() {

    }

    public static BaseEntityProcessor getInstance() {
	return processor;
    }

    public static void main(String[] args) throws Exception {
//	BaseEntityProcessor processor = BaseEntityProcessor.getInstance();
	// System.out.println(processor.columnToProperty("DF_DriverComment"));
	// System.out.println(processor.tableToClass("DF_DriverComment"));
	// System.out.println(processor.propertyGetter("dfDriver"));
	// System.out.println(processor.propertySetter("dfDriver"));
	MainDataProcessor processor = new MainDataProcessor();
	processor.init(DataTypeConstant.ENTITY);
	List<DBTableColumn> columns = new ArrayList<DBTableColumn>();
	DBTableColumn column = new DBTableColumn();
	column.setName("actor_id");
	column.setType(1);
	column.setRemarks("test");
	columns.add(column);

	DBTableColumn column2 = new DBTableColumn();
	column2.setName("title");
	column2.setType(2);
	column2.setRemarks("123123");
	columns.add(column2);

	ProcessData data = new ProcessData();
	data.setColumns(columns);
	data.setRootPath("com.texi.coffee3.entity");
	data.setTableName("ss_actor");
	data.setTableRemarks("这里是表描述");
	processor.processAll(data);
    }
}
