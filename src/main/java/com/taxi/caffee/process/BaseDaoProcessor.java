package com.taxi.caffee.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class BaseDaoProcessor implements DataProcessor {

    private static BaseDaoProcessor processor = new BaseDaoProcessor();

    @Override
    public Map<String, Object> process(Map<String, Object> params) throws IOException {
	VelocityEngine engine = new VelocityEngine();
	Template temp = null;
	try {
		Properties properties = new Properties();
	    properties.put("file.resource.loader.class",
		    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    engine.init(properties);
	    temp = engine.getTemplate("./templates/baseDaoTemplate.vm");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	VelocityContext context = new VelocityContext();
	for(String key : params.keySet()){
	    context.put(key, params.get(key));
	}
	String daoInterfaceName = (String)params.get("daoInterfaceName");
	BufferedWriter writer = new BufferedWriter(new FileWriter(daoInterfaceName + ".java"));
	temp.merge(context, writer);
	writer.flush();
	writer.close();
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
