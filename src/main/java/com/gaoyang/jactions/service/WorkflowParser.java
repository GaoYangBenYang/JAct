package com.gaoyang.jactions.service;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Service
public class WorkflowParser {
    public Map<String, Object> parseYaml(InputStream inputStream) {
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }
}
