package com.taxi.caffee.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class BaseDaoImplProcessor implements DataProcessor {

    private static BaseDaoImplProcessor processor = new BaseDaoImplProcessor();

    @Override
    public Map<String, Object> process(Map<String, Object> params) throws IOException {
	VelocityEngine engine = new VelocityEngine();
	Template temp = null;
	try {
		Properties properties = new Properties();
	    properties.put("file.resource.loader.class",
		    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    engine.init(properties);
	    temp = engine.getTemplate("./templates/baseDaoImplTemplate.vm");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	VelocityContext context = new VelocityContext();
	for(String key : params.keySet()){
	    context.put(key, params.get(key));
	}
	String daoClassName = (String)params.get("daoClassName");
	BufferedWriter writer = new BufferedWriter(new FileWriter(daoClassName + ".java"));
	temp.merge(context, writer);
	writer.flush();
	writer.close();
	return null;
    }

    @Override
    public Integer supportType() {
	return DataTypeConstant.DAOIMPL;
    }

    private BaseDaoImplProcessor() {

    }

    public static BaseDaoImplProcessor getInstance() {
	return processor;
    }

}
