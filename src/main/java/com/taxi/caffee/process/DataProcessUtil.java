package com.taxi.caffee.process;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DataProcessUtil {

	private static Map<Integer, String> jdbcTypeMap;

	private static Map<String, String> importMap;

	static {
		jdbcTypeMap = new HashMap<Integer, String>();
		jdbcTypeMap.put(new Integer(Types.LONGNVARCHAR), "String"); // -16 字符串
		jdbcTypeMap.put(new Integer(Types.NCHAR), "String"); // -15 字符串
		jdbcTypeMap.put(new Integer(Types.NVARCHAR), "String"); // -9 字符串
		jdbcTypeMap.put(new Integer(Types.ROWID), "String"); // -8 字符串
		jdbcTypeMap.put(new Integer(Types.BIT), "Boolean"); // -7 布尔
		jdbcTypeMap.put(new Integer(Types.TINYINT), "Byte"); // -6 数字
		jdbcTypeMap.put(new Integer(Types.BIGINT), "Long"); // -5 数字
		// jdbcTypeMap.put(new Integer(Types.LONGVARBINARY), "Blob"); // -4 二进制
		// jdbcTypeMap.put(new Integer(Types.VARBINARY), "Blob"); // -3 二进制
		// jdbcTypeMap.put(new Integer(Types.BINARY), "Blob"); // -2 二进制
		jdbcTypeMap.put(new Integer(Types.LONGVARCHAR), "String"); // -1 字符串
		// jdbcTypeMap.put(new Integer(Types.NULL), String); // 0 /
		jdbcTypeMap.put(new Integer(Types.CHAR), "String"); // 1 字符串
		jdbcTypeMap.put(new Integer(Types.NUMERIC), "BigDecimal"); // 2 数字
		jdbcTypeMap.put(new Integer(Types.DECIMAL), "BigDecimal"); // 3 数字
		jdbcTypeMap.put(new Integer(Types.INTEGER), "Integer"); // 4 数字
		jdbcTypeMap.put(new Integer(Types.SMALLINT), "Short"); // 5 数字
		jdbcTypeMap.put(new Integer(Types.FLOAT), "BigDecimal"); // 6 数字
		jdbcTypeMap.put(new Integer(Types.REAL), "BigDecimal"); // 7 数字
		jdbcTypeMap.put(new Integer(Types.DOUBLE), "BigDecimal"); // 8 数字
		jdbcTypeMap.put(new Integer(Types.VARCHAR), "String"); // 12 字符串
		jdbcTypeMap.put(new Integer(Types.BOOLEAN), "Boolean"); // 16 布尔
		// jdbcTypeMap.put(new Integer(Types.DATALINK), String); // 70 /
		jdbcTypeMap.put(new Integer(Types.DATE), "Date"); // 91 日期
		jdbcTypeMap.put(new Integer(Types.TIME), "Date"); // 92 日期
		jdbcTypeMap.put(new Integer(Types.TIMESTAMP), "Date"); // 93 日期
		// jdbcTypeMap.put(new Integer(Types.OTHER), Object); // 1111 其他类型？
		// jdbcTypeMap.put(new Integer(Types.JAVA_OBJECT), Object); // 2000
		// jdbcTypeMap.put(new Integer(Types.DISTINCT), String); // 2001
		// jdbcTypeMap.put(new Integer(Types.STRUCT), String); // 2002
		// jdbcTypeMap.put(new Integer(Types.ARRAY), String); // 2003
		// jdbcTypeMap.put(new Integer(Types.BLOB), "Blob"); // 2004 二进制
		// jdbcTypeMap.put(new Integer(Types.CLOB), "Clob"); // 2005 大文本
		// jdbcTypeMap.put(new Integer(Types.REF), String); // 2006
		// jdbcTypeMap.put(new Integer(Types.SQLXML), String); // 2009
		// jdbcTypeMap.put(new Integer(Types.NCLOB), Clob); // 2011 大文本
		importMap = new HashMap<String, String>();
		importMap.put("java.sql.Date", "java.util.Date");
		importMap.put("java.math.BigDecimal", "java.math.BigDecimal");
	}
	
	public static String columnToProperty(String column) {
		StringBuilder propertyBuilder = new StringBuilder();
		if (StringUtils.isBlank(column)) {
			return "";
		}
		if (!column.contains("_")) {
			return propertyBuilder.append(column.substring(0, 1).toLowerCase())
					.append(column.substring(1)).toString();
		}
		String[] phase = column.split("_");
		for (String p : phase) {
			if (StringUtils.isBlank(p)) {
				continue;
			}
			if (propertyBuilder.length() == 0) {
				propertyBuilder.append(p.toLowerCase());
			} else {
				propertyBuilder.append(p.substring(0, 1).toUpperCase())
						.append(p.substring(1)).toString();
			}
		}
		return propertyBuilder.toString();
	}

	public static String tableToClass(String table) {
		StringBuilder classBuilder = new StringBuilder();
		if (StringUtils.isBlank(table)) {
			return "";
		}
		if (!table.contains("_")) {
			return classBuilder.append(table.substring(0, 1).toUpperCase())
					.append(table.substring(1)).toString();
		}
		String[] phase = table.split("_");
		for (String p : phase) {
			if (StringUtils.isBlank(p)) {
				continue;
			}
			classBuilder.append(p.substring(0, 1).toUpperCase()).append(
					p.substring(1));
		}
		return classBuilder.toString();
	}

	public static String propertyGetter(String property) {
		StringBuilder getterBuilder = new StringBuilder();
		if (StringUtils.isBlank(property)) {
			return "";
		}
		getterBuilder.append("get")
				.append(property.substring(0, 1).toUpperCase())
				.append(property.substring(1));
		return getterBuilder.toString();
	}

	public static String propertySetter(String property) {
		StringBuilder setterBuilder = new StringBuilder();
		if (StringUtils.isBlank(property)) {
			return "";
		}
		setterBuilder.append("set")
				.append(property.substring(0, 1).toUpperCase())
				.append(property.substring(1));
		return setterBuilder.toString();
	}

	public static String jdbcTypeToJavaType(Integer jdbcType) {
		return jdbcTypeMap.get(jdbcType);
	}
	
	public static String getImportPath(String type) {
		return importMap.get(type);
	}
}
