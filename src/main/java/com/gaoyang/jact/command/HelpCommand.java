package com.gaoyang.jact.command;

import picocli.CommandLine;

@CommandLine.Command(name = "help", description = "Application help documentation")
public class HelpCommand implements Runnable {
    /**
     *
     */
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
