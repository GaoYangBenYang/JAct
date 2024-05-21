//package com.gaoyang.jact.controller;
//
//import com.gaoyang.jact.service.DockerManager;
//import com.gaoyang.jact.service.WorkflowParser;
//import com.gaoyang.jact.service.WorkflowRunner;
//import org.springframework.stereotype.Controller;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Controller
//public class WorkflowController {
//    private final WorkflowRunner workflowRunner;
//    private final WorkflowParser workflowParser;
//    private final DockerManager dockerManager;
//
//    public WorkflowController(WorkflowRunner workflowRunner, WorkflowParser workflowParser, DockerManager dockerManager) {
//        this.workflowRunner = workflowRunner;
//        this.workflowParser = workflowParser;
//        this.dockerManager = dockerManager;
//    }
//
//
//    public String runWorkflow() {
//        try {
//            Map<String, Object> workflowDefinition = workflowParser.parseYaml(file.getInputStream());
//            workflowRunner.runWorkflow(workflowDefinition);
//            return "Workflow executed successfully";
//        } catch (IOException e) {
//            return "Failed to execute workflow: " + e.getMessage();
//        }
//    }
//
//
//}
