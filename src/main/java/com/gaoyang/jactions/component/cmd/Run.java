package com.gaoyang.jactions.component.cmd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class Run implements CommandLineRunner {
    @Override
    @CommandLine.Command(name = "run", description = "Run the application")
    public void run(String... args) throws Exception {

    }
}
