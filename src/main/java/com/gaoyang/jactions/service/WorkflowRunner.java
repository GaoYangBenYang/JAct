package com.gaoyang.jactions.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WorkflowRunner {
    private final DockerManager dockerManager;

    public WorkflowRunner(DockerManager dockerManager) {
        this.dockerManager = dockerManager;
    }

    public void runWorkflow(Map<String, Object> workflowDefinition) {
//        List<Map<String, String>> jobs = (List<Map<String, String>>) workflowDefinition.get("jobs");
//        for (Map<String, String> job : jobs) {
//            String imageName = job.get("image");
//            String containerId = dockerManager.createAndStartContainer(imageName);
//
//            // 处理步骤执行逻辑
//
//            dockerManager.stopAndRemoveContainer(containerId);
//        }
        dockerManager.ping();
    }
}
