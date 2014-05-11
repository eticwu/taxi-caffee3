package com.taxi.caffee.process;

public class BasePropertyData {
	
	private String type;
	
	private String name;
	
	private String gMethod;
	
	private String sMethod;
	
	private String remarks;
	
	private String column;
	
	private String isPK;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getgMethod() {
		return gMethod;
	}

	public void setgMethod(String gMethod) {
		this.gMethod = gMethod;
	}

	public String getsMethod() {
		return sMethod;
	}

	public void setsMethod(String sMethod) {
		this.sMethod = sMethod;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getIsPK() {
		return isPK;
	}

	public void setIsPK(String isPK) {
		this.isPK = isPK;
	}

}
