package com.gaoyang.jact.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class Command implements CommandLineRunner {

    @Override
    public void run(String... args) {
        new CommandLine(new HelpCommand()).execute(args);
    }

}