package com.gaoyang.jactions.component.cmd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class Version implements CommandLineRunner {

    @Override
    @CommandLine.Command(name = "version", description = "Show the application version")
    public void run(String... args) throws Exception {
        
    }
}
