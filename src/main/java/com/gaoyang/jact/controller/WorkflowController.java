package com.gaoyang.jact.controller;

import com.gaoyang.jact.service.DockerManager;
import org.springframework.stereotype.Component;

@Component
public class WorkflowController {
    private final DockerManager dockerManager;

    public WorkflowController(DockerManager dockerManager) {
        this.dockerManager = dockerManager;
    }

    public void runWorkflow() {
        dockerManager.ping();
    }


}
