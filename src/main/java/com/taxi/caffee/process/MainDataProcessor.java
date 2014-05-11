package com.taxi.caffee.process;

import java.util.ArrayList;
import java.util.List;

public class MainDataProcessor {

	private List<DataProcessor> processors;

	public void init() {
		processors = new ArrayList<DataProcessor>();
		//processors.add(BaseDaoProcessor.getInstance());
//		processors.add(BaseEntityProcessor.getInstance());
		processors.add(BaseMapperXmlProcessor.getInstance());
	}

	public void processAll(ProcessData data) throws Exception {
		for (DataProcessor processor : processors) {
			processor.process(data);
		}
	}

}
