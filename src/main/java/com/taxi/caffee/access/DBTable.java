package com.taxi.caffee.access;

import java.util.List;

public class DBTable {

    private String name;
    
    private String pkName;
    
    private List<DBTableColumn> columnList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DBTableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<DBTableColumn> columnList) {
        this.columnList = columnList;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }
}
