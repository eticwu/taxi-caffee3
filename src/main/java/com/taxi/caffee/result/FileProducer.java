package com.taxi.caffee.result;

import java.io.File;
import java.util.Map;

public interface FileProducer {

    public File produceResult(Map<String, Object> dataMap);
}
