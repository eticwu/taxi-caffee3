package com.taxi.caffee.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.taxi.caffee.access.DBTableColumn;

public class BaseMapperXmlProcessor implements DataProcessor {

	private static BaseMapperXmlProcessor processor = new BaseMapperXmlProcessor();

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
			temp = engine.getTemplate("./templates/baseMapperXmlTemplate.vm");
//			temp2 = engine.getTemplate("./templates/entityTemplate.vm"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		VelocityContext context = new VelocityContext();
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
			prop.setColumn(column.getName());
			if (DataProcessUtil.getImportPath(propType) != null) {
				importSet.add(DataProcessUtil.getImportPath(propType));
			}
			if(column.getIsPK()){
				context.put("idVar", propName);
				context.put("idType", propType);
			}
			props.add(prop);
		}

		String className = DataProcessUtil.tableToClass(data.getTableName());
		context.put("rootPath", data.getRootPath());
		context.put("props", props);
		context.put("entity", data.getRootPath() + "." + className);
		context.put("namespace", data.getRootPath() + "." + className + "Dao");
		context.put("tableRemarks", data.getTableRemarks());
		context.put("tableName", data.getTableName());
		context.put("idName", data.getPrimaryKey());
		BufferedWriter writer = new BufferedWriter(new FileWriter("Base" + className + "Mapper.xml"));
		temp.merge(context, writer);
		writer.flush();
		writer.close();
//		writer = new BufferedWriter(new FileWriter(className + ".java"));
//		temp2.merge(context, writer);
//		writer.flush();
//		writer.close();
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
}
