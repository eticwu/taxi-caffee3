package com.taxi.caffee.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.taxi.caffee.access.DBTableColumn;

public class BaseEntityProcessor implements DataProcessor {

	private static BaseEntityProcessor processor = new BaseEntityProcessor();

	@Override
	public Map<String, Object> process(ProcessData data) throws IOException {
		if (data == null) {
			// TODO
		}
		VelocityEngine engine = new VelocityEngine();
		Template temp = null, temp2 = null;
		try {
			Properties properties = new Properties();
			properties
					.put("file.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			engine.init(properties);
			temp = engine.getTemplate("./templates/baseEntityTemplate.vm");
			temp2 = engine.getTemplate("./templates/entityTemplate.vm");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<BasePropertyData> props = new ArrayList<BasePropertyData>();
		Set<String> importSet = new HashSet<String>();
		for (DBTableColumn column : data.getColumns()) {
			BasePropertyData prop = new BasePropertyData();
			String propName = DataProcessUtil.columnToProperty(column.getName());
			String propType = DataProcessUtil.jdbcTypeToJavaType(column.getType());
			prop.setName(DataProcessUtil.columnToProperty(column.getName()));
			prop.setType(propType);
			prop.setRemarks(column.getRemarks());
			prop.setgMethod(DataProcessUtil.propertyGetter(propName));
			prop.setsMethod(DataProcessUtil.propertySetter(propName));
			if (DataProcessUtil.getImportPath(propType) != null) {
				importSet.add(DataProcessUtil.getImportPath(propType));
			}
			props.add(prop);
		}

		VelocityContext context = new VelocityContext();
		String className = DataProcessUtil.tableToClass(data.getTableName());
		String baseClassName = "Base" + className;
		context.put("rootPath", data.getRootPath());
		context.put("props", props);
		context.put("className", className);
		context.put("baseClassName", baseClassName);
		context.put("tableRemarks", data.getTableRemarks());
		context.put("imports", new ArrayList<String>(importSet));
		context.put("date", DateFormat.getDateInstance().format(new Date()));
		BufferedWriter writer = new BufferedWriter(new FileWriter(baseClassName
				+ ".java"));
		temp.merge(context, writer);
		writer.flush();
		writer.close();
		writer = new BufferedWriter(new FileWriter(className + ".java"));
		temp2.merge(context, writer);
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

	public static void main(String[] args) throws IOException {
		BaseEntityProcessor processor = BaseEntityProcessor.getInstance();
		// System.out.println(processor.columnToProperty("DF_DriverComment"));
		// System.out.println(processor.tableToClass("DF_DriverComment"));
		// System.out.println(processor.propertyGetter("dfDriver"));
		// System.out.println(processor.propertySetter("dfDriver"));
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
		processor.process(data);
	}
}
