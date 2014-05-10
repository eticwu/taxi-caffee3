package com.taxi.caffee.process;

import java.util.List;
import java.util.Map;

import com.taxi.caffee.access.DBTable;

public class BaseEntityProcessor implements DataProcessor {

    @Override
    public Map<String, Object> process(List<DBTable> tables) {
	return null;
    }

    @Override
    public Integer supportType() {
	return DataTypeConstant.ENTITYTYPE;
    }

}
