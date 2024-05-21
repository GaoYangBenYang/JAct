package com.gaoyang.jact.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.springframework.stereotype.Service;

@Service
public class DockerManager {

    private final DockerClient dockerClient;

    public DockerManager(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public String createAndStartContainer(String imageName) {
        CreateContainerResponse container = dockerClient.createContainerCmd(imageName).exec();
        dockerClient.startContainerCmd(container.getId()).exec();
        return container.getId();
    }

    public void stopAndRemoveContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
        dockerClient.removeContainerCmd(containerId).exec();
    }

    public void ping() {
        dockerClient.pingCmd().exec();
    }
}
