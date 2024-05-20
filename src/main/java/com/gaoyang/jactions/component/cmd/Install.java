package com.gaoyang.jactions.component.cmd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class Install implements CommandLineRunner {
    @Override
    @CommandLine.Command(name = "install", description = "Show the application version")
    public void run(String... args) throws Exception {

    }
}
