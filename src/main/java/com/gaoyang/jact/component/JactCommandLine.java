package com.gaoyang.jact.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class JactCommandLine implements CommandLineRunner {

    @Override
    public void run(String... args) {
        new CommandLine(new JactCommand()).execute(args);
    }

    @CommandLine.Command(name = "jact", mixinStandardHelpOptions = true, version = "jact 1.0", description = "Jact CLI application")
    private static class JactCommand implements Runnable {

        @CommandLine.Command(name = "run", description = "Run the application")
        private void run(@CommandLine.Parameters(paramLabel = "<params>", description = "The parameters to run with") String[] params) {
            System.out.println("Running with parameters: " + String.join(", ", params));
        }

        @CommandLine.Command(name = "version", description = "Show the application version")
        private void version() {
            System.out.println("Jact version 1.0");
        }

        @Override
        public void run() {
            CommandLine.usage(this, System.out);
        }
    }
}