package com.taxi.caffee.process;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.taxi.caffee.access.DBTableColumn;

public class MainDataProcessor {

    private List<DataProcessor> processors;

    private boolean isInitialize = false;

    public void init(Integer... no) {
	if (isInitialize) {
	    return;
	}
	List<Integer> select = null;
	if (no == null || no.length == 0) {
	    select = Arrays.asList(DataTypeConstant.DAO, DataTypeConstant.ENTITY,
		    DataTypeConstant.MAPPERXML, DataTypeConstant.DAOIMPL);
	} else {
	    select = Arrays.asList(no);
	}
	processors = new ArrayList<DataProcessor>(select.size());
	if (select.contains(DataTypeConstant.DAO)) {
	    processors.add(BaseDaoProcessor.getInstance());
	}
	if (select.contains(DataTypeConstant.ENTITY)) {
	    processors.add(BaseEntityProcessor.getInstance());
	}
	if (select.contains(DataTypeConstant.MAPPERXML)) {
	    processors.add(BaseMapperXmlProcessor.getInstance());
	}
	if (select.contains(DataTypeConstant.DAOIMPL)) {
	    processors.add(BaseDaoImplProcessor.getInstance());
	}
	isInitialize = true;
    }

    public void processAll(ProcessData data) throws Exception {
	init();
	Map<String, Object> params = new HashMap<String, Object>();
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
	    if (column.getIsPK()) {
		params.put("idVar", propName);
		params.put("idType", propType);
		params.put("idFullType", DataProcessUtil.getJavaFullType(propType));
	    }
	    props.add(prop);
	}

	String className = DataProcessUtil.tableToClass(data.getTableName());
	String baseClassName = "Base" + className;
	String daoInterfaceName = className + "Dao";
	String daoClassName = daoInterfaceName + "Impl";
	String entityPath = data.getRootPath() + ".entity";
	String daoPath = data.getRootPath() + ".dao";
	String daoImplPath = data.getRootPath() + ".dao.impl";
	params.put("rootPath", data.getRootPath());
	params.put("props", props);
	params.put("entity", entityPath + "." + className);
	params.put("namespace", daoPath + "." + daoInterfaceName);
	params.put("tableRemarks", data.getTableRemarks());
	params.put("tableName", data.getTableName());
	params.put("idName", data.getPrimaryKey());
	params.put("className", className);
	params.put("baseClassName", baseClassName); //
	params.put("daoInterfaceName", daoInterfaceName);	
	params.put("daoClassName", daoClassName);
	params.put("entityPath", entityPath);
	params.put("daoPath", daoPath);
	params.put("daoImplPath", daoImplPath);
	params.put("imports", new ArrayList<String>(importSet));
	params.put("date", DateFormat.getDateInstance().format(new Date()));
	for (DataProcessor processor : processors) {
	    processor.process(params);
	}
    }

}
