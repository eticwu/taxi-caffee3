package com.taxi.caffee.access;

public class DBTableColumn {
    
    private String name = "";
    
    private Integer type = 0;

    private String remarks = "";
    
    private Boolean isPK = false;

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

	public Boolean getIsPK() {
		return isPK;
	}

	public void setIsPK(Boolean isPK) {
		this.isPK = isPK;
	}
	
	@Override
	public String toString() {
		return "[name:" + name + ", type:" + type + ", isPK:" + isPK + ", remarks:" + remarks + "]";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
