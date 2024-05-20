package com.gaoyang.jactions.controller;

import com.gaoyang.jactions.service.DockerManager;
import com.gaoyang.jactions.service.WorkflowParser;
import com.gaoyang.jactions.service.WorkflowRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
    private final WorkflowRunner workflowRunner;
    private final WorkflowParser workflowParser;
    private final DockerManager dockerManager;

    public WorkflowController(WorkflowRunner workflowRunner, WorkflowParser workflowParser, DockerManager dockerManager) {
        this.workflowRunner = workflowRunner;
        this.workflowParser = workflowParser;
        this.dockerManager = dockerManager;
    }

    @PostMapping("/run")
    public String runWorkflow(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> workflowDefinition = workflowParser.parseYaml(file.getInputStream());
            workflowRunner.runWorkflow(workflowDefinition);
            return "Workflow executed successfully";
        } catch (IOException e) {
            return "Failed to execute workflow: " + e.getMessage();
        }
    }

    @GetMapping("/ping")
    public String ping() {
        dockerManager.ping();
        return "成功";
    }
}
