package com.gaoyang.jact.command;

import picocli.CommandLine;

@CommandLine.Command(name = "jact", subcommands = {
        RunCommand.class,
        VersionCommand.class,
        PingCommand.class
})
public class HelpCommand implements Runnable {
    /**
     *
     */
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
