package com.taxi.caffee.process;

import java.util.Map;

public interface DataProcessor {
    
    public Map<String, Object> process(ProcessData data) throws Exception;
    
    public Integer supportType();

}
