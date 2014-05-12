package com.taxi.caffee.process;

import java.util.Map;

public interface DataProcessor {
    
    public Map<String, Object> process(Map<String, Object> params) throws Exception;
    
    public Integer supportType();

}
