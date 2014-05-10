package com.taxi.caffee.process;

import java.util.List;
import java.util.Map;

import com.taxi.caffee.access.DBTable;

public interface DataProcessor {
    
    public Map<String, Object> process(List<DBTable> tables);
    
    public Integer supportType();

}
