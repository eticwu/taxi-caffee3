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

public class BaseMapperXmlProcessor implements DataProcessor {

    private static BaseMapperXmlProcessor processor = new BaseMapperXmlProcessor();

    @Override
    public Map<String, Object> process(Map<String, Object> params) throws IOException {
	VelocityEngine engine = new VelocityEngine();
	Template temp = null;
	try {
	    Properties properties = new Properties();
	    properties.put("file.resource.loader.class",
		    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    engine.init(properties);
	    temp = engine.getTemplate("./templates/baseMapperXmlTemplate.vm");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	VelocityContext context = new VelocityContext();
	for (String key : params.keySet()) {
	    context.put(key, params.get(key));
	}
	String className = (String) params.get("className");
	BufferedWriter writer = new BufferedWriter(new FileWriter(className + "Mapper.xml"));
	temp.merge(context, writer);
	writer.flush();
	writer.close();
	return null;
    }

    @Override
    public Integer supportType() {
	return DataTypeConstant.MAPPERXML;
    }

    private BaseMapperXmlProcessor() {

    }

    public static BaseMapperXmlProcessor getInstance() {
	return processor;
    }

    public static void main(String[] args) throws Exception {
	MainDataProcessor processor = new MainDataProcessor();
	processor.init(DataTypeConstant.MAPPERXML);
	List<DBTableColumn> columns = new ArrayList<DBTableColumn>();
	DBTableColumn column = new DBTableColumn();
	column.setName("actor_id");
	column.setType(1);
	column.setRemarks("test");
	column.setIsPK(true);
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
