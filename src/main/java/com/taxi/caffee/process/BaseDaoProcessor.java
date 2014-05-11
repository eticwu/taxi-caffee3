package com.taxi.caffee.process;

import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class BaseDaoProcessor implements DataProcessor {

	private static BaseDaoProcessor processor = new BaseDaoProcessor();
	
	@Override
	public Map<String, Object> process(ProcessData data) {
		VelocityEngine engine = new VelocityEngine();
		Template temp = null;
		try {
			engine.init();
			temp = engine.getTemplate("daoTemplate.vm");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VelocityContext context = new VelocityContext();
		context.put("rootPath", data.getRootPath());
//		context.put("properties", value);
//		 temp.merge(context, prams);
		return null;
	}

	@Override
	public Integer supportType() {
		return DataTypeConstant.DAO;
	}

	private BaseDaoProcessor() {
		
	}

	public static BaseDaoProcessor getInstance() {
		return processor;
	}

	
}
